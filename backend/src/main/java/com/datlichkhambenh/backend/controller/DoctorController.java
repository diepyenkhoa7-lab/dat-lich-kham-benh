package com.datlichkhambenh.backend.controller;

import com.datlichkhambenh.backend.dto.request.CreateScheduleDto;
import com.datlichkhambenh.backend.dto.request.UpdateMedicalRecordDto;
import com.datlichkhambenh.backend.dto.response.ApiResponseDto;
import com.datlichkhambenh.backend.dto.response.AppointmentDto;
import com.datlichkhambenh.backend.dto.response.DoctorDto;
import com.datlichkhambenh.backend.dto.response.ScheduleDto;
import com.datlichkhambenh.backend.entity.Doctor;
import com.datlichkhambenh.backend.entity.User;
import com.datlichkhambenh.backend.exception.ResourceNotFoundException;
import com.datlichkhambenh.backend.repository.DoctorRepository;
import com.datlichkhambenh.backend.repository.UserRepository;
import com.datlichkhambenh.backend.service.BookingService;
import com.datlichkhambenh.backend.service.DoctorScheduleService;
import com.datlichkhambenh.backend.service.MedicalRecordService;
import com.datlichkhambenh.backend.service.RedisCacheService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorScheduleService doctorScheduleService;
    private final RedisCacheService redisCacheService;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final BookingService bookingService;
    private final MedicalRecordService medicalRecordService;

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .map(User::getId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin tài khoản hiện tại"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<DoctorDto>>> getApprovedDoctors(
            @RequestParam(value = "specialtyId", required = false) Long specialtyId
    ) {
        List<DoctorDto> cached = redisCacheService.getCachedDoctors(specialtyId);
        if (cached != null) {
            return ResponseEntity.ok(ApiResponseDto.success(cached));
        }

        List<Doctor> doctors = (specialtyId != null)
                ? doctorRepository.findBySpecialtyIdAndIsApprovedTrue(specialtyId)
                : doctorRepository.findByIsApprovedTrue();

        List<DoctorDto> result = doctors.stream().map(d -> DoctorDto.builder()
                .userId(d.getUserId())
                .email(d.getUser().getEmail())
                .fullName(d.getFullName())
                .phone(d.getPhone())
                .degree(d.getDegree())
                .experienceYears(d.getExperienceYears())
                .consultationFee(d.getConsultationFee())
                .isApproved(d.getIsApproved())
                .specialtyId(d.getSpecialty() != null ? d.getSpecialty().getId() : null)
                .specialtyName(d.getSpecialty() != null ? d.getSpecialty().getName() : null)
                .build()
        ).collect(Collectors.toList());

        redisCacheService.cacheDoctors(specialtyId, result);
        return ResponseEntity.ok(ApiResponseDto.success(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<DoctorDto>> getDoctorById(@PathVariable("id") Long id) {
        Doctor d = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bác sĩ"));

        DoctorDto dto = DoctorDto.builder()
                .userId(d.getUserId())
                .email(d.getUser().getEmail())
                .fullName(d.getFullName())
                .phone(d.getPhone())
                .degree(d.getDegree())
                .experienceYears(d.getExperienceYears())
                .consultationFee(d.getConsultationFee())
                .isApproved(d.getIsApproved())
                .specialtyId(d.getSpecialty() != null ? d.getSpecialty().getId() : null)
                .specialtyName(d.getSpecialty() != null ? d.getSpecialty().getName() : null)
                .build();

        return ResponseEntity.ok(ApiResponseDto.success(dto));
    }

    @GetMapping("/{id}/slots")
    public ResponseEntity<ApiResponseDto<List<ScheduleDto>>> getAvailableSlots(
            @PathVariable("id") Long doctorId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<ScheduleDto> slots = doctorScheduleService.getAvailableSlots(doctorId, date);
        return ResponseEntity.ok(ApiResponseDto.success(slots));
    }

    @PostMapping("/slots")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<ApiResponseDto<ScheduleDto>> createSlot(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateScheduleDto request
    ) {
        Long doctorUserId = getUserIdFromUserDetails(userDetails);
        ScheduleDto slot = doctorScheduleService.createSlot(doctorUserId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(slot, "Tạo ca khám thành công"));
    }

    @PutMapping("/slots/{id}/toggle")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<ApiResponseDto<Void>> toggleSlot(
            @PathVariable("id") Long scheduleId,
            @RequestParam("available") boolean available
    ) {
        doctorScheduleService.toggleSlotAvailability(scheduleId, available);
        return ResponseEntity.ok(ApiResponseDto.success(null, "Cập nhật trạng thái ca khám thành công"));
    }

    @GetMapping("/my-appointments")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<ApiResponseDto<List<AppointmentDto>>> getMyDoctorAppointments(@AuthenticationPrincipal UserDetails userDetails) {
        Long doctorUserId = getUserIdFromUserDetails(userDetails);
        List<AppointmentDto> appointments = bookingService.getDoctorAppointments(doctorUserId);
        return ResponseEntity.ok(ApiResponseDto.success(appointments));
    }

    @PostMapping("/medical-records")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<ApiResponseDto<Void>> updateMedicalRecord(@Valid @RequestBody UpdateMedicalRecordDto request) {
        medicalRecordService.updateMedicalRecord(request);
        return ResponseEntity.ok(ApiResponseDto.success(null, "Cập nhật kết quả chẩn đoán bệnh thành công"));
    }
}

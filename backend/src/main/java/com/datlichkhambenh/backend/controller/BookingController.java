package com.datlichkhambenh.backend.controller;

import com.datlichkhambenh.backend.dto.request.CreateBookingDto;
import com.datlichkhambenh.backend.dto.response.ApiResponseDto;
import com.datlichkhambenh.backend.dto.response.AppointmentDto;
import com.datlichkhambenh.backend.entity.User;
import com.datlichkhambenh.backend.repository.UserRepository;
import com.datlichkhambenh.backend.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserRepository userRepository;

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .map(User::getId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin tài khoản hiện tại"));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<ApiResponseDto<AppointmentDto>> createBooking(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateBookingDto request
    ) {
        Long patientUserId = getUserIdFromUserDetails(userDetails);
        AppointmentDto appointment = bookingService.createBooking(patientUserId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(appointment, "Đặt lịch khám thành công"));
    }

    @DeleteMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<ApiResponseDto<Void>> cancelBooking(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("id") Long appointmentId
    ) {
        Long patientUserId = getUserIdFromUserDetails(userDetails);
        bookingService.cancelBooking(appointmentId, patientUserId);
        return ResponseEntity.ok(ApiResponseDto.success(null, "Hủy lịch hẹn thành công"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('PATIENT', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<AppointmentDto>> getBookingDetails(@PathVariable("id") Long appointmentId) {
        AppointmentDto appointment = bookingService.getBookingDetails(appointmentId);
        return ResponseEntity.ok(ApiResponseDto.success(appointment));
    }

    @GetMapping("/my-appointments")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<ApiResponseDto<List<AppointmentDto>>> getMyPatientAppointments(@AuthenticationPrincipal UserDetails userDetails) {
        Long patientUserId = getUserIdFromUserDetails(userDetails);
        List<AppointmentDto> appointments = bookingService.getPatientAppointments(patientUserId);
        return ResponseEntity.ok(ApiResponseDto.success(appointments));
    }
}

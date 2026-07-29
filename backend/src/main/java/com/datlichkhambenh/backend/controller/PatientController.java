package com.datlichkhambenh.backend.controller;

import com.datlichkhambenh.backend.dto.response.ApiResponseDto;
import com.datlichkhambenh.backend.dto.response.MedicalRecordDto;
import com.datlichkhambenh.backend.dto.response.PatientDto;
import com.datlichkhambenh.backend.entity.User;
import com.datlichkhambenh.backend.repository.UserRepository;
import com.datlichkhambenh.backend.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final UserRepository userRepository;

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .map(User::getId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin tài khoản hiện tại"));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<ApiResponseDto<PatientDto>> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Long patientUserId = getUserIdFromUserDetails(userDetails);
        PatientDto profile = patientService.getProfile(patientUserId);
        return ResponseEntity.ok(ApiResponseDto.success(profile));
    }

    @PutMapping("/me")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<ApiResponseDto<PatientDto>> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PatientDto request
    ) {
        Long patientUserId = getUserIdFromUserDetails(userDetails);
        PatientDto updated = patientService.updateProfile(patientUserId, request);
        return ResponseEntity.ok(ApiResponseDto.success(updated, "Cập nhật hồ sơ bệnh nhân thành công"));
    }

    @GetMapping("/me/medical-records")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<ApiResponseDto<List<MedicalRecordDto>>> getMyMedicalRecords(@AuthenticationPrincipal UserDetails userDetails) {
        Long patientUserId = getUserIdFromUserDetails(userDetails);
        List<MedicalRecordDto> records = patientService.getPatientMedicalRecords(patientUserId);
        return ResponseEntity.ok(ApiResponseDto.success(records));
    }
}

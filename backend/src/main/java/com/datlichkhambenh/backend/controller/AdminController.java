package com.datlichkhambenh.backend.controller;

import com.datlichkhambenh.backend.dto.request.SpecialtyRequestDto;
import com.datlichkhambenh.backend.dto.response.ApiResponseDto;
import com.datlichkhambenh.backend.dto.response.DoctorDto;
import com.datlichkhambenh.backend.dto.response.SpecialtyDto;
import com.datlichkhambenh.backend.service.AdminManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final AdminManagementService adminManagementService;

    @GetMapping("/doctors/pending")
    public ResponseEntity<ApiResponseDto<List<DoctorDto>>> getPendingDoctors() {
        List<DoctorDto> doctors = adminManagementService.getPendingDoctors();
        return ResponseEntity.ok(ApiResponseDto.success(doctors));
    }

    @PutMapping("/doctors/{id}/approve")
    public ResponseEntity<ApiResponseDto<Void>> approveDoctor(@PathVariable("id") Long doctorUserId) {
        adminManagementService.approveDoctor(doctorUserId);
        return ResponseEntity.ok(ApiResponseDto.success(null, "Phê duyệt bác sĩ thành công"));
    }

    @PutMapping("/doctors/{id}/reject")
    public ResponseEntity<ApiResponseDto<Void>> rejectDoctor(@PathVariable("id") Long doctorUserId) {
        adminManagementService.rejectDoctor(doctorUserId);
        return ResponseEntity.ok(ApiResponseDto.success(null, "Từ chối bác sĩ thành công"));
    }

    @PostMapping("/specialties")
    public ResponseEntity<ApiResponseDto<SpecialtyDto>> createSpecialty(@Valid @RequestBody SpecialtyRequestDto request) {
        SpecialtyDto specialty = adminManagementService.createSpecialty(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(specialty, "Tạo chuyên khoa mới thành công"));
    }

    @GetMapping("/specialties")
    public ResponseEntity<ApiResponseDto<List<SpecialtyDto>>> getAllSpecialties() {
        List<SpecialtyDto> specialties = adminManagementService.getAllSpecialties();
        return ResponseEntity.ok(ApiResponseDto.success(specialties));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> getSystemStats() {
        Map<String, Object> stats = adminManagementService.getSystemStats();
        return ResponseEntity.ok(ApiResponseDto.success(stats));
    }
}

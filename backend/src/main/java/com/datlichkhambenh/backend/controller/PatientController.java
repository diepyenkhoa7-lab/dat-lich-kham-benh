package com.datlichkhambenh.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PatientController — Hồ sơ bệnh nhân.
 * Base path: /api/v1/patients
 *
 * Yêu cầu JWT + role PATIENT (hoặc ADMIN).
 * Controller chỉ nhận request, validate DTO, gọi PatientService — KHÔNG viết business logic.
 *
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    // TODO: inject PatientService
    // TODO: GET  /me             → patientService.getProfile(currentUserId)
    // TODO: PUT  /me             → patientService.updateProfile(currentUserId, request)
    // TODO: GET  /me/appointments → appointmentService (hoặc BookingService)
    // TODO: GET  /me/records      → medicalRecordService
}

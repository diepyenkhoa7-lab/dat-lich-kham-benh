package com.datlichkhambenh.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DoctorController — Quản lý bác sĩ và lịch làm việc.
 * Base path: /api/v1/doctors
 *
 * Yêu cầu JWT + role DOCTOR (endpoint cá nhân) hoặc public (tìm kiếm bác sĩ).
 * Controller gọi DoctorScheduleService + RedisCacheService — KHÔNG viết business logic.
 *
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    // TODO: inject DoctorScheduleService, RedisCacheService
    // TODO: GET  /              → danh sách bác sĩ đã approved (cache Redis)
    // TODO: GET  /{id}          → thông tin bác sĩ
    // TODO: GET  /{id}/slots    → slot rảnh theo ngày (cache Redis)
    // TODO: POST /slots         → tạo slot (DOCTOR only)
    // TODO: PUT  /slots/{id}    → bật/tắt slot (DOCTOR only)
}

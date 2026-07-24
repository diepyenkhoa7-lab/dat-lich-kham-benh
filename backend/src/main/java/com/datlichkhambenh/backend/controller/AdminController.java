package com.datlichkhambenh.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController — Quản trị hệ thống.
 * Base path: /api/v1/admin
 *
 * Yêu cầu JWT + role ADMIN (tất cả endpoint đều protected).
 * Controller gọi AdminManagementService — KHÔNG viết business logic.
 *
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    // TODO: inject AdminManagementService
    // TODO: GET  /doctors              → danh sách bác sĩ chờ duyệt
    // TODO: PUT  /doctors/{id}/approve → adminService.approveDoctor(id)
    // TODO: PUT  /doctors/{id}/reject  → adminService.rejectDoctor(id)
    // TODO: CRUD /specialties          → adminService.createSpecialty / update / delete
    // TODO: GET  /reports/revenue      → adminService.getRevenueReport(from, to)
}

package com.datlichkhambenh.backend.service;

/**
 * AdminManagementService — Tầng Service quản trị hệ thống.
 *
 * Trách nhiệm:
 * - Phê duyệt / từ chối đăng ký bác sĩ (is_approved)
 * - CRUD chuyên khoa (Specialty)
 * - Quản lý người dùng (activate / deactivate)
 * - Thống kê báo cáo doanh thu / ca khám
 *
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
public interface AdminManagementService {
    // TODO: void approveDoctor(Long doctorId);
    // TODO: void rejectDoctor(Long doctorId);
    // TODO: SpecialtyDto createSpecialty(CreateSpecialtyDto request);
    // TODO: ReportDto getRevenueReport(LocalDate from, LocalDate to);
}

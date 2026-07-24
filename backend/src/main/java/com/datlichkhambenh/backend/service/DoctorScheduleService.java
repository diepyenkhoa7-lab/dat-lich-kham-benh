package com.datlichkhambenh.backend.service;

/**
 * DoctorScheduleService — Tầng Service quản lý lịch làm việc bác sĩ.
 *
 * Trách nhiệm:
 * - Tạo / bật / tắt slot làm việc (Schedule)
 * - Lấy danh sách slot rảnh của bác sĩ (ưu tiên Redis cache → fallback DB)
 * - Tích hợp với RedisCacheService để ghi/xóa cache khi slot thay đổi
 *
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
public interface DoctorScheduleService {
    // TODO: ScheduleDto createSlot(Long doctorId, CreateScheduleDto request);
    // TODO: List<ScheduleDto> getAvailableSlots(Long doctorId, LocalDate date);
    // TODO: void toggleSlotAvailability(Long scheduleId, boolean available);
}

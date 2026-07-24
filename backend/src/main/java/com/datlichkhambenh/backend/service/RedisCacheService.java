package com.datlichkhambenh.backend.service;

/**
 * RedisCacheService — Tầng Service đọc/ghi Redis Cache.
 *
 * Trách nhiệm:
 * - Cache danh sách bác sĩ theo chuyên khoa (TTL configurable)
 * - Cache danh sách slot rảnh theo bác sĩ + ngày (TTL ngắn hơn)
 * - Xóa cache khi dữ liệu thay đổi (invalidate on write)
 *
 * Pattern: Cache-Aside
 *   1. Đọc từ Redis trước
 *   2. Nếu cache miss → query DB → ghi lại vào Redis
 *   3. Khi cập nhật DB → xóa key cache liên quan
 *
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
public interface RedisCacheService {
    // TODO: List<DoctorDto> getCachedDoctors(Long specialtyId);
    // TODO: void cacheDoctors(Long specialtyId, List<DoctorDto> doctors);
    // TODO: void evictDoctorCache(Long specialtyId);
    // TODO: List<ScheduleDto> getCachedAvailableSlots(Long doctorId, LocalDate date);
    // TODO: void cacheAvailableSlots(Long doctorId, LocalDate date, List<ScheduleDto> slots);
    // TODO: void evictSlotCache(Long doctorId, LocalDate date);
}

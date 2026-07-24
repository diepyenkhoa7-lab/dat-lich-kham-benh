package com.datlichkhambenh.backend.service;

/**
 * BookingService — Xử lý đặt / hủy lịch khám.
 * Triển khai: BookingServiceImpl.java (tách Interface + Impl vì nghiệp vụ phức tạp)
 *
 * LUỒNG ĐẶT LỊCH BẮT BUỘC (theo architecture.md):
 * 1. Lấy Schedule bằng findByIdForUpdate() — Pessimistic Write Lock
 * 2. Kiểm tra slot còn trống (is_available = true)
 * 3. Tạo Appointment (booking_code = UUID random)
 * 4. Đánh dấu Schedule.isAvailable = false
 * 5. Trigger sự kiện gửi email → đẩy vào async task (KHÔNG gửi đồng bộ)
 *
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
public interface BookingService {
    // TODO: AppointmentDto createBooking(Long patientId, CreateBookingDto request);
    // TODO: void cancelBooking(Long appointmentId, Long patientId);
}

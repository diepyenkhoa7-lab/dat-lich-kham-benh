package com.datlichkhambenh.backend.service;

/**
 * AsyncEmailNotificationService — Gửi email xác nhận / nhắc lịch bất đồng bộ.
 *
 * QUAN TRỌNG (theo architecture.md):
 * - KHÔNG gọi trực tiếp trong luồng đặt lịch (tránh block response)
 * - Được trigger sau khi BookingServiceImpl hoàn thành ghi Appointment
 * - Xử lý bất đồng bộ qua @Async (Spring Task Executor)
 *   hoặc message queue tuỳ scale yêu cầu
 *
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
public interface AsyncEmailNotificationService {
    // TODO: void sendBookingConfirmation(Long appointmentId);
    // TODO: void sendAppointmentReminder(Long appointmentId);
    // TODO: void sendCancellationNotice(Long appointmentId);
}

package com.datlichkhambenh.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BookingController — Đặt / hủy lịch khám.
 * Base path: /api/v1/appointments
 *
 * Yêu cầu JWT + role PATIENT (đặt/hủy) hoặc DOCTOR/ADMIN (xem trạng thái).
 * Controller gọi BookingService — KHÔNG viết business logic.
 *
 * LUỒNG ĐẶT LỊCH (thực thi trong BookingServiceImpl):
 *   Pessimistic Lock → Tạo Appointment → Trigger async email
 *
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class BookingController {

    // TODO: inject BookingService
    // TODO: POST /          → bookingService.createBooking(patientId, request)  [PATIENT]
    // TODO: DELETE /{id}    → bookingService.cancelBooking(appointmentId, patientId) [PATIENT]
    // TODO: GET  /{id}      → lấy chi tiết lịch hẹn [PATIENT / DOCTOR / ADMIN]
}

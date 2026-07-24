package com.datlichkhambenh.backend.repository;

import com.datlichkhambenh.backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng APPOINTMENTS.
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Tìm lịch hẹn theo booking_code (mã đặt chỗ duy nhất)
    Optional<Appointment> findByBookingCode(String bookingCode);

    // Lịch sử đặt khám của bệnh nhân (xem lịch sử / hủy lịch)
    List<Appointment> findByPatientUserId(Long patientId);

    // Danh sách bệnh nhân đã khám của bác sĩ
    List<Appointment> findByDoctorUserId(Long doctorId);

    // Kiểm tra slot đã bị đặt chưa (trước khi lock)
    boolean existsByScheduleIdAndStatusNot(Long scheduleId, String status);
}

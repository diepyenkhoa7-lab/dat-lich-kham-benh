package com.datlichkhambenh.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entity ánh xạ bảng SCHEDULES.
 * Mỗi bản ghi là 1 slot làm việc của bác sĩ trong 1 ngày.
 *
 * LƯU Ý QUAN TRỌNG (Pessimistic Lock):
 * Khi BookingServiceImpl muốn đặt lịch, phải dùng
 * @Lock(LockModeType.PESSIMISTIC_WRITE) trên query lấy Schedule
 * để tránh 2 bệnh nhân đặt cùng 1 slot (race condition).
 */
@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** FK → DOCTORS.user_id (1 bác sĩ có nhiều slot). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "doctor_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_schedules_doctor")
    )
    private Doctor doctor;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    /** true = slot còn trống, false = đã được đặt. */
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;
}

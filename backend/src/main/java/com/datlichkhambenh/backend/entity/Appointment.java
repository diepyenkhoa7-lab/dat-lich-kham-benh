package com.datlichkhambenh.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity ánh xạ bảng APPOINTMENTS.
 * Lịch hẹn khám giữa bệnh nhân và bác sĩ trên 1 slot.
 * booking_code phải UNIQUE ở tầng DB.
 */
@Entity
@Table(
    name = "appointments",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_appointments_booking_code", columnNames = "booking_code"),
        @UniqueConstraint(name = "uk_appointments_schedule",     columnNames = "schedule_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "booking_code", nullable = false, unique = true, length = 100)
    private String bookingCode;

    /** FK → PATIENTS.user_id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "patient_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_appointments_patient")
    )
    private Patient patient;

    /** FK → DOCTORS.user_id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "doctor_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_appointments_doctor")
    )
    private Doctor doctor;

    /** FK → SCHEDULES.id — quan hệ 1-1 (1 slot chỉ có 1 appointment). */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "schedule_id",
        nullable = false,
        unique = true,
        foreignKey = @ForeignKey(name = "fk_appointments_schedule")
    )
    private Schedule schedule;

    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms;

    /** CONFIRMED / COMPLETED / CANCELLED */
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

package com.datlichkhambenh.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity ánh xạ bảng MEDICAL_RECORDS.
 * Mỗi lịch hẹn hoàn thành có tối đa 1 hồ sơ khám (UNIQUE trên appointment_id).
 */
@Entity
@Table(
    name = "medical_records",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_medical_records_appointment",
        columnNames = "appointment_id"
    )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** FK → APPOINTMENTS.id — UNIQUE: mỗi appointment chỉ có 1 hồ sơ. */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "appointment_id",
        nullable = false,
        unique = true,
        foreignKey = @ForeignKey(name = "fk_medical_records_appointment")
    )
    private Appointment appointment;

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "doctor_notes", columnDefinition = "TEXT")
    private String doctorNotes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

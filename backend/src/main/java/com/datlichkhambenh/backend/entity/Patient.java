package com.datlichkhambenh.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entity ánh xạ bảng PATIENTS.
 * user_id vừa là PK vừa là FK về USERS.id — dùng @MapsId,
 * KHÔNG tạo thêm cột id riêng cho bảng này.
 */
@Entity
@Table(
    name = "patients",
    uniqueConstraints = @UniqueConstraint(name = "uk_patients_phone", columnNames = "phone")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    /**
     * PK = FK → USERS.id.
     * @MapsId: giá trị id được lấy từ user.id (không auto-generate riêng).
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_patients_user"))
    private User user;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(name = "dob")
    private LocalDate dob;

    /** MALE / FEMALE / OTHER */
    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "phone", unique = true, length = 20)
    private String phone;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "medical_history", columnDefinition = "TEXT")
    private String medicalHistory;
}

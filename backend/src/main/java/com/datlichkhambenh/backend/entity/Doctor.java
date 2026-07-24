package com.datlichkhambenh.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entity ánh xạ bảng DOCTORS.
 * user_id vừa là PK vừa là FK về USERS.id — dùng @MapsId,
 * KHÔNG tạo thêm cột id riêng cho bảng này.
 */
@Entity
@Table(
    name = "doctors",
    uniqueConstraints = @UniqueConstraint(name = "uk_doctors_phone", columnNames = "phone")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    /**
     * PK = FK → USERS.id.
     * @MapsId: giá trị id lấy từ user.id, không auto-generate riêng.
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_doctors_user"))
    private User user;

    /** Quan hệ N-1: nhiều bác sĩ cùng 1 chuyên khoa. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "specialty_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_doctors_specialty")
    )
    private Specialty specialty;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(name = "phone", unique = true, length = 20)
    private String phone;

    @Column(name = "degree", length = 100)
    private String degree;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "consultation_fee", precision = 12, scale = 2)
    private BigDecimal consultationFee;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;
}

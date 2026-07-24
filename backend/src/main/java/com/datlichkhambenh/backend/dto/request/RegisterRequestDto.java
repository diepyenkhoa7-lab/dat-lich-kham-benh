package com.datlichkhambenh.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO nhận request đăng ký tài khoản mới.
 * Dùng cho cả PATIENT và DOCTOR — field role xác định loại tài khoản.
 * Validate bằng @Valid tại AuthController.
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
@Data
public class RegisterRequestDto {

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    /** PATIENT hoặc DOCTOR */
    @NotBlank(message = "Role không được để trống")
    @Pattern(regexp = "PATIENT|DOCTOR", message = "Role phải là PATIENT hoặc DOCTOR")
    private String role;

    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    private String phone;
    private LocalDate dob;

    /** MALE / FEMALE / OTHER — chỉ dùng cho PATIENT */
    private String gender;

    /** Chỉ dùng cho DOCTOR */
    private Long specialtyId;
    private String degree;
    private Integer experienceYears;
}

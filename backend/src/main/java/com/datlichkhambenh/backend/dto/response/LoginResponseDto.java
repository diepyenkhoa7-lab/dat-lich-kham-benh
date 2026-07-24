package com.datlichkhambenh.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO trả về sau khi đăng nhập thành công.
 * Chứa JWT access token và thông tin cơ bản.
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private String accessToken;
    private String tokenType = "Bearer";
    private long expiresIn;  // seconds
    private String role;
    private Long userId;
    private String email;
}

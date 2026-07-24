package com.datlichkhambenh.backend.service;

/**
 * AuthService — Tầng Service xử lý xác thực.
 * Triển khai: AuthServiceImpl.java
 *
 * Trách nhiệm:
 * - Đăng ký tài khoản (hash password bằng BCrypt, tạo User + Patient/Doctor)
 * - Đăng nhập (kiểm tra credentials, tạo JWT access token + refresh token)
 * - Làm mới token (refresh token flow)
 * - Đăng xuất (invalidate token nếu dùng blacklist Redis)
 *
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
public interface AuthService {
    // TODO: LoginResponseDto login(LoginRequestDto request);
    // TODO: void register(RegisterRequestDto request);
    // TODO: LoginResponseDto refreshToken(String refreshToken);
}

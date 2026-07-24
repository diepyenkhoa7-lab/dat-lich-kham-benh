package com.datlichkhambenh.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController — Xử lý đăng ký, đăng nhập, cấp token.
 * Base path: /api/v1/auth
 *
 * Endpoint này KHÔNG yêu cầu JWT (public endpoint).
 * Controller chỉ nhận request, validate DTO, gọi AuthService — KHÔNG viết business logic.
 *
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    // TODO: inject AuthService
    // TODO: POST /register  → authService.register(request)
    // TODO: POST /login     → authService.login(request)
    // TODO: POST /refresh   → authService.refreshToken(token)
}

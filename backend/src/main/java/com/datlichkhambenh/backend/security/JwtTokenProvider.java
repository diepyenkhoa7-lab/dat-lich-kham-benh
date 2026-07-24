package com.datlichkhambenh.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JwtTokenProvider — Sinh và xác thực JWT token.
 *
 * Cấu trúc token: Header.Payload.Signature
 * - Payload chứa: subject (email), role, expiration
 * - Signature = HMAC-SHA256(Header + Payload, SECRET_KEY)
 *
 * Logic nghiệp vụ đầy đủ sẽ được bổ sung ở bước tiếp theo.
 * Hiện tại chỉ định nghĩa cấu trúc và inject config.
 */
@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    // TODO: generateToken(String email, String role) → String
    // TODO: getEmailFromToken(String token)          → String
    // TODO: getRoleFromToken(String token)           → String
    // TODO: validateToken(String token)              → boolean
    // TODO: extractClaims(String token)              → Claims

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}

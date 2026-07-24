package com.datlichkhambenh.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtTokenFilter -- Interceptor xac thuc JWT cho moi request.
 * Dat trong Security pipeline TRUOC UsernamePasswordAuthenticationFilter.
 *
 * Pipeline (theo architecture.md):
 *   Client -> JwtTokenFilter -> Role check -> Controller
 *
 * Quy trinh xu ly:
 * 1. Doc header Authorization: Bearer <token>
 * 2. Validate token qua JwtTokenProvider
 * 3. Load UserDetails tu DB hoac cache
 * 4. Set Authentication vao SecurityContext
 *
 * Logic day du se duoc bo sung o buoc tiep theo.
 */
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // TODO: Extract token from Authorization header
        // TODO: Validate token với jwtTokenProvider.validateToken()
        // TODO: Load user từ userDetailsService.loadUserByUsername(email)
        // TODO: Set UsernamePasswordAuthenticationToken vào SecurityContextHolder
        filterChain.doFilter(request, response);
    }

    /**
     * Bỏ qua filter cho các endpoint public (/api/v1/auth/**).
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/v1/auth/");
    }
}

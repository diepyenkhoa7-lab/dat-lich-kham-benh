package com.datlichkhambenh.backend.service.impl;

import com.datlichkhambenh.backend.dto.request.LoginRequestDto;
import com.datlichkhambenh.backend.dto.request.RefreshTokenRequestDto;
import com.datlichkhambenh.backend.dto.request.RegisterRequestDto;
import com.datlichkhambenh.backend.dto.response.LoginResponseDto;
import com.datlichkhambenh.backend.dto.response.UserDto;
import com.datlichkhambenh.backend.entity.Doctor;
import com.datlichkhambenh.backend.entity.Patient;
import com.datlichkhambenh.backend.entity.Specialty;
import com.datlichkhambenh.backend.entity.User;
import com.datlichkhambenh.backend.exception.AppException;
import com.datlichkhambenh.backend.exception.ResourceNotFoundException;
import com.datlichkhambenh.backend.repository.DoctorRepository;
import com.datlichkhambenh.backend.repository.PatientRepository;
import com.datlichkhambenh.backend.repository.SpecialtyRepository;
import com.datlichkhambenh.backend.repository.UserRepository;
import com.datlichkhambenh.backend.security.JwtTokenProvider;
import com.datlichkhambenh.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public UserDto register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException("Email đã tồn tại trên hệ thống");
        }

        String role = request.getRole().toUpperCase();
        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        if ("PATIENT".equals(role)) {
            Patient patient = Patient.builder()
                    .user(savedUser)
                    .fullName(request.getFullName())
                    .phone(request.getPhone())
                    .dob(request.getDob())
                    .gender(request.getGender())
                    .address(request.getAddress())
                    .medicalHistory(request.getMedicalHistory())
                    .build();
            patientRepository.save(patient);
        } else if ("DOCTOR".equals(role)) {
            if (request.getSpecialtyId() == null) {
                throw new AppException("Bác sĩ phải đăng ký thuộc 1 chuyên khoa (specialtyId)");
            }
            Specialty specialty = specialtyRepository.findById(request.getSpecialtyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chuyên khoa"));

            Doctor doctor = Doctor.builder()
                    .user(savedUser)
                    .specialty(specialty)
                    .fullName(request.getFullName())
                    .phone(request.getPhone())
                    .degree(request.getDegree())
                    .experienceYears(request.getExperienceYears())
                    .consultationFee(request.getConsultationFee())
                    .isApproved(false)
                    .build();
            doctorRepository.save(doctor);
        }

        return UserDto.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .isActive(savedUser.getIsActive())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return LoginResponseDto.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public LoginResponseDto refreshToken(RefreshTokenRequestDto request) {
        String token = request.getRefreshToken();
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AppException("Refresh Token không hợp lệ hoặc đã hết hạn");
        }

        String email = jwtTokenProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));

        String newToken = jwtTokenProvider.generateToken(user.getEmail(), user.getRole());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return LoginResponseDto.builder()
                .accessToken(newToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}

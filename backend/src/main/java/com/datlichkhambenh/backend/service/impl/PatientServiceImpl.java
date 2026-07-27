package com.datlichkhambenh.backend.service.impl;

import com.datlichkhambenh.backend.dto.response.MedicalRecordDto;
import com.datlichkhambenh.backend.dto.response.PatientDto;
import com.datlichkhambenh.backend.entity.MedicalRecord;
import com.datlichkhambenh.backend.entity.Patient;
import com.datlichkhambenh.backend.exception.ResourceNotFoundException;
import com.datlichkhambenh.backend.repository.MedicalRecordRepository;
import com.datlichkhambenh.backend.repository.PatientRepository;
import com.datlichkhambenh.backend.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    @Transactional(readOnly = true)
    public PatientDto getProfile(Long patientUserId) {
        Patient patient = patientRepository.findById(patientUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Hồ sơ bệnh nhân không tồn tại"));
        return mapToDto(patient);
    }

    @Override
    @Transactional
    public PatientDto updateProfile(Long patientUserId, PatientDto request) {
        Patient patient = patientRepository.findById(patientUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Hồ sơ bệnh nhân không tồn tại"));

        if (request.getFullName() != null) patient.setFullName(request.getFullName());
        if (request.getPhone() != null) patient.setPhone(request.getPhone());
        if (request.getDob() != null) patient.setDob(request.getDob());
        if (request.getGender() != null) patient.setGender(request.getGender());
        if (request.getAddress() != null) patient.setAddress(request.getAddress());
        if (request.getMedicalHistory() != null) patient.setMedicalHistory(request.getMedicalHistory());

        Patient saved = patientRepository.save(patient);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordDto> getPatientMedicalRecords(Long patientUserId) {
        List<MedicalRecord> records = medicalRecordRepository.findByAppointmentPatientUserId(patientUserId);
        return records.stream().map(r -> MedicalRecordDto.builder()
                .id(r.getId())
                .appointmentId(r.getAppointment().getId())
                .bookingCode(r.getAppointment().getBookingCode())
                .patientName(r.getAppointment().getPatient().getFullName())
                .doctorName(r.getAppointment().getDoctor().getFullName())
                .diagnosis(r.getDiagnosis())
                .doctorNotes(r.getDoctorNotes())
                .createdAt(r.getCreatedAt())
                .build()
        ).collect(Collectors.toList());
    }

    private PatientDto mapToDto(Patient patient) {
        return PatientDto.builder()
                .userId(patient.getUserId())
                .email(patient.getUser().getEmail())
                .fullName(patient.getFullName())
                .phone(patient.getPhone())
                .dob(patient.getDob())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .medicalHistory(patient.getMedicalHistory())
                .build();
    }
}

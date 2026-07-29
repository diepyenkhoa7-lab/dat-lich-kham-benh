package com.datlichkhambenh.backend.service;

import com.datlichkhambenh.backend.dto.response.MedicalRecordDto;
import com.datlichkhambenh.backend.dto.response.PatientDto;

import java.util.List;

public interface PatientService {
    PatientDto getProfile(Long patientUserId);
    PatientDto updateProfile(Long patientUserId, PatientDto request);
    List<MedicalRecordDto> getPatientMedicalRecords(Long patientUserId);
}

package com.datlichkhambenh.backend.service;

import com.datlichkhambenh.backend.dto.request.SpecialtyRequestDto;
import com.datlichkhambenh.backend.dto.response.DoctorDto;
import com.datlichkhambenh.backend.dto.response.SpecialtyDto;

import java.util.List;
import java.util.Map;

public interface AdminManagementService {
    void approveDoctor(Long doctorUserId);
    void rejectDoctor(Long doctorUserId);
    SpecialtyDto createSpecialty(SpecialtyRequestDto request);
    List<SpecialtyDto> getAllSpecialties();
    List<DoctorDto> getPendingDoctors();
    Map<String, Object> getSystemStats();
}

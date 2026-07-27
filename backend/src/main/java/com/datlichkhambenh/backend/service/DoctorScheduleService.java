package com.datlichkhambenh.backend.service;

import com.datlichkhambenh.backend.dto.request.CreateScheduleDto;
import com.datlichkhambenh.backend.dto.response.ScheduleDto;

import java.time.LocalDate;
import java.util.List;

public interface DoctorScheduleService {
    ScheduleDto createSlot(Long doctorUserId, CreateScheduleDto request);
    List<ScheduleDto> getAvailableSlots(Long doctorUserId, LocalDate date);
    void toggleSlotAvailability(Long scheduleId, boolean available);
}

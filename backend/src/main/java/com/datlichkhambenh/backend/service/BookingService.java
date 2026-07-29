package com.datlichkhambenh.backend.service;

import com.datlichkhambenh.backend.dto.request.CreateBookingDto;
import com.datlichkhambenh.backend.dto.response.AppointmentDto;

import java.util.List;

public interface BookingService {
    AppointmentDto createBooking(Long patientUserId, CreateBookingDto request);
    void cancelBooking(Long appointmentId, Long patientUserId);
    AppointmentDto getBookingDetails(Long appointmentId);
    List<AppointmentDto> getPatientAppointments(Long patientUserId);
    List<AppointmentDto> getDoctorAppointments(Long doctorUserId);
}

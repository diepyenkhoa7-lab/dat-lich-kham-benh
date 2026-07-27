package com.datlichkhambenh.backend.service.impl;

import com.datlichkhambenh.backend.entity.Appointment;
import com.datlichkhambenh.backend.service.AsyncEmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncEmailNotificationServiceImpl implements AsyncEmailNotificationService {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendBookingConfirmation(Appointment appointment) {
        log.info("[Async Mail] Processing confirmation email for booking: {}", appointment.getBookingCode());
        try {
            String recipientEmail = appointment.getPatient().getUser().getEmail();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("Xác nhận đặt lịch khám bệnh - Mã: " + appointment.getBookingCode());
            message.setText(String.format(
                    "Kính gửi %s,\n\nBạn đã đặt lịch khám thành công!\n" +
                    "Mã đặt chỗ: %s\n" +
                    "Bác sĩ phụ trách: %s\n" +
                    "Ngày khám: %s\n" +
                    "Thời gian: %s - %s\n" +
                    "Phí khám: %s VNĐ\n\nCảm ơn bạn đã lựa chọn dịch vụ của chúng tôi!",
                    appointment.getPatient().getFullName(),
                    appointment.getBookingCode(),
                    appointment.getDoctor().getFullName(),
                    appointment.getSchedule().getWorkDate(),
                    appointment.getSchedule().getStartTime(),
                    appointment.getSchedule().getEndTime(),
                    appointment.getPrice()
            ));
            mailSender.send(message);
            log.info("[Async Mail] Confirmation email sent successfully to {}", recipientEmail);
        } catch (Exception e) {
            log.warn("[Async Mail] Failed to send email via SMTP (using fallback log mode): {}", e.getMessage());
        }
    }

    @Async
    @Override
    public void sendAppointmentReminder(Appointment appointment) {
        log.info("[Async Mail] Processing reminder email for booking: {}", appointment.getBookingCode());
        try {
            String recipientEmail = appointment.getPatient().getUser().getEmail();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("Nhắc nhở lịch khám - Mã: " + appointment.getBookingCode());
            message.setText(String.format(
                    "Kính gửi %s,\n\nBạn có lịch khám sắp tới vào ngày %s (%s - %s) với bác sĩ %s.\nVui lòng đến đúng giờ!",
                    appointment.getPatient().getFullName(),
                    appointment.getSchedule().getWorkDate(),
                    appointment.getSchedule().getStartTime(),
                    appointment.getSchedule().getEndTime(),
                    appointment.getDoctor().getFullName()
            ));
            mailSender.send(message);
            log.info("[Async Mail] Reminder email sent to {}", recipientEmail);
        } catch (Exception e) {
            log.warn("[Async Mail] Failed to send reminder email: {}", e.getMessage());
        }
    }

    @Async
    @Override
    public void sendCancellationNotice(Appointment appointment) {
        log.info("[Async Mail] Processing cancellation email for booking: {}", appointment.getBookingCode());
        try {
            String recipientEmail = appointment.getPatient().getUser().getEmail();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("Thông báo hủy lịch khám - Mã: " + appointment.getBookingCode());
            message.setText(String.format(
                    "Kính gửi %s,\n\nLịch khám mã %s đã được hủy thành công.",
                    appointment.getPatient().getFullName(),
                    appointment.getBookingCode()
            ));
            mailSender.send(message);
            log.info("[Async Mail] Cancellation notice sent to {}", recipientEmail);
        } catch (Exception e) {
            log.warn("[Async Mail] Failed to send cancellation email: {}", e.getMessage());
        }
    }
}

package com.datlichkhambenh.backend.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO nhận request đặt lịch khám.
 * Validate bằng @Valid tại BookingController.
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
@Data
public class CreateBookingDto {

    @NotNull(message = "ID slot (schedule) không được để trống")
    private Long scheduleId;

    /** Triệu chứng / lý do khám của bệnh nhân */
    private String symptoms;
}

package com.docdock.group09.appointment_service.dto.request;

import com.docdock.group09.appointment_service.constant.AppointmentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentUpdateRequest {
    private String action;
    private String cancelReason;
    private String reason;
    private AppointmentType type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}

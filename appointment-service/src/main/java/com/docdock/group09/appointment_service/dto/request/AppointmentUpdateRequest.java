package com.docdock.group09.appointment_service.dto.request;

import com.docdock.group09.appointment_service.constant.AppointmentType;
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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

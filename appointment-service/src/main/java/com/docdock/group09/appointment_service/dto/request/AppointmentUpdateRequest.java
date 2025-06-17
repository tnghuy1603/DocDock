package com.docdock.group09.appointment_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentUpdateRequest {
    private String action;
    private String cancelReason;
    private String appointmentId;
}

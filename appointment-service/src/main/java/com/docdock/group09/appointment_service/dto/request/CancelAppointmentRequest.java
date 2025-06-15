package com.docdock.group09.appointment_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CancelAppointmentRequest {
    String cancelReason;
    String initiatorId;
}

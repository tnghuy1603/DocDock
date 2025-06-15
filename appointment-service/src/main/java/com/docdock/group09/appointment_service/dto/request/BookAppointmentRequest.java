package com.docdock.group09.appointment_service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class BookAppointmentRequest {
    private String patientId;
    private String doctorId;
    private String type;
    private String reason;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

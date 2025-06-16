package com.docdock.group09.appointment_service.dto.response;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import com.docdock.group09.appointment_service.constant.AppointmentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentResponse {
    private String id;
    private String patientId;
    private String doctorId;
    private String description;
    private AppointmentStatus status;
    private AppointmentType type;
    private String reason;
    private String cancelReason;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

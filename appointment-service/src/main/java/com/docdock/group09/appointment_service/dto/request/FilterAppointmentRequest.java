package com.docdock.group09.appointment_service.dto.request;

import com.docdock.group09.appointment_service.constant.AppointmentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FilterAppointmentRequest {
    private String doctorId;
    private String patientName;
    private String patientId;
    private List<String> patientIds;
    private String reason;
    private AppointmentType type;
    private LocalDate appointmentDate;
}

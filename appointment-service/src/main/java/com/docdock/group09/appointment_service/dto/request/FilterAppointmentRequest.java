package com.docdock.group09.appointment_service.dto.request;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import com.docdock.group09.appointment_service.constant.AppointmentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FilterAppointmentRequest {
    private String patientName;
    private String doctorName;
    private String patientId;
    private String doctorId;
    private String reason;
    private String appointmentId;
    private List<String> patientIds;
    private AppointmentType type;
    private AppointmentStatus status;

    private LocalDate date;
    private int offset = 0;
    private int limit = 8;

}

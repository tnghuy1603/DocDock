package com.docdock.group09.medical_record_service.dto.request;

import com.docdock.group09.medical_record_service.entity.VisitType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class FilterMedicalRecordRequest {
    private String patientName;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private String doctorName;
    private LocalDate from;
    private VisitType visitType;
    private LocalDate to;
    private int limit = 8;
    private int offset = 0;
}

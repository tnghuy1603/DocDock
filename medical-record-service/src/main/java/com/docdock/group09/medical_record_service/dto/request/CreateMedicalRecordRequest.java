package com.docdock.group09.medical_record_service.dto.request;

import com.docdock.group09.medical_record_service.entity.VisitType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class CreateMedicalRecordRequest {
    private String patientId;
    private String doctorId;
    private VisitType visitType;
    private String diagnosis;
    private String treatment;
    private String description;
    private LocalDate visitDate;
}

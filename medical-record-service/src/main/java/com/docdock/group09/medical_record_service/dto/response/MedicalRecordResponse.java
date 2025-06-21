package com.docdock.group09.medical_record_service.dto.response;

import com.docdock.group09.medical_record_service.entity.VisitType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Setter
@Getter
public class MedicalRecordResponse {
    private String id;
    private String patientId;
    private String patientName;
    private String patientPhoneNumber;
    private String doctorId;
    private String doctorName;
    private String treatment;
    private String diagnosis;
    private String description;
    private LocalDate visitDate;
    private VisitType visitType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

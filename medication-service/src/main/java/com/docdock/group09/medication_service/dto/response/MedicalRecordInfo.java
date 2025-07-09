package com.docdock.group09.medication_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalRecordInfo {
    private String id;
    private String patientId;
    private String patientName;
    private String doctorId;
    private String doctorName;
    private String patientPhoneNumber;
}

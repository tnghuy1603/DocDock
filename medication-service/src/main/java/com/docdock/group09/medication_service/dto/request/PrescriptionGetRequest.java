package com.docdock.group09.medication_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionGetRequest {
    private String patientId;
    private String doctorId;
    private int limit = 8;
    private int offset = 0;
}

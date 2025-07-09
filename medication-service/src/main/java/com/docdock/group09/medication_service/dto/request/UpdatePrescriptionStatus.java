package com.docdock.group09.medication_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePrescriptionStatus {
    private String id;
    private String status;
}

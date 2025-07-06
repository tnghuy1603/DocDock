package com.docdock.group09.medication_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationServiceException extends RuntimeException {
    private int code;
    private String message;
    public MedicationServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public static MedicationServiceException buildBadRequest(String message) {
        return new MedicationServiceException(400, message);
    }

    public static MedicationServiceException buildResourceNotException(String message) {
        return new MedicationServiceException(404, message);
    }
}

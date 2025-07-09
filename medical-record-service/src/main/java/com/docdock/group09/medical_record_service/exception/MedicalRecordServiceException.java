package com.docdock.group09.medical_record_service.exception;

import com.docdock.group09.medical_record_service.service.MedicalRecordService;
import lombok.Getter;

@Getter
public class MedicalRecordServiceException extends RuntimeException {
    private int code;
    public MedicalRecordServiceException(int code, String message) {
        super(message);
        this.code = code;
    }
    public static MedicalRecordServiceException buildBadRequest(String message) {
        return new MedicalRecordServiceException(400, message);
    }
}

package com.docdock.group09.medication_service.exception;

import com.docdock.group09.medication_service.dto.response.DocDockResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MedicationServiceExceptionHandler {

    @ExceptionHandler(MedicationServiceException.class)
    public ResponseEntity<?> handleMedicationException(MedicationServiceException ex) {
        return DocDockResponse.returnError(ex.getMessage(), ex.getCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return DocDockResponse.returnError("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}

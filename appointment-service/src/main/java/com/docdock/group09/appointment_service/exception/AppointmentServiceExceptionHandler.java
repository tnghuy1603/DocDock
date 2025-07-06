package com.docdock.group09.appointment_service.exception;

import com.docdock.group09.appointment_service.dto.response.DocDockResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppointmentServiceExceptionHandler {
    @ExceptionHandler(AppointmentServiceException.class)
    public ResponseEntity<?> handleAppointmentServiceException(AppointmentServiceException ex) {
        return DocDockResponse.returnError(ex.getMessage(), ex.getCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return DocDockResponse.returnError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}

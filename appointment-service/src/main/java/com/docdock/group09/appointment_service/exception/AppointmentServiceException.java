package com.docdock.group09.appointment_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentServiceException extends RuntimeException {
    private int code;
    public AppointmentServiceException(String message, int code) {
        super(message);
        this.code = code;
    }
    public static AppointmentServiceException buildBadRequest(String message) {
        return new AppointmentServiceException(message, 400);
    }
    public static AppointmentServiceException buildNotFound(String message) {
        return new AppointmentServiceException(message, 404);
    }
}

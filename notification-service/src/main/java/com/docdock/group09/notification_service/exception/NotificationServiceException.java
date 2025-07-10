package com.docdock.group09.notification_service.exception;

import lombok.Getter;

@Getter
public class NotificationServiceException extends RuntimeException {
    private final int code;
    public NotificationServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public static NotificationServiceException buildBadRequest(String message) {
        return new NotificationServiceException(400, message);
    }
}

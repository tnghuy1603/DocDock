package com.docdock.group09.user_service.exception;

import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

@Getter
@Setter
public class UserServiceException extends RuntimeException {
    private final int code;
    public UserServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public static UserServiceException buildBadRequestException(String message, Object... params) {
        return new UserServiceException(400, MessageFormat.format(message, params));
    }
    public static UserServiceException buildResourceNotFoundException(String message, Object... params) {
        return new UserServiceException(404, MessageFormat.format(message, params));
    }
}

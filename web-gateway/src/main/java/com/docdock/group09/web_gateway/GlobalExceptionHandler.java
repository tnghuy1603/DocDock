package com.docdock.group09.web_gateway;

import feign.FeignException;
import feign.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RetryableException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Object> handleRetryable(RetryableException ex) {
        return Map.of("code", 503, "message", "Service unavailable");
    }

    @ExceptionHandler(FeignException.class)
    @ResponseBody
    public ResponseEntity<?> handleFeign(FeignException ex) {
        return ResponseEntity
                .status(ex.status() > 0 ? ex.status() : 500)
                .body(Map.of("code", ex.status(), "message", ex.getMessage()));
    }
}

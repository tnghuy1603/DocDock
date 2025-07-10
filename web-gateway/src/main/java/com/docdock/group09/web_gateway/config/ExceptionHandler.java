package com.docdock.group09.web_gateway.config;

import com.docdock.group09.web_gateway.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(200).body(ResponseUtils.createErrorResponse(500, e.getMessage()));
    }
}

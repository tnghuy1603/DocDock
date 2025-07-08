package com.docdock.group09.web_gateway.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    public static Map<String, Object> createErrorResponse(int code, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", code);
        errorResponse.put("message", message);
        return errorResponse;
    }
}

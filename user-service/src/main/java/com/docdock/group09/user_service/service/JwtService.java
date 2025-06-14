package com.docdock.group09.user_service.service;

public interface JwtService {
    String signToken();
    void verifyToken(String token);
}

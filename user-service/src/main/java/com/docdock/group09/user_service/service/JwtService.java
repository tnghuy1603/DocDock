package com.docdock.group09.user_service.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface JwtService {
    String signToken();
    void verifyToken(String token);
}

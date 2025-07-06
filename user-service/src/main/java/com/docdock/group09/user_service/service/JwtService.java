package com.docdock.group09.user_service.service;

import com.docdock.group09.user_service.entity.UserEntity;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface JwtService {
    String signToken(UserEntity userEntity);
    void verifyToken(String token);
}

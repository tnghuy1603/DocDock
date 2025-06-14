package com.docdock.group09.user_service.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.docdock.group09.user_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class Auth0JwtServiceImpl implements JwtService {
    private final Algorithm algorithm;
    public String signToken() {
        return JWT.create()
                .withIssuer("docdock")
                .sign(algorithm);
    }

    public void verifyToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer("docdock")
                .build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
    }
}

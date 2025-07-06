package com.docdock.group09.user_service.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.docdock.group09.user_service.entity.UserEntity;
import com.docdock.group09.user_service.service.JwtService;
import com.docdock.group09.user_service.utils.RSAKeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;


@Service
@RequiredArgsConstructor
public class Auth0JwtServiceImpl implements JwtService {
    private final RSAKeyUtils rsaKeyUtils;
    @Value("${jwt.private-key}")
    private String privateKeyPath;
    @Value("${jwt.public-key}")
    private String publicKeyPath;
    public String signToken(UserEntity userEntity) {
        Algorithm algorithm;
        try {
            algorithm = Algorithm.RSA256((RSAPublicKey) rsaKeyUtils.getPublicKey(publicKeyPath), (RSAPrivateKey) rsaKeyUtils.getPrivateKey(privateKeyPath));
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return JWT.create()
                .withIssuer("docdock")
                .withSubject(userEntity.getId().toString())
                .withClaim("email", userEntity.getEmail())
                .withClaim("role", userEntity.getRole().name())
                .withIssuedAt(new java.util.Date())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(algorithm);
    }

    public void verifyToken(String token) {
        Algorithm algorithm;
        try {
            algorithm = Algorithm.RSA256((RSAPublicKey) rsaKeyUtils.getPublicKey(publicKeyPath), (RSAPrivateKey) rsaKeyUtils.getPrivateKey(privateKeyPath));
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer("docdock")
                .build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
    }
}

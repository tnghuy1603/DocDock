package com.docdock.group09.web_gateway.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.docdock.group09.web_gateway.util.RSAKeyUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    private final RSAKeyUtils rsaKeyUtils;
    @Value("${jwt.private-key}")
    private String privateKeyPath;
    @Value("${jwt.public-key}")
    private String publicKeyPath;
    @SneakyThrows
    @Bean
    public Algorithm rsaAlgorithm() {
        return Algorithm.RSA256((RSAPublicKey) rsaKeyUtils.getPublicKey(publicKeyPath), (RSAPrivateKey) rsaKeyUtils.getPrivateKey(privateKeyPath));
    }
}

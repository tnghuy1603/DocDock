package com.docdock.group09.user_service.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.docdock.group09.user_service.utils.RSAKeyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    private final String[] WHITE_LIST_ENDPOINT = {"auth/**", "public/**"};
    @Value("${jwt.private-key}")
    private String privateKeyPath;
    @Value("${jwt.public-key}")
    private String publicKeyPath;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(WHITE_LIST_ENDPOINT).permitAll()
                .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public PublicKey publicKey() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        return RSAKeyUtils.getPublicKey(publicKeyPath);
    }

    @Bean
    public PrivateKey privateKey() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        return RSAKeyUtils.getPrivateKey(privateKeyPath);
    }
    @Bean
    public Algorithm rsaAlgorithm() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        return Algorithm.RSA256((RSAPublicKey) privateKey(), (RSAPrivateKey) publicKey());
    }
}

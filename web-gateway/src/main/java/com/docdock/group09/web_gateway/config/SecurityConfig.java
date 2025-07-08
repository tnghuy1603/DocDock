package com.docdock.group09.web_gateway.config;

import com.docdock.group09.web_gateway.util.ResponseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private String[] AUTH_WHITELIST = {"/auth/**"};
    private final JwtAuthenticationFilter jwtFilter;
    private final ObjectMapper objectMapper;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint((request, response, authException) -> {
                        sendErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
                    });
                    exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                        sendErrorResponse(response, HttpStatus.FORBIDDEN.value(), accessDeniedException.getMessage());
                    });
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @SneakyThrows
    private void sendErrorResponse(HttpServletResponse response, int code, String message) {
        response.setStatus(200);
        response.setContentType("application/json");
        Map<String, Object> errorBody = ResponseUtils.createErrorResponse(401, message);
        String json = new ObjectMapper().writeValueAsString(errorBody);
        response.getWriter().write(json);
    }

}

package com.docdock.group09.user_service.controller;

import com.docdock.group09.user_service.dto.request.PatientSignUpRequest;
import com.docdock.group09.user_service.dto.request.SignInRequest;
import com.docdock.group09.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody PatientSignUpRequest request) {
        return ResponseEntity.ok(authService.signUp(request));
    }

}

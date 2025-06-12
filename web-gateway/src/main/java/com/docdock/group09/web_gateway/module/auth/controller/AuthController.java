package com.docdock.group09.web_gateway.module.auth.controller;

import com.docdock.group09.web_gateway.module.auth.dto.SignUpRequest;
import com.docdock.group09.web_gateway.module.user.dto.LoginRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private Object signUp(@RequestBody SignUpRequest signUpRequest) {
        return null;
    }
    private Object login(@RequestBody LoginRequest loginRequest) {
        return null;
    }
}

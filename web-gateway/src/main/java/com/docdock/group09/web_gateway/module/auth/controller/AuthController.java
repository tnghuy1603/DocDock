package com.docdock.group09.web_gateway.module.auth.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    public Object signUp(@RequestBody Object signUpRequest) {
        return null;
    }
    public Object login(@RequestBody Object loginRequest) {
        return null;
    }
}

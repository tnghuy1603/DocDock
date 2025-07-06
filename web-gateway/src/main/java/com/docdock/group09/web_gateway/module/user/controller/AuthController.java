package com.docdock.group09.web_gateway.module.user.controller;

import com.docdock.group09.web_gateway.module.user.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceClient userServiceClient;
    @PostMapping("sign-up")
    public Object signUp(@RequestBody Object signUpRequest) {
        return userServiceClient.signUp(signUpRequest);
    }

    @PostMapping("sign-in")
    public Object signIn(@RequestBody Object signInRequest) {
        return userServiceClient.signIn(signInRequest);
    }
}

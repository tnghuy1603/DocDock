package com.docdock.group09.web_gateway.module.user.controller;

import com.docdock.group09.web_gateway.module.user.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceClient userServiceClient;
    Object findUsers(@RequestParam Map<String, String> params) {
        return null;
    }
}

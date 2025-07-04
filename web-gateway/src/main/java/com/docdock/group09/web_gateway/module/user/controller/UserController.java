package com.docdock.group09.web_gateway.module.user.controller;

import com.docdock.group09.web_gateway.module.user.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceClient userServiceClient;

    @GetMapping("/{id}")
    public Object findUsers(@PathVariable("id") String id,@RequestParam Map<String, String> params) {
        return userServiceClient.getUserDetails(id,  params);
    }

    @GetMapping
    public Object findUsers(@RequestParam Map<String, String> params) {
        return userServiceClient.getUsers(params);
    }

    @PutMapping("/{id}")
    public Object updateUsers(@PathVariable("id") String id, @RequestBody Object updateUserRequest) {
        return userServiceClient.updateUsers(id, updateUserRequest);
    }
}

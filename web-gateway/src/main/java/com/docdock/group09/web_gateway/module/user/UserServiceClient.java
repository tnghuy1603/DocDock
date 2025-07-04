package com.docdock.group09.web_gateway.module.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "user-service", url = "${user.service.host}")
public interface UserServiceClient {

    @GetMapping("/users/{id}")
    ResponseEntity<?> getUserDetails(@PathVariable("id") String id, @RequestParam Map<String, String> params);

    @GetMapping("/users")
    ResponseEntity<?> getUsers(@RequestParam Map<String, String> params);

    @PutMapping("/users/{id}")
    ResponseEntity<?> updateUsers(@PathVariable("id") String id, @RequestBody Object updateUserRequest);
}

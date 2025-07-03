package com.docdock.group09.web_gateway.module.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "user-service", url = "${user.service.host}")
public interface UserServiceClient {
    @GetMapping("/users")
    ResponseEntity<?> getUserDetails(@RequestParam Map<String, Object> params);


}

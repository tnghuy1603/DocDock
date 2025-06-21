package com.docdock.group09.appointment_service.service;

import com.docdock.group09.appointment_service.dto.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserServiceClient {
    @GetMapping("{id}")
    UserInfo getUserDetails(@PathVariable("id") String userId, @RequestParam String role);
}

package com.docdock.group09.medication_service.service;

import com.docdock.group09.medication_service.dto.response.DocDockResponse;
import com.docdock.group09.medication_service.dto.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "${api.user-service-host}")
public interface UserServiceClient {

    @GetMapping("/users/{id}")
    DocDockResponse<UserInfo> getUserInfo(@PathVariable("id") String userId, @RequestParam String role);
}

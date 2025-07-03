package com.docdock.group09.web_gateway.module.user;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", url = "${user.service.host}")
public interface UserServiceClient {

}

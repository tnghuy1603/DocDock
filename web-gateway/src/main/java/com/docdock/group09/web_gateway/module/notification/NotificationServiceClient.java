package com.docdock.group09.web_gateway.module.notification;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "notification-service-client", url = "${api.notification-service-host}")
public interface NotificationServiceClient {
}

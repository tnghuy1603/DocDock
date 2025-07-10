package com.docdock.group09.web_gateway.module.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "notification-service-client", url = "${api.notification-service-host}")
public interface NotificationServiceClient {
    @GetMapping("notifications/count-unread")
    ResponseEntity<?> countUnread(@RequestParam Map<String, String> params);

    @GetMapping("notifications")
    ResponseEntity<?> getNotifications(@RequestParam Map<String, String> params);
}

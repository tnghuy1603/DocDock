package com.docdock.group09.web_gateway.module.notification.controller;

import com.docdock.group09.web_gateway.module.notification.NotificationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationServiceClient notificationServiceClient;
    @GetMapping("count-unread")
    public Object countUnread(@RequestParam Map<String, String> params) {
        return notificationServiceClient.countUnread(params);
    }

    @GetMapping
    public Object getNotification(@RequestParam Map<String, String> params) {
        return notificationServiceClient.getNotifications(params);
    }
}

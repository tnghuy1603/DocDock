package com.docdock.group09.web_gateway.module.notification.controller;

import com.docdock.group09.web_gateway.module.notification.NotificationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("read-all")
    public Object readAll(@RequestBody Object request) {
        return notificationServiceClient.readAll(request);
    }

    @PutMapping("{id}/markAsRead")
    public Object markAsRead(@PathVariable("id") String id, @RequestBody Object request) {
        return notificationServiceClient.markAsRead(request, id);
    }
}

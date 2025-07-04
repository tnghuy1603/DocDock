package com.docdock.group09.notification_service.controller;

import com.docdock.group09.notification_service.dto.request.SendNotificationRequest;
import com.docdock.group09.notification_service.entity.NotificationType;
import com.docdock.group09.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping
    public ResponseEntity<?> getNotifications(@RequestParam("userId") String userId,
                                              @RequestParam(name = "type", required = false) NotificationType type,
                                              @RequestParam(name = "limit", required = false, defaultValue = "8") Integer limit,
                                              @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset) {
        if (limit <= 0) {
            limit = 8;
        }
        if (offset < 0) {
            offset = 0;
        }
        return ResponseEntity.ok(notificationService.getNotifications(userId, type, limit, offset));
    }
    @GetMapping("/count-unread")
    public ResponseEntity<?> getUnreadNotifications(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(notificationService.countUnRead(userId));
    }

    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody SendNotificationRequest request) {
        return ResponseEntity.ok(notificationService.sendNotification(request));
    }
}

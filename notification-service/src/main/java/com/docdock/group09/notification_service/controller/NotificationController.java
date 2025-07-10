package com.docdock.group09.notification_service.controller;

import com.docdock.group09.notification_service.dto.request.NotificationFilterRequest;
import com.docdock.group09.notification_service.dto.request.SendNotificationRequest;
import com.docdock.group09.notification_service.dto.response.DocDockResponse;
import com.docdock.group09.notification_service.dto.response.ReadAllNotificationRequest;
import com.docdock.group09.notification_service.dto.response.ReadNotificationRequest;
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
    public ResponseEntity<?> getNotifications(NotificationFilterRequest filterRequest) {
        return DocDockResponse.returnSuccessPagination(notificationService.getNotifications(filterRequest));
    }
    @GetMapping("/count-unread")
    public ResponseEntity<?> getUnreadNotifications(@RequestParam("userId") String userId, @RequestParam("channel") String channel) {
        return DocDockResponse.returnSuccess(notificationService.countUnRead(userId, channel));
    }

    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody SendNotificationRequest request) {
        return DocDockResponse.returnSuccess(notificationService.sendNotification(request));
    }

    @PutMapping("{id}/markAsRead")
    public ResponseEntity<?> markAsRead(@PathVariable("id") String notificationId, @RequestBody ReadNotificationRequest request) {
        notificationService.markAsRead(request.getUserId(), notificationId);
        return DocDockResponse.returnSuccess("Marked as read");
    }

    @PutMapping("read-all")
    public ResponseEntity<?> readAllNotification(@RequestBody ReadAllNotificationRequest request) {
        notificationService.readAll(request);
        return DocDockResponse.returnSuccess("Read all notification");
    }
}

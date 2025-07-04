package com.docdock.group09.notification_service.service;

import com.docdock.group09.notification_service.dto.request.SendNotificationRequest;
import com.docdock.group09.notification_service.dto.response.NotificationResponse;
import com.docdock.group09.notification_service.entity.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    NotificationResponse sendNotification(SendNotificationRequest request);
    Page<NotificationResponse> getNotifications(String userId, NotificationType type, Integer limit, Integer offset);
    long countUnRead(String userId);
}

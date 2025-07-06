package com.docdock.group09.appointment_service.service;

import com.docdock.group09.appointment_service.dto.request.SendNotificationRequest;

public interface NotificationService {
    void sendNotification(SendNotificationRequest request);

}

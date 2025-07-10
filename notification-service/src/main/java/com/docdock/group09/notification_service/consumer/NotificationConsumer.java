package com.docdock.group09.notification_service.consumer;

import com.docdock.group09.notification_service.dto.request.SendNotificationRequest;
import com.docdock.group09.notification_service.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
//    @RabbitListener(queues = "notification.queue")
//    public void handleNotificationMessage(String message) {
//        SendNotificationRequest request = objectMapper.convertValue(message, SendNotificationRequest.class);
//        log.info("Received notification message: {}", message);
//        notificationService.sendNotification(request);
//    }
}

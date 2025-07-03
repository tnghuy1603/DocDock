package com.docdock.group09.appointment_service.service.impl;

import com.docdock.group09.appointment_service.config.RabbitMQConfig;
import com.docdock.group09.appointment_service.dto.request.SendNotificationRequest;
import com.docdock.group09.appointment_service.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;
    @SneakyThrows
    @Override
    public void sendNotification(SendNotificationRequest request) {
        String jsonMessage = objectMapper.writeValueAsString(request);
        amqpTemplate.convertAndSend(RabbitMQConfig.ROUTING_KEY, jsonMessage);
    }
}

package com.docdock.group09.notification_service.dto.request;

import com.docdock.group09.notification_service.entity.NotificationType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class SendNotificationRequest {
    private String receiverId;
    private Map<String, Object> params;
    private NotificationType type;

}

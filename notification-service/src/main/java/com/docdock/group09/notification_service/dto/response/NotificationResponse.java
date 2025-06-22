package com.docdock.group09.notification_service.dto.response;

import com.docdock.group09.notification_service.entity.ChannelType;
import com.docdock.group09.notification_service.entity.NotificationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class NotificationResponse {
    private String id;
    private String receiverId;
    private String body;
    private String title;
    private Boolean isRead;
    private NotificationType type;
    private ChannelType channel;
    private LocalDateTime sentAt;
}

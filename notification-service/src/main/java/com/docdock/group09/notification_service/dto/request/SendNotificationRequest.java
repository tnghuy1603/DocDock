package com.docdock.group09.notification_service.dto.request;

import com.docdock.group09.notification_service.entity.NotificationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class SendNotificationRequest {
    private String receiverId;
    private NotificationType type;
    private String doctorName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String cancelReason;
}

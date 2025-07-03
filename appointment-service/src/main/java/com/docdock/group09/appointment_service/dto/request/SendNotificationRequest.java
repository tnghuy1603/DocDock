package com.docdock.group09.appointment_service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class SendNotificationRequest {
    private String receiverId;
    private String type;
    private String doctorName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String cancelReason;
}

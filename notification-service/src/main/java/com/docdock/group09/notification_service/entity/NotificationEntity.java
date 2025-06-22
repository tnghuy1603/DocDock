package com.docdock.group09.notification_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification")
public class NotificationEntity {
    @Id
    @UuidGenerator
    private String id;
    private String receiverId;
    private String body;
    private String title;
    private Boolean isRead;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @Enumerated(EnumType.STRING)
    private ChannelType channel;
    private LocalDateTime sentAt;
    private LocalDateTime updatedAt;
}

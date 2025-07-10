package com.docdock.group09.notification_service.service.impl;

import com.docdock.group09.notification_service.dto.mapper.NotificationMapper;
import com.docdock.group09.notification_service.dto.request.NotificationFilterRequest;
import com.docdock.group09.notification_service.dto.request.SendNotificationRequest;
import com.docdock.group09.notification_service.dto.response.NotificationResponse;
import com.docdock.group09.notification_service.dto.response.ReadAllNotificationRequest;
import com.docdock.group09.notification_service.entity.ChannelType;
import com.docdock.group09.notification_service.entity.NotificationEntity;
import com.docdock.group09.notification_service.entity.NotificationType;
import com.docdock.group09.notification_service.exception.NotificationServiceException;
import com.docdock.group09.notification_service.repository.NotificationRepository;
import com.docdock.group09.notification_service.repository.NotificationSpecification;
import com.docdock.group09.notification_service.service.NotificationService;
import com.docdock.group09.notification_service.utils.SmsTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;
    @SneakyThrows
    @Override
    public NotificationResponse sendNotification(SendNotificationRequest request) {
        String content = "";
        switch (request.getType()) {
            case APPOINTMENT_REMIND:
                content = SmsTemplate.getAppointmentReminder(request.getDoctorName(), request.getStartAt(), request.getEndAt());
                break;
            case APPOINTMENT_CONFIRMED:
                content = SmsTemplate.getAppointmentConfirmation(request.getDoctorName(), request.getStartAt(), request.getEndAt());
                break;
            case APPOINTMENT_REJECTED:
                content = SmsTemplate.getAppointmentCancelled(request.getDoctorName(), request.getStartAt(), request.getEndAt(), request.getCancelReason());
                break;
            case PRESCRIPTION_READY:
                break;
            default:
                throw NotificationServiceException.buildBadRequest("Invalid notification type");
        }
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .body(content)
                .title(content)
                .receiverId(request.getReceiverId())
                .type(request.getType())
                .sentAt(LocalDateTime.now())
                .channel(ChannelType.IN_APP)
                .isRead(false)
                .build();
        notificationEntity = notificationRepository.save(notificationEntity);
        simpMessagingTemplate.convertAndSend("topic/notifications/"+ request.getReceiverId(), objectMapper.writeValueAsString(notificationEntity));
        return notificationMapper.toModel(notificationEntity);
    }

    @Override
    public Page<NotificationResponse> getNotifications(NotificationFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getOffset() / request.getLimit(), request.getLimit());
        Specification<NotificationEntity> spec = NotificationSpecification.filterNotification(request);
        Page<NotificationEntity> notificationEntities = notificationRepository.findAll(spec, pageable);
        List<NotificationResponse> notificationResponses = notificationMapper.toModelList(notificationEntities.getContent());
        return new PageImpl<>(notificationResponses, pageable, notificationEntities.getTotalElements());
    }

    @Override
    public long countUnRead(String userId, String channel) {
        return notificationRepository.countUnread(userId, channel);
    }

    @Override
    public void markAsRead(String userId, String notificationId) {
        NotificationEntity notificationEntity = notificationRepository.findById(notificationId)
                .orElseThrow(() -> NotificationServiceException.buildBadRequest("Notification not found"));
        if (notificationEntity.getChannel() == ChannelType.IN_APP && Boolean.FALSE.equals(notificationEntity.getIsRead())) {
            notificationEntity.setIsRead(true);
            notificationRepository.save(notificationEntity);
        }
    }

    @Override
    public void readAll(ReadAllNotificationRequest request) {
        notificationRepository.readAll(request.getUserId());
    }


}

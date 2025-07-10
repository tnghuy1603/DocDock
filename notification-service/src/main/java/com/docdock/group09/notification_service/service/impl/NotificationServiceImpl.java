package com.docdock.group09.notification_service.service.impl;

import com.docdock.group09.notification_service.dto.mapper.NotificationMapper;
import com.docdock.group09.notification_service.dto.request.SendNotificationRequest;
import com.docdock.group09.notification_service.dto.response.NotificationResponse;
import com.docdock.group09.notification_service.entity.NotificationEntity;
import com.docdock.group09.notification_service.entity.NotificationType;
import com.docdock.group09.notification_service.repository.NotificationRepository;
import com.docdock.group09.notification_service.service.NotificationService;
import com.docdock.group09.notification_service.utils.SmsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    @Override
    public NotificationResponse sendNotification(SendNotificationRequest request) {
        String content;
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
                throw new IllegalArgumentException("Invalid notification type");
        }
        return null;
    }

    @Override
    public Page<NotificationResponse> getNotifications(String userId, NotificationType type, Integer limit, Integer offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<NotificationEntity> notificationEntities = notificationRepository.findByReceiverId(userId, pageable);
        List<NotificationResponse> notificationResponses = notificationMapper.toModelList(notificationEntities.getContent());
        return new PageImpl<>(notificationResponses, pageable, notificationEntities.getTotalElements());

    }

    @Override
    public long countUnRead(String userId, String channel) {
        return notificationRepository.countUnread(userId, channel);
    }
}

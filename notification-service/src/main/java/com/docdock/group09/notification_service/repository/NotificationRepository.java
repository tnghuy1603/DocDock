package com.docdock.group09.notification_service.repository;

import com.docdock.group09.notification_service.entity.NotificationEntity;
import com.docdock.group09.notification_service.entity.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, String> {
    Page<NotificationEntity> findByReceiverIdAndType(String receiverId, NotificationType type, Pageable pageable);
    @Query(value = "", nativeQuery = true)
    long countUnread(String userId);
}

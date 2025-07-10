package com.docdock.group09.notification_service.repository;

import com.docdock.group09.notification_service.entity.NotificationEntity;
import com.docdock.group09.notification_service.entity.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, String> {
    Page<NotificationEntity> findByReceiverId(String receiverId, Pageable pageable);
    @Query(value = "SELECT COUNT(*) FROM notification WHERE receiver_id = :userId AND is_read = FALSE AND channel = :channel", nativeQuery = true)
    long countUnread(@Param("userId") String userId, @Param("channel") String channel);

}

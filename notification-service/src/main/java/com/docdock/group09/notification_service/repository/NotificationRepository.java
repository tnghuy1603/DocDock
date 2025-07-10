package com.docdock.group09.notification_service.repository;

import com.docdock.group09.notification_service.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface NotificationRepository extends JpaRepository<NotificationEntity, String>, JpaSpecificationExecutor<NotificationEntity> {
    @Query(value = "SELECT COUNT(*) FROM notification WHERE receiver_id = :userId AND is_read = FALSE AND channel = :channel", nativeQuery = true)
    long countUnread(@Param("userId") String userId, @Param("channel") String channel);
    @Modifying
    @Transactional
    @Query(value = "UPDATE notification SET is_read = true WHERE receiver_id = :userId AND channel = 'IN_APP'", nativeQuery = true)
    int readAll(@Param("userId") String userId);
}

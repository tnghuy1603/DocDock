package com.docdock.group09.notification_service.dto.mapper;

import com.docdock.group09.notification_service.dto.response.NotificationResponse;
import com.docdock.group09.notification_service.entity.NotificationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponse toModel(NotificationEntity entity);
    List<NotificationResponse> toModelList(List<NotificationEntity> entities);

}

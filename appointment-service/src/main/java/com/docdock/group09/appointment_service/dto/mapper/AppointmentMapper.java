package com.docdock.group09.appointment_service.dto.mapper;

import com.docdock.group09.appointment_service.dto.request.BookAppointmentRequest;
import com.docdock.group09.appointment_service.dto.request.SendNotificationRequest;
import com.docdock.group09.appointment_service.dto.response.AppointmentResponse;
import com.docdock.group09.appointment_service.entity.AppointmentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentEntity toEntity(BookAppointmentRequest request);
    AppointmentResponse toModel(AppointmentEntity entity);
    List<AppointmentResponse> toModelList(List<AppointmentEntity> entities);
}

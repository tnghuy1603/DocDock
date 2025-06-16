package com.docdock.group09.appointment_service.repository;

import com.docdock.group09.appointment_service.dto.request.FilterAppointmentRequest;
import com.docdock.group09.appointment_service.dto.response.AppointmentStatusCount;

import java.util.List;

public interface AppointmentRepositoryCustom {
    List<AppointmentStatusCount> countByFilter(FilterAppointmentRequest request);
}

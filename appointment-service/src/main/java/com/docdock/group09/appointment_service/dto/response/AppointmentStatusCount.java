package com.docdock.group09.appointment_service.dto.response;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppointmentStatusCount {
    private AppointmentStatus status;
    private int count;
}

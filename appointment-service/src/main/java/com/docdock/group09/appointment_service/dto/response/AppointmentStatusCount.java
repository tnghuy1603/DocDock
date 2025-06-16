package com.docdock.group09.appointment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppointmentStatusCount {
    private String status;
    private int count;
}

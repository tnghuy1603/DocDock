package com.docdock.group09.appointment_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppointmentFilterResponse {
    private List<AppointmentResponse> appointments;

}

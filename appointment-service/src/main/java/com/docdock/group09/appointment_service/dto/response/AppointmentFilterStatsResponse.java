package com.docdock.group09.appointment_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentFilterStatsResponse {
    private int numberOfPending;
    private int numberOfCompleted;
    private int numberOfCancelled;
    private int numberOfConfirmed;
}

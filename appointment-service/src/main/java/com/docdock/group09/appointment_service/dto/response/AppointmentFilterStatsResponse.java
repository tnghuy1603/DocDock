package com.docdock.group09.appointment_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentFilterStatsResponse {
    private long numberOfPending;
    private long numberOfCompleted;
    private long numberOfCancelled;
    private long numberOfConfirmed;
}

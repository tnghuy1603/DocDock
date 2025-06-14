package com.docdock.group09.appointment_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Setter
@Getter
public class DoctorScheduleResponse {
    private String doctorId;
    private LocalDate date;
    private List<WorkShift> workShifts;
    @Setter
    @Getter
    public static class WorkShift {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
}

package com.docdock.group09.appointment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class AvailableScheduleResponse {
    private String doctorId;
    private List<TimeFrame> timeFrames;
    @Getter
    @Setter
    @AllArgsConstructor
    public static class TimeFrame {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
}

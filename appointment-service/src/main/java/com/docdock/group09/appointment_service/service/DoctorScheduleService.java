package com.docdock.group09.appointment_service.service;

import com.docdock.group09.appointment_service.dto.response.DoctorScheduleResponse;

import java.time.LocalDate;
import java.util.List;

public interface DoctorScheduleService {
    void bulkInsertData();
    List<DoctorScheduleResponse> getDoctorScheduleIn7days(String doctorId, LocalDate currentDate);
}

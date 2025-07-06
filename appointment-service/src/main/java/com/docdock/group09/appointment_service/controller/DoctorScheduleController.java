package com.docdock.group09.appointment_service.controller;

import com.docdock.group09.appointment_service.dto.response.DocDockResponse;
import com.docdock.group09.appointment_service.service.DoctorScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor-schedules")
public class DoctorScheduleController {
    private final DoctorScheduleService doctorScheduleService;

    @GetMapping
    public ResponseEntity<?> getDoctorScheduleIn7dayFromDate(@RequestParam(name = "doctorId") String doctorId,
                                                      @RequestParam(name = "date", required = false) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return DocDockResponse.returnSuccess(doctorScheduleService.getDoctorScheduleIn7days(doctorId, date));
    }

    @GetMapping("/seed")
    public ResponseEntity<?> mockData() {
        doctorScheduleService.bulkInsertData();
        return DocDockResponse.returnSuccess("Mock data successfully");
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableTimes(@RequestParam(name = "doctorId") String doctorId,
                                               @RequestParam(name = "date") LocalDate date) {
        return DocDockResponse.returnSuccess(doctorScheduleService.getTodayAvailable(doctorId, date));
    }
}

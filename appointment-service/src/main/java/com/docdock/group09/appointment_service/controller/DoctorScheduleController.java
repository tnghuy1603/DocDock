package com.docdock.group09.appointment_service.controller;

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
        return ResponseEntity.ok(doctorScheduleService.getDoctorScheduleIn7days(doctorId, date));
    }

    @GetMapping("/seed")
    public ResponseEntity<?> mockData() {
        doctorScheduleService.bulkInsertData();
        return ResponseEntity.ok().build();
    }
}

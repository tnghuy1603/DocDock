package com.docdock.group09.appointment_service.controller;

import com.docdock.group09.appointment_service.service.DoctorScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor-schedules")
public class DoctorScheduleController {
    private final DoctorScheduleService doctorScheduleService;

    @GetMapping
    public ResponseEntity<?> getDoctorScheduleIn7days(String doctorId, LocalDate currentDate) {
        return ResponseEntity.ok(doctorScheduleService.getDoctorScheduleIn7days(doctorId, currentDate));
    }

    @GetMapping("/seed")
    public ResponseEntity<?> mockData() {
        doctorScheduleService.bulkInsertData();
        return ResponseEntity.ok().build();
    }
}

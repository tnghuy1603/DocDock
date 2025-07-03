package com.docdock.group09.web_gateway.module.appointment.controller;

import com.docdock.group09.web_gateway.module.appointment.AppointmentServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/doctor-schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {
    private final AppointmentServiceClient appointmentServiceClient;
    @GetMapping
    public Object getAllDoctorSchedules(@RequestParam Map<String, String> params) {
        return appointmentServiceClient.getDoctorSchedule(params);
    }
}

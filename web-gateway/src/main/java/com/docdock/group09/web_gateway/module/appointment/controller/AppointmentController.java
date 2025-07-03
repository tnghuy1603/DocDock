package com.docdock.group09.web_gateway.module.appointment.controller;

import com.docdock.group09.web_gateway.module.appointment.AppointmentServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentServiceClient appointmentServiceClient;

    @GetMapping
    public Object getAppointments(@RequestParam Map<String, String> params) {
        return appointmentServiceClient.getAppointments(params);
    }

    @GetMapping("/stats")
    public Object getStats(@RequestParam Map<String, String> params) {
        return appointmentServiceClient.getAppointmentsStats(params);
    }

    @PostMapping
    public Object bookAppointment(@RequestBody Object bookingRequest) {
        return appointmentServiceClient.bookAppointment(bookingRequest);
    }

    @PutMapping("/{id}")
    public Object updateAppointment(@PathVariable("id") String id, @RequestBody Object bookingRequest) {
        return appointmentServiceClient.updateAppointment(id, bookingRequest);
    }
}

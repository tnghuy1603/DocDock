package com.docdock.group09.appointment_service.controller;

import com.docdock.group09.appointment_service.dto.request.BookAppointmentRequest;
import com.docdock.group09.appointment_service.dto.request.CancelAppointmentRequest;
import com.docdock.group09.appointment_service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<?> bookAppointment(@RequestBody BookAppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.bookAppointment(request));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable(name = "id") String id, @RequestBody CancelAppointmentRequest request) {
        return ResponseEntity.ok()
    }
}

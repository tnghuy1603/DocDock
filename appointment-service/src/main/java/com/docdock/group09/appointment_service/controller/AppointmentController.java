package com.docdock.group09.appointment_service.controller;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import com.docdock.group09.appointment_service.dto.request.AppointmentUpdateRequest;
import com.docdock.group09.appointment_service.dto.request.BookAppointmentRequest;
import com.docdock.group09.appointment_service.dto.request.FilterAppointmentRequest;
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

    @GetMapping
    public ResponseEntity<?> filterAppointment(FilterAppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.filterAppointments(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable String id, @RequestBody AppointmentUpdateRequest request) {
        if (AppointmentStatus.CANCELLED.toString().equals(request.getAction())) {
            return ResponseEntity.ok(appointmentService.cancelAppointment(request, id));
        } else if (AppointmentStatus.CONFIRMED.toString().equals(request.getAction())) {
            return ResponseEntity.ok(appointmentService.confirmAppointment(request, id));
        } else if (AppointmentStatus.COMPLETED.toString().equals(request.getAction())) {
            return ResponseEntity.ok(appointmentService.completeAppointment(request, id));
        } else if ("UPDATE".equals(request.getAction())) {
            return ResponseEntity.ok(appointmentService.updateAppointment(request, id));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findAppointmentById(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(FilterAppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.getStats(request));
    }


}

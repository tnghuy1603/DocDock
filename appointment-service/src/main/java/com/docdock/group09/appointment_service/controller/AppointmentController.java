package com.docdock.group09.appointment_service.controller;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import com.docdock.group09.appointment_service.dto.request.AppointmentUpdateRequest;
import com.docdock.group09.appointment_service.dto.request.BookAppointmentRequest;
import com.docdock.group09.appointment_service.dto.request.FilterAppointmentRequest;
import com.docdock.group09.appointment_service.dto.response.DocDockResponse;
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
        return DocDockResponse.returnSuccess(appointmentService.bookAppointment(request), 201);
    }

    @GetMapping
    public ResponseEntity<?> filterAppointment(FilterAppointmentRequest request) {
        return DocDockResponse.returnSuccessPagination(appointmentService.filterAppointments(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable String id, @RequestBody AppointmentUpdateRequest request) {
        if (AppointmentStatus.CANCELLED.toString().equals(request.getAction())) {
            return DocDockResponse.returnSuccess(appointmentService.cancelAppointment(request, id));
        } else if (AppointmentStatus.CONFIRMED.toString().equals(request.getAction())) {
            return DocDockResponse.returnSuccess(appointmentService.confirmAppointment(request, id));
        } else if (AppointmentStatus.COMPLETED.toString().equals(request.getAction())) {
            return DocDockResponse.returnSuccess(appointmentService.completeAppointment(request, id));
            } else if ("UPDATE".equals(request.getAction())) {
            return DocDockResponse.returnSuccess(appointmentService.updateAppointment(request, id));
        }
        return DocDockResponse.returnError("Supported action", 400);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findAppointmentById(@PathVariable String id) {
        return DocDockResponse.returnSuccess(appointmentService.getAppointmentDetails(id));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(FilterAppointmentRequest request) {
        return DocDockResponse.returnSuccess(appointmentService.getStats(request));
    }

}

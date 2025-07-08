package com.docdock.group09.web_gateway.module.appointment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "appointment-service-client", url = "${api.appointment-service-host}")
public interface AppointmentServiceClient {
    @GetMapping("/appointments")
    ResponseEntity<?> getAppointments(@RequestParam Map<String, String> params);

    @GetMapping("/appointments/stats")
    ResponseEntity<?> getAppointmentsStats(@RequestParam Map<String, String> params);

    @PostMapping("/appointments")
    ResponseEntity<?> bookAppointment(@RequestBody Object bookingRequest);

    @PutMapping("/appointments/{id}")
    ResponseEntity<?> updateAppointment(@PathVariable("id") String id, @RequestBody Object bookingRequest);

    @GetMapping("/appointments/{id}")
    ResponseEntity<?> getAppointmentById(@PathVariable("id") String id);

    @GetMapping("/doctor-schedules")
    ResponseEntity<?> getDoctorSchedule(@RequestParam Map<String, String> params);


    @GetMapping("/doctor-schedules/available")
    ResponseEntity<?> getAvailableSchedule(@RequestParam Map<String, String> params);
}

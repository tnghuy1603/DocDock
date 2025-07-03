package com.docdock.group09.web_gateway.module.appointment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "appointment-service-client", url = "${api.appointment-service-host}")
public interface AppointmentServiceClient {
    @GetMapping("/appointments")
    ResponseEntity<?> getAppointments(@RequestParam Map<String, String> params);
}

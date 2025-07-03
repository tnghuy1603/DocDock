package com.docdock.group09.web_gateway.module.medication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "medication-service", url = "${api.medication-service-host}")
public interface MedicationServiceClient {
    @GetMapping("medications")
    ResponseEntity<?> filterMedication(@RequestParam Map<String, String> params);

    @GetMapping("medications/{id}")
    ResponseEntity<?> getMedicationById(@PathVariable String id);

    @PutMapping("medications/{id}")
    ResponseEntity<?> updateMedication(@RequestBody Object updateRequest, @PathVariable String id);

    @PostMapping("medications")
    ResponseEntity<?> createMedicine(@RequestBody Object request);

    @GetMapping("medications/stats")
    ResponseEntity<?> getMedicationStats();

    @GetMapping("/prescriptions/{id}")
    ResponseEntity<?> getPrescription(@PathVariable String id);

    @GetMapping("/prescriptions")
    ResponseEntity<?> filterPrescription(@RequestParam Map<String, String> params);

    @PostMapping("/prescriptions")
    ResponseEntity<?> addPrescription(@RequestBody Object request);

    @GetMapping("/prescriptions/details")
    ResponseEntity<?> getPrescriptionDetails(@RequestParam Map<String, String> params);
}

package com.docdock.group09.web_gateway.module.medication.controller;

import com.docdock.group09.web_gateway.module.medication.MedicationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/medications")
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationServiceClient medicationServiceClient;
    @GetMapping
    public Object filterMedication(@RequestParam Map<String, String> params) {
        return medicationServiceClient.filterMedication(params);
    }

    @GetMapping("{id}")
    public Object getMedicationById(@PathVariable String id) {
        return medicationServiceClient.getMedicationById(id);
    }

    @PutMapping("{id}")
    public Object updateMedication(@RequestBody Object updateRequest, @PathVariable String id) {
        return medicationServiceClient.updateMedication(updateRequest, id);
    }

    @PostMapping
    public Object createMedicine(@RequestBody Object request) {
        return medicationServiceClient.createMedicine(request);
    }

    @GetMapping("stats")
    public Object getMedicationStats() {
        return medicationServiceClient.getMedicationStats();
    }

}

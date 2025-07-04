package com.docdock.group09.web_gateway.module.medication.controller;

import com.docdock.group09.web_gateway.module.medication.MedicationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {
    private final MedicationServiceClient medicationServiceClient;
    @GetMapping("/{id}")
    public Object getPrescription(@PathVariable String id) {
        return medicationServiceClient.getPrescription(id);
    }

    @GetMapping
    public Object filterPrescription(@RequestParam Map<String, String> params) {
        return medicationServiceClient.filterPrescription(params);
    }

    @PostMapping
    public Object addPrescription(@RequestBody Object request) {
        return medicationServiceClient.addPrescription(request);
    }

    @GetMapping("details")
    public Object getPrescriptionDetails(@RequestParam Map<String, String> params) {
        return medicationServiceClient.getPrescriptionDetails(params);
    }


}

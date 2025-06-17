package com.docdock.group09.medication_service.controller;

import com.docdock.group09.medication_service.dto.request.CreatePrescriptionRequest;
import com.docdock.group09.medication_service.dto.request.PrescriptionGetRequest;
import com.docdock.group09.medication_service.entity.PrescriptionEntity;
import com.docdock.group09.medication_service.service.MedicationService;
import com.docdock.group09.medication_service.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prescriptions")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    @GetMapping("{id}")
    public ResponseEntity<?> getPrescription(@PathVariable String id) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(id));
    }

    @GetMapping
    public ResponseEntity<?> filterPrescription(PrescriptionGetRequest request) {
        return ResponseEntity.ok(prescriptionService.getPrescriptions(request));
    }

    @PostMapping
    public ResponseEntity<?> addPrescription(@RequestBody CreatePrescriptionRequest request) {
        return ResponseEntity.ok(prescriptionService.prescribeMedication(request));
    }

    @GetMapping("/details")
    public ResponseEntity<?> getPrescriptionDetails(@RequestParam(name = "prescriptionId") String prescriptionId) {

    }


}

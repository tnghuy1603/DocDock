package com.docdock.group09.medication_service.controller;

import com.docdock.group09.medication_service.dto.request.MedicationGetRequest;
import com.docdock.group09.medication_service.dto.request.MedicationRequest;
import com.docdock.group09.medication_service.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("medications")
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;
    @GetMapping
    public ResponseEntity<?> filterMedication(MedicationGetRequest request) {
        return ResponseEntity.ok(medicationService.getByQuery(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getMedicationById(@PathVariable String id) {
        return ResponseEntity.ok(medicationService.getById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateMedication(@RequestBody MedicationRequest medicationRequest, @PathVariable String id) {
        return ResponseEntity.ok(medicationService.updateMedication(medicationRequest, id));
    }

    @PostMapping
    public ResponseEntity<?> createMedicine(@RequestBody MedicationRequest request) {
        return ResponseEntity.ok(medicationService.createMedication(request));
    }

    @GetMapping("stats")
    public ResponseEntity<?> getMedicationStats() {
        return ResponseEntity.ok(medicationService.getStats());
    }

}

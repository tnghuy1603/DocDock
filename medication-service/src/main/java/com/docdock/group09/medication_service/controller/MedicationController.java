package com.docdock.group09.medication_service.controller;

import com.docdock.group09.medication_service.dto.request.MedicationGetRequest;
import com.docdock.group09.medication_service.dto.request.MedicationRequest;
import com.docdock.group09.medication_service.dto.response.DocDockResponse;
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
        return DocDockResponse.returnSuccessPagination(medicationService.getByQuery(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getMedicationById(@PathVariable String id) {
        return DocDockResponse.returnSuccess(medicationService.getById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateMedication(@RequestBody MedicationRequest medicationRequest, @PathVariable String id) {
        return DocDockResponse.returnSuccess(medicationService.updateMedication(medicationRequest, id));
    }

    @PostMapping
    public ResponseEntity<?> createMedicine(@RequestBody MedicationRequest request) {
        return DocDockResponse.returnSuccess(medicationService.createMedication(request), 201);
    }

    @GetMapping("stats")
    public ResponseEntity<?> getMedicationStats() {
        return DocDockResponse.returnSuccess(medicationService.getStats());
    }
}

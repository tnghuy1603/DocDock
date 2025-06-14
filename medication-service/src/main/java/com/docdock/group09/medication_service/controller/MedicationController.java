package com.docdock.group09.medication_service.controller;

import com.docdock.group09.medication_service.dto.request.MedicationGetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("medications")
@RequiredArgsConstructor
public class MedicationController {
    public ResponseEntity<?> filterMedication(@RequestParam MedicationGetRequest request) {

    }
    @GetMapping("{id}")
    public ResponseEntity<?> getMedicationById(@PathVariable String id) {

    }

}

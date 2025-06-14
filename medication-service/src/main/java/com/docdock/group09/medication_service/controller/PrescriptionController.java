package com.docdock.group09.medication_service.controller;

import com.docdock.group09.medication_service.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("prescription")
public class PrescriptionController {
    private final MedicationService medicationService;
    
}

package com.docdock.group09.medical_record_service.controller;

import com.docdock.group09.medical_record_service.dto.request.CreateMedicalRecordRequest;
import com.docdock.group09.medical_record_service.dto.request.FilterMedicalRecordRequest;
import com.docdock.group09.medical_record_service.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/medical-records")
@RestController
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<?> createMedicalRecord(@RequestBody CreateMedicalRecordRequest request) {
        return ResponseEntity.ok(medicalRecordService.createRecord(request));
    }

    @GetMapping
    public ResponseEntity<?> filterRecords(FilterMedicalRecordRequest request) {
        return ResponseEntity.ok(medicalRecordService.filter(request));
    }
}

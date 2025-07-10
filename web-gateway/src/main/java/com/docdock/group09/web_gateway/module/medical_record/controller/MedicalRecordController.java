package com.docdock.group09.web_gateway.module.medical_record.controller;

import com.docdock.group09.web_gateway.module.medical_record.MedicalRecordServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordServiceClient medicalRecordServiceClient;

    @GetMapping
    public Object getMedicalRecords(@RequestParam Map<String, String> params) {
        return medicalRecordServiceClient.filterRecords(params);
    }

    @PostMapping
    public Object addMedicalRecord(@RequestBody Object medicalRecordCreateRequest) {
        return medicalRecordServiceClient.createMedicalRecord(medicalRecordCreateRequest);
    }

    @GetMapping("{id}")
    public Object getMedicalRecordById(@PathVariable("id") String id) {
        return medicalRecordServiceClient.getByMedicalRecordId(id);
    }
}

package com.docdock.group09.web_gateway.module.medical_record;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "medical-record-service-client", url = "${api.medical-record-service-host}")
public interface MedicalRecordServiceClient {
    @PostMapping("/medical-records")
    ResponseEntity<?> createMedicalRecord(@RequestBody Object request);

    @GetMapping("/medical-records")
    ResponseEntity<?> filterRecords(@RequestParam Map<String, String> params);

    @GetMapping("medical-records/{id}")
    ResponseEntity<?> getByMedicalRecordId(@PathVariable("id") String id);
}

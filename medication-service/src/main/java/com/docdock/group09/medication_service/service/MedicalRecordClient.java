package com.docdock.group09.medication_service.service;

import com.docdock.group09.medication_service.dto.response.DocDockResponse;
import com.docdock.group09.medication_service.dto.response.MedicalRecordInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "medical-record-service", url = "${api.medical-record-service-host}")
public interface MedicalRecordClient {
    @GetMapping("medical-records/{id}")
    DocDockResponse<MedicalRecordInfo> getMedicalRecordById(@PathVariable("id") String id);
}

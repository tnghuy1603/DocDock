package com.docdock.group09.web_gateway.module.medical_record;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "medical-record-service-client", url = "${api.medical-record-service-host}")
public interface MedicalRecordServiceClient {
}

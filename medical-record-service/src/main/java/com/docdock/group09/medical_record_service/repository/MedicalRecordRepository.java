package com.docdock.group09.medical_record_service.repository;

import com.docdock.group09.medical_record_service.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecordEntity, String>, JpaSpecificationExecutor<MedicalRecordEntity> {
}

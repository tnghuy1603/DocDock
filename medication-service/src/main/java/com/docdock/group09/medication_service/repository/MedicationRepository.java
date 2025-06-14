package com.docdock.group09.medication_service.repository;

import com.docdock.group09.medication_service.entity.MedicationEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationEntity, String>, JpaSpecificationExecutor<MedicationEntity> {
    List<MedicationEntity> findByIdIn(List<String> ids);
}

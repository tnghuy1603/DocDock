package com.docdock.group09.medication_service.repository;

import com.docdock.group09.medication_service.entity.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {
}

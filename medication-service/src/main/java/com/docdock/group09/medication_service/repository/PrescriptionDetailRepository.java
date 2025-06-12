package com.docdock.group09.medication_service.repository;

import com.docdock.group09.medication_service.entity.PrescriptionDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetailEntity, Long> {
}

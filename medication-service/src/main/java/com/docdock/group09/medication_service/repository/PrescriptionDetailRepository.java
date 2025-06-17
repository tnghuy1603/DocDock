package com.docdock.group09.medication_service.repository;

import com.docdock.group09.medication_service.entity.PrescriptionDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetailEntity, String> {
    List<PrescriptionDetailEntity> findByPrescription_Id(String  prescriptionId);
}

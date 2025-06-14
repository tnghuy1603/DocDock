package com.docdock.group09.medication_service.repository;

import com.docdock.group09.medication_service.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, String> {
}

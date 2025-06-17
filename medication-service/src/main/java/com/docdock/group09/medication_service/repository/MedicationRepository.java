package com.docdock.group09.medication_service.repository;

import com.docdock.group09.medication_service.entity.MedicationEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationEntity, String>, JpaSpecificationExecutor<MedicationEntity> {
    List<MedicationEntity> findByIdIn(List<String> ids);
    @Query(value = "SELECT COUNT(*) AS total, " +
            "SUM(CASE WHEN expiry_date <= :expireDate THEN 1 ELSE 0 END) AS expireSoon, " +
            "SUM(CASE WHEN stock_quantity < :threshold THEN 1 ELSE 0 END) AS lowStock " +
            "FROM medication",
            nativeQuery = true)
    List<Object[]> getMedicineStats(@Param("expireDate") LocalDate expireDate,
                              @Param("threshold") int threshold);
    List<MedicationEntity> findByIdIsIn(List<String> ids);
}

package com.docdock.group09.user_service.repository;

import com.docdock.group09.user_service.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, String>, JpaSpecificationExecutor<PatientEntity> {
    List<PatientEntity> findByIdIsIn(List<String> ids);
}

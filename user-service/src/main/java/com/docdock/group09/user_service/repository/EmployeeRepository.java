package com.docdock.group09.user_service.repository;

import com.docdock.group09.user_service.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String>, JpaSpecificationExecutor<EmployeeEntity> {
    List<EmployeeEntity> findByIdIsIn(List<String> ids);
    List<EmployeeEntity> findByNameContainingIgnoreCase(String name);

}

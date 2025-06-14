package com.docdock.group09.appointment_service.repository;

import com.docdock.group09.appointment_service.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, String> {
}

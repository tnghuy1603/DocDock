package com.docdock.group09.appointment_service.repository;

import com.docdock.group09.appointment_service.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, String> {
    List<AppointmentEntity> findByDoctorIdAndStartTimeBetween(String doctorId, LocalDateTime startTimeAfter, LocalDateTime startTimeBefore);
}

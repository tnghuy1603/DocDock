package com.docdock.group09.appointment_service.repository;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import com.docdock.group09.appointment_service.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, String>, AppointmentRepositoryCustom {
    List<AppointmentEntity> findByDoctorIdAndStartTimeBetweenAndStatusIsNot(String doctorId, LocalDateTime startTimeAfter, LocalDateTime startTimeBefore, AppointmentStatus status);
    List<AppointmentEntity> findByStatusIsAndStartTimeAfter(AppointmentStatus status, LocalDateTime startTimeAfter);
    List<AppointmentEntity> findByStatusIsAndStartTimeBetween(AppointmentStatus status, LocalDateTime startTimeAfter, LocalDateTime startTimeBefore);
}

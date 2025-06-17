package com.docdock.group09.appointment_service.service.impl;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import com.docdock.group09.appointment_service.dto.mapper.AppointmentMapper;
import com.docdock.group09.appointment_service.dto.request.AppointmentUpdateRequest;
import com.docdock.group09.appointment_service.dto.request.BookAppointmentRequest;
import com.docdock.group09.appointment_service.dto.request.FilterAppointmentRequest;
import com.docdock.group09.appointment_service.dto.response.AppointmentResponse;
import com.docdock.group09.appointment_service.entity.AppointmentEntity;
import com.docdock.group09.appointment_service.entity.DoctorScheduleEntity;
import com.docdock.group09.appointment_service.repository.AppointmentRepository;
import com.docdock.group09.appointment_service.repository.DoctorScheduleRepository;
import com.docdock.group09.appointment_service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorScheduleRepository doctorScheduleRepository;
    @Override
    public AppointmentResponse bookAppointment(BookAppointmentRequest request) {
        //Assuming patient can not book appointment with span of 2 days
        validateTimeFrame(request.getDoctorId(), request.getStartTime(), request.getEndTime());
        AppointmentEntity appointmentEntity = appointmentMapper.toEntity(request);
        appointmentEntity.setStatus(AppointmentStatus.PENDING);
        appointmentEntity.setCreatedAt(LocalDateTime.now());
        appointmentEntity.setUpdatedAt(LocalDateTime.now());
        appointmentEntity = appointmentRepository.save(appointmentEntity);
        return appointmentMapper.toModel(appointmentEntity);
        //TODO check patient and doctorId
    }

    @Override
    public AppointmentResponse cancelAppointment(AppointmentUpdateRequest request, String appointmentId ) {
        AppointmentEntity existingEntity = appointmentRepository.findById(appointmentId).
                orElseThrow(() ->new RuntimeException("Not found any appointment"));
        //TODO check initiator id
        //After confirmation
        if (AppointmentStatus.COMPLETED.equals(existingEntity.getStatus()) || AppointmentStatus.CANCELLED.equals(existingEntity.getStatus())) {
            throw new RuntimeException("Can not cancel completed appointment or canceled appointment");
        }
        existingEntity.setStatus(AppointmentStatus.CANCELLED);
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity = appointmentRepository.save(existingEntity);
        return appointmentMapper.toModel(existingEntity);
    }

    @Override
    public AppointmentResponse confirmAppointment(AppointmentUpdateRequest request, String appointmentId) {
        AppointmentEntity existingEntity = appointmentRepository.findById(appointmentId).
            orElseThrow(() ->new RuntimeException("Not found any appointment"));
        if (!AppointmentStatus.PENDING.equals(existingEntity.getStatus())) {
            throw new RuntimeException("Appointment is not in PENDING state");
        }
        existingEntity.setStatus(AppointmentStatus.CONFIRMED);
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity = appointmentRepository.save(existingEntity);
        return appointmentMapper.toModel(existingEntity);
    }

    @Override
    public AppointmentResponse getAppointmentDetails(String appointmentId) {
        AppointmentEntity entity = appointmentRepository.findById(appointmentId).orElseThrow(() ->new RuntimeException("Not found any appointment"));
        return appointmentMapper.toModel(entity);
    }

    @Override
    public AppointmentResponse updateAppointment(AppointmentUpdateRequest request, String appointmentId) {
        return null;
    }

    @Override
    public AppointmentResponse completeAppointment(AppointmentUpdateRequest request, String appointmentId) {
        return null;
    }

    @Override
    public List<AppointmentResponse> filterAppointments(FilterAppointmentRequest request) {
        List<AppointmentEntity> appointmentEntities = appointmentRepository.findAll()
    }

    private void validateTimeFrame(String doctorId, LocalDateTime startAt, LocalDateTime endAt) {
        LocalDateTime atStartOfTheDay = startAt .toLocalDate().atStartOfDay();
        LocalDateTime atEndOfTheDay = endAt .toLocalDate().atTime(LocalTime.MAX);
        List<AppointmentEntity> appointmentInADay = appointmentRepository.findByDoctorIdAndStartTimeBetween(doctorId, atStartOfTheDay, atEndOfTheDay);
        List<DoctorScheduleEntity> doctorSchedules = doctorScheduleRepository.findByDoctorIdAndStartTimeBetween(doctorId, atStartOfTheDay, atEndOfTheDay);
        boolean isOverlap = appointmentInADay.stream()
                .anyMatch(appointment -> startAt.isBefore(appointment.getEndTime()) && endAt.isAfter(appointment.getStartTime()));
        if (isOverlap) {
            throw new RuntimeException("Overlapping Appointment");
        }

        boolean isWithinSchedule = doctorSchedules.stream()
                .anyMatch(schedule -> startAt.isAfter(schedule.getStartTime()) && endAt.isBefore(schedule.getEndTime()));
        if (!isWithinSchedule) {
            throw new RuntimeException("Doctor is not available for this appointment");
        }
    }

}

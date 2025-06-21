package com.docdock.group09.appointment_service.service.impl;

import com.docdock.group09.appointment_service.constant.AppointmentStatus;
import com.docdock.group09.appointment_service.constant.AppointmentType;
import com.docdock.group09.appointment_service.dto.mapper.AppointmentMapper;
import com.docdock.group09.appointment_service.dto.request.AppointmentUpdateRequest;
import com.docdock.group09.appointment_service.dto.request.BookAppointmentRequest;
import com.docdock.group09.appointment_service.dto.request.FilterAppointmentRequest;
import com.docdock.group09.appointment_service.dto.response.AppointmentFilterStatsResponse;
import com.docdock.group09.appointment_service.dto.response.AppointmentResponse;
import com.docdock.group09.appointment_service.dto.response.AppointmentStatusCount;
import com.docdock.group09.appointment_service.entity.AppointmentEntity;
import com.docdock.group09.appointment_service.entity.DoctorScheduleEntity;
import com.docdock.group09.appointment_service.repository.AppointmentRepository;
import com.docdock.group09.appointment_service.repository.DoctorScheduleRepository;
import com.docdock.group09.appointment_service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.docdock.group09.appointment_service.constant.AppointmentStatus.PENDING;

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
        appointmentEntity.setStatus(PENDING);
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
        if (AppointmentStatus.COMPLETED.equals(existingEntity.getStatus())
                || AppointmentStatus.CANCELLED.equals(existingEntity.getStatus())
                || AppointmentStatus.CONFIRMED.equals(existingEntity.getStatus())) {
            throw new RuntimeException("Can not cancel/completed/confirmed appointment");
        }
        existingEntity.setStatus(AppointmentStatus.CANCELLED);
        existingEntity.setCancelReason(request.getCancelReason());
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity = appointmentRepository.save(existingEntity);
        return appointmentMapper.toModel(existingEntity);
    }

    @Override
    public AppointmentResponse confirmAppointment(AppointmentUpdateRequest request, String appointmentId) {
        AppointmentEntity existingEntity = appointmentRepository.findById(appointmentId).
            orElseThrow(() ->new RuntimeException("Not found any appointment"));
        if (!PENDING.equals(existingEntity.getStatus())) {
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
        AppointmentEntity existingEntity = appointmentRepository.findById(appointmentId).
                orElseThrow(() ->new RuntimeException("Not found any appointment"));
        if (!PENDING.equals(existingEntity.getStatus())) {
            throw new RuntimeException("Can only update PENDING appointment");
        }
        //TODO limit the time to update appoint and how can update appointment
        existingEntity.setReason(request.getReason());
        if (request.getStartTime() != null && request.getEndTime() != null) {
            validateTimeFrame(existingEntity.getDoctorId(), request.getStartTime(), request.getEndTime());
        }
        existingEntity.setStartTime(request.getStartTime());
        existingEntity.setEndTime(request.getEndTime());
        existingEntity.setType(request.getType());
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity = appointmentRepository.save(existingEntity);
        return appointmentMapper.toModel(existingEntity);
    }

    @Override
    public AppointmentResponse completeAppointment(AppointmentUpdateRequest request, String appointmentId) {
        AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Not found any appointment"));
        if (AppointmentStatus.CANCELLED.equals(appointmentEntity.getStatus()) || PENDING.equals(appointmentEntity.getStatus())) {
            throw new RuntimeException("Bad request can not complete CANCELLED or PENDING appointment");
        }
        appointmentEntity.setStatus(AppointmentStatus.COMPLETED);
        appointmentEntity.setUpdatedAt(LocalDateTime.now());
        appointmentEntity = appointmentRepository.save(appointmentEntity);
        return appointmentMapper.toModel(appointmentEntity);
    }

    @Override
    public Page<AppointmentResponse> filterAppointments(FilterAppointmentRequest request) {
        Page<AppointmentEntity> appointmentEntityPage = appointmentRepository.filterAppointment(request);
        List<AppointmentResponse> appointmentResponses = appointmentMapper.toModelList(appointmentEntityPage.getContent());
        return new PageImpl<>(appointmentResponses, appointmentEntityPage.getPageable(), appointmentEntityPage.getTotalElements());
    }

    @Override
    public AppointmentFilterStatsResponse getStats(FilterAppointmentRequest request) {
        List<AppointmentStatusCount> appointmentStatusCounts = appointmentRepository.countByFilter(request);
        AppointmentFilterStatsResponse response = new AppointmentFilterStatsResponse();
        for (AppointmentStatusCount appointmentStatusCount : appointmentStatusCounts) {
            int count = appointmentStatusCount.getCount();
            switch (appointmentStatusCount.getStatus()) {
                case PENDING:
                    response.setNumberOfPending(count);
                    break;
                case COMPLETED:
                    response.setNumberOfCompleted(count);
                    break;
                case CONFIRMED:
                    response.setNumberOfConfirmed(count);
                    break;
                case CANCELLED:
                    response.setNumberOfCancelled(count);
                    break;
                default:
                    break;
            }
        }
        return response;
    }

    private void validateTimeFrame(String doctorId, LocalDateTime startAt, LocalDateTime endAt) {
        LocalDateTime atStartOfTheDay = startAt .toLocalDate().atStartOfDay();
        LocalDateTime atEndOfTheDay = endAt .toLocalDate().atTime(LocalTime.MAX);

        List<DoctorScheduleEntity> doctorSchedules = doctorScheduleRepository.findByDoctorIdAndStartTimeBetween(doctorId, atStartOfTheDay, atEndOfTheDay);
        boolean isWithinSchedule = doctorSchedules.stream()
                .anyMatch(schedule -> startAt.isAfter(schedule.getStartTime()) && endAt.isBefore(schedule.getEndTime()));
        if (!isWithinSchedule) {
            throw new RuntimeException("Doctor is not available for this appointment");
        }

        List<AppointmentEntity> appointmentInADay = appointmentRepository.findByDoctorIdAndStartTimeBetweenAndStatusIsNot(doctorId, atStartOfTheDay, atEndOfTheDay, AppointmentStatus.CANCELLED);
        boolean isOverlap = appointmentInADay.stream()
                .anyMatch(appointment -> startAt.isBefore(appointment.getEndTime()) && endAt.isAfter(appointment.getStartTime()));
        if (isOverlap) {
            throw new RuntimeException("Overlapping Appointment");
        }
    }

}

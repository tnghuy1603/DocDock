package com.docdock.group09.appointment_service.service;

import com.docdock.group09.appointment_service.dto.request.AppointmentUpdateRequest;
import com.docdock.group09.appointment_service.dto.request.BookAppointmentRequest;
import com.docdock.group09.appointment_service.dto.request.FilterAppointmentRequest;
import com.docdock.group09.appointment_service.dto.response.AppointmentFilterStatsResponse;
import com.docdock.group09.appointment_service.dto.response.AppointmentResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse bookAppointment(BookAppointmentRequest request);
    AppointmentResponse cancelAppointment(AppointmentUpdateRequest request, String appointmentId);
    AppointmentResponse confirmAppointment(AppointmentUpdateRequest request, String appointmentId);
    AppointmentResponse getAppointmentDetails(String appointmentId);
    AppointmentResponse updateAppointment(AppointmentUpdateRequest request, String appointmentId);
    AppointmentResponse completeAppointment(AppointmentUpdateRequest request, String appointmentId);
    Page<AppointmentResponse> filterAppointments(FilterAppointmentRequest request);
    AppointmentFilterStatsResponse getStats(FilterAppointmentRequest request);
}

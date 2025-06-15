package com.docdock.group09.appointment_service.service;

import com.docdock.group09.appointment_service.dto.request.BookAppointmentRequest;
import com.docdock.group09.appointment_service.dto.request.CancelAppointmentRequest;
import com.docdock.group09.appointment_service.dto.response.AppointmentResponse;

public interface AppointmentService {
    AppointmentResponse bookAppointment(BookAppointmentRequest request);
    AppointmentResponse cancelAppointment(CancelAppointmentRequest request, String appointmentId);
    AppointmentResponse confirmAppointment(String appointmentId);

}

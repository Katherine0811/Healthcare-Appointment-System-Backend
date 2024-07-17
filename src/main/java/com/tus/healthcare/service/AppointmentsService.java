package com.tus.healthcare.service;

import java.util.List;
import com.tus.healthcare.model.Appointments;

public interface AppointmentsService {
    List<Appointments> getAllAppointments();
    Appointments getAppointmentById(Long id);
    Appointments saveAppointment(Appointments appointment);
    void deleteAppointment(Long id);
}

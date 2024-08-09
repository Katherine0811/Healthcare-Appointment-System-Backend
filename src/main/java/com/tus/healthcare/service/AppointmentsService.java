package com.tus.healthcare.service;

import java.time.LocalDate;
import java.util.List;

import com.tus.healthcare.dto.AppointmentDTO;
import com.tus.healthcare.model.Appointments;

public interface AppointmentsService {
    List<AppointmentDTO> getUpcomingAppointments(Long patientId);
	List<AppointmentDTO> getUpcomingAppointmentsByProvider(Long providerId);
    List<AppointmentDTO> getPastAppointments(Long patientId);
    void cancelAppointment(Long appointmentId);
    AppointmentDTO rescheduleAppointment(Long appointmentId, AppointmentDTO newDetails);
	void completeAppointment(Long appointmentId);
	List<AppointmentDTO> getAppointmentsByProviderAndDate(Long providerId, LocalDate appointmentDate);
	AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO);

}

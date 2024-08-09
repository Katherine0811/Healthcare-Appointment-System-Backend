package com.tus.healthcare.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tus.healthcare.dto.AppointmentDTO;
import com.tus.healthcare.exception.AppointmentsException;
import com.tus.healthcare.model.Appointments;
import com.tus.healthcare.model.Patients;
import com.tus.healthcare.model.Providers;
import com.tus.healthcare.repository.AppointmentsRepository;
import com.tus.healthcare.repository.PatientsRepository;
import com.tus.healthcare.repository.ProvidersRepository;
import com.tus.healthcare.service.AppointmentsService;

@Service
public class AppointmentsServiceImpl implements AppointmentsService {

    @Autowired
    private AppointmentsRepository appointmentsRepository;
    
    @Autowired
    private PatientsRepository patientsRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    @Override
    public List<AppointmentDTO> getUpcomingAppointments(Long patientId) {
        List<Appointments> appointments = appointmentsRepository.findByStatusAndPatientPatientId("Scheduled", patientId);
        if (appointments.isEmpty()) {
            throw new AppointmentsException("No upcoming appointments found.");
        }
        return appointments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
	@Override
	public List<AppointmentDTO> getUpcomingAppointmentsByProvider(Long providerId) {
		List<Appointments> appointments = appointmentsRepository.findByStatusAndProviderProviderId("Scheduled", providerId);
        if (appointments.isEmpty()) {
            throw new AppointmentsException("No upcoming appointments found.");
        }
        return appointments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

	@Override
    public List<AppointmentDTO> getPastAppointments(Long patientId) {
        List<String> statuses = Arrays.asList("Completed", "Cancelled");
        List<Appointments> appointments = appointmentsRepository.findByStatusInAndPatientPatientId(statuses, patientId);

        if (appointments.isEmpty()) {
            throw new AppointmentsException("No past appointments found.");
        }

        return appointments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO) {
        Patients patient = patientsRepository.findById(appointmentDTO.getPatientId())
                .orElseThrow(() -> new AppointmentsException("Patient not found"));
        
        Providers provider = providersRepository.findById(appointmentDTO.getProviderId())
                .orElseThrow(() -> new AppointmentsException("Provider not found"));

        Appointments appointment = new Appointments();
        appointment.setPatient(patient);
        appointment.setProvider(provider);
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        appointment.setStatus("Scheduled");
        return convertToDTO(appointmentsRepository.save(appointment));
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        Appointments appointment = appointmentsRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentsException("Appointment not found"));
        appointment.setStatus("Cancelled");
        appointmentsRepository.save(appointment);
    }

    @Override
    public AppointmentDTO rescheduleAppointment(Long appointmentId, AppointmentDTO newAppointment) {
        Appointments appointment = appointmentsRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentsException("Appointment not found"));
        appointment.setAppointmentDate(newAppointment.getAppointmentDate());
        appointment.setAppointmentTime(newAppointment.getAppointmentTime());
        return convertToDTO(appointmentsRepository.save(appointment));
    }

	@Override
	public void completeAppointment(Long appointmentId) {
        Appointments appointment = appointmentsRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentsException("Appointment not found"));
        appointment.setStatus("Completed");
        appointmentsRepository.save(appointment);
    }
    
	@Override
	public List<AppointmentDTO> getAppointmentsByProviderAndDate(Long providerId, LocalDate appointmentDate) {
		List<Appointments> appointments = appointmentsRepository.findByProviderProviderIdAndAppointmentDate(providerId, appointmentDate);
		if (appointments.isEmpty()) {
			return Collections.emptyList();
        }

        return appointments.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

    private AppointmentDTO convertToDTO(Appointments appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setPatientId(appointment.getPatient().getPatientId());
        dto.setPatientName(appointment.getPatient().getUser().getName());
        dto.setProviderId(appointment.getProvider().getProviderId());
        dto.setProviderName(appointment.getProvider().getUser().getName());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());
        return dto;
    }
}

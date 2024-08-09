package com.tus.healthcare.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tus.healthcare.dto.AppointmentDTO;
import com.tus.healthcare.exception.AppointmentsException;
import com.tus.healthcare.exception.LoginException;
import com.tus.healthcare.model.Appointments;
import com.tus.healthcare.service.AppointmentsService;

@RestController
@RequestMapping("/appointments")
public class AppointmentsController {

    @Autowired
    private AppointmentsService appointmentsService;

    @GetMapping("/upcoming/{patientId}")
    public ResponseEntity<?> getUpcomingAppointments(@PathVariable Long patientId) {
        try {
        	List<AppointmentDTO> upcomingAppointments = appointmentsService.getUpcomingAppointments(patientId);
        	return new ResponseEntity<>(upcomingAppointments, HttpStatus.OK);
        } catch (AppointmentsException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/upcoming/provider/{providerId}")
    public ResponseEntity<?> getUpcomingAppointmentsByProvider(@PathVariable Long providerId) {
        try {
        	List<AppointmentDTO> upcomingAppointments = appointmentsService.getUpcomingAppointmentsByProvider(providerId);
            return ResponseEntity.ok(upcomingAppointments);
        } catch (AppointmentsException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/past/{patientId}")
    public ResponseEntity<?> getPastAppointments(@PathVariable Long patientId) {
        try {
        	List<AppointmentDTO> pastAppointments = appointmentsService.getPastAppointments(patientId);
        	return new ResponseEntity<>(pastAppointments, HttpStatus.OK);
        } catch (AppointmentsException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/book")
    public AppointmentDTO bookAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentsService.bookAppointment(appointmentDTO);
    }

    @PutMapping("/reschedule/{appointmentId}")
    public AppointmentDTO rescheduleAppointment(@PathVariable Long appointmentId, @RequestBody AppointmentDTO newAppointment) {
        return appointmentsService.rescheduleAppointment(appointmentId, newAppointment);
    }

    @DeleteMapping("/cancel/{appointmentId}")
    public void AppointmentDTO(@PathVariable Long appointmentId) {
        appointmentsService.cancelAppointment(appointmentId);
    }
    
    @PutMapping("/complete/{appointmentId}")
    public ResponseEntity<Void> completeAppointment(@PathVariable Long appointmentId) {
        appointmentsService.completeAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/provider/{providerId}/date/{date}")
    public List<AppointmentDTO> getAppointmentsByProviderAndDate(
        @PathVariable Long providerId,
        @PathVariable LocalDate date
    ) {
        return appointmentsService.getAppointmentsByProviderAndDate(providerId, date);
    }
}
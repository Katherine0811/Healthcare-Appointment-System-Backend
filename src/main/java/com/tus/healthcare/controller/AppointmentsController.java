package com.tus.healthcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tus.healthcare.model.Appointments;
import com.tus.healthcare.service.impl.AppointmentsServiceImpl;

@RestController
@RequestMapping("/appointments")
public class AppointmentsController {
    @Autowired
    private AppointmentsServiceImpl appointmentsService;

    @GetMapping
    public List<Appointments> getAllAppointments() {
        return appointmentsService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public Appointments getAppointmentById(@PathVariable Long id) {
        return appointmentsService.getAppointmentById(id);
    }

    @PostMapping
    public Appointments createAppointment(@RequestBody Appointments appointment) {
        return appointmentsService.saveAppointment(appointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentsService.deleteAppointment(id);
    }
}
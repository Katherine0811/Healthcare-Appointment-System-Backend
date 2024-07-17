package com.tus.healthcare.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tus.healthcare.model.Appointments;
import com.tus.healthcare.repository.AppointmentsRepository;
import com.tus.healthcare.service.AppointmentsService;

@Service
public class AppointmentsServiceImpl implements AppointmentsService {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Override
    public List<Appointments> getAllAppointments() {
        return appointmentsRepository.findAll();
    }

    @Override
    public Appointments getAppointmentById(Long id) {
        return appointmentsRepository.findById(id).orElse(null);
    }

    @Override
    public Appointments saveAppointment(Appointments appointment) {
        return appointmentsRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentsRepository.deleteById(id);
    }
}

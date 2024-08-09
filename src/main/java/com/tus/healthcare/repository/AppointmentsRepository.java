package com.tus.healthcare.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.healthcare.model.Appointments;
import com.tus.healthcare.model.Availability;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Long> {
//    List<Appointments> findByPatientUserUserId(Long userId);
//    List<Appointments> findByProviderUserUserId(Long userId);
    List<Appointments> findByStatusAndPatientPatientId(String status, Long patientId);
    List<Appointments> findByStatusAndProviderProviderId(String status, Long providerId);
	List<Appointments> findByStatusInAndPatientPatientId(List<String> statuses, Long patientId);
	List<Appointments> findByProviderProviderIdAndAppointmentDate(Long providerId, LocalDate appointmentDate);
}

package com.tus.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.healthcare.model.Appointments;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Long> {
}

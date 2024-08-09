package com.tus.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.healthcare.model.Patients;
import com.tus.healthcare.model.Users;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Long> {
	Patients findByUser(Users user);
	Patients findByPatientId(Long patientId);
}

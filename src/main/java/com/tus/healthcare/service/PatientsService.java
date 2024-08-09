package com.tus.healthcare.service;

import com.tus.healthcare.model.Patients;

public interface PatientsService {
	Patients getPatientRecords(Long patientId);

}

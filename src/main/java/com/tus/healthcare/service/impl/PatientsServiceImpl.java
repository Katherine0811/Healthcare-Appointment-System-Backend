package com.tus.healthcare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tus.healthcare.model.Patients;
import com.tus.healthcare.repository.PatientsRepository;
import com.tus.healthcare.service.PatientsService;

@Service
public class PatientsServiceImpl implements PatientsService{

    @Autowired
    private PatientsRepository patientsRepository;
	
	@Override
	public Patients getPatientRecords(Long patientId) {
		return patientsRepository.findByPatientId(patientId);
	}

}

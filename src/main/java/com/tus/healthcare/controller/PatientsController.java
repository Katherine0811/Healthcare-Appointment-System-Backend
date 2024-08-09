package com.tus.healthcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tus.healthcare.model.Patients;
import com.tus.healthcare.service.PatientsService;

@RestController
@RequestMapping("/patients")
public class PatientsController {

    @Autowired
    private PatientsService patientsService;

    @GetMapping("/{patientId}")
    public Patients getPatientRecords(@PathVariable Long patientId) {
    	return patientsService.getPatientRecords(patientId);
    }
}
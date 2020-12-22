package com.sheke.clinicals.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sheke.clinicals.dto.ClinicalDataRequest;
import com.sheke.clinicals.entities.ClinicalData;
import com.sheke.clinicals.entities.Patient;
import com.sheke.clinicals.repos.ClinicalDataRepository;
import com.sheke.clinicals.repos.PatientRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {
	
	private ClinicalDataRepository clinicalDataRepo;
	private PatientRepository patientRepo;
	
	@Autowired
	ClinicalDataController(ClinicalDataRepository clinicalDataRepo, PatientRepository patientRepo){
		this.clinicalDataRepo = clinicalDataRepo;
		this.patientRepo = patientRepo;
	}

	@RequestMapping(value="/clinicals", method=RequestMethod.POST)
	public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest request) {
		Patient patient = patientRepo.findById(request.getPatientId()).get();
		
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setComponentName(request.getComponentName());
		clinicalData.setComponentValue(request.getComponentValue());
		clinicalData.setPatient(patient);
		return clinicalDataRepo.save(clinicalData);
	}
	
	
}

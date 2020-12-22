package com.sheke.clinicals.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sheke.clinicals.entities.ClinicalData;
import com.sheke.clinicals.entities.Patient;
import com.sheke.clinicals.repos.PatientRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {
	
	private PatientRepository repository;
	
	Map<String,String> filter= new HashMap<>();
	
	@Autowired
	PatientController(PatientRepository repository){
		this.repository=repository;
	}
	
	@RequestMapping(value="/patients", method=RequestMethod.GET)
	public List<Patient> getPatients(){
		return repository.findAll();
	}
	
	@RequestMapping(value="patients/{id}")
	public Patient getPatient(@PathVariable("id") int id) {
		return repository.findById(id).get();
	}
	
	@RequestMapping(value="/patients",method=RequestMethod.POST)
	public Patient savePatient(@RequestBody Patient patient) {
		return repository.save(patient);
	}
	
	@RequestMapping(value="/patients/analyze/{id}", method=RequestMethod.POST)
	public Patient analyze(@PathVariable int id) {
		Patient patient = repository.findById(id).get();
		List<ClinicalData> clinicalData = patient.getClinicalData();
		ArrayList<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for(ClinicalData eachEntry:duplicateClinicalData) {
			if(filter.containsKey(eachEntry.getComponentName())) {
				clinicalData.remove(eachEntry);
			}else {
				filter.put(eachEntry.getComponentName(),null);
			}
			if(eachEntry.getComponentName().endsWith("hw")){
				String[] heightAndWeight = eachEntry.getComponentValue().split("/");
				
				if (heightAndWeight !=null && heightAndWeight.length>1) {
				float heightInMetres = Float.parseFloat(heightAndWeight[0])*0.4536F;
				float bmi = Float.parseFloat(heightAndWeight[1])/(heightInMetres*heightInMetres);
				
				ClinicalData bmiData = new ClinicalData();
				bmiData.setComponentName("bmi");
				bmiData.setComponentValue(Float.toString(bmi));
				
				clinicalData.add(bmiData);
				}
			}
		}
		return patient;
	}

}

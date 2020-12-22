package com.sheke.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheke.clinicals.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}

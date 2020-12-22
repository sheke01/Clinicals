package com.sheke.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheke.clinicals.entities.ClinicalData;

public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Integer> {

}

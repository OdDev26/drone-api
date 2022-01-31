package com.example.droneapi.repository;

import com.example.droneapi.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository <Medication,Integer> {
    Medication findMedicationById(Integer medicationId);
    Medication findMedicationByDrone_Id(Integer droneId);
    List<Medication> findMedicationsByDrone_Id(Integer droneId);
}

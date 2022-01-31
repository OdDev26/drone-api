package com.example.droneapi.service;

import com.example.droneapi.dto.MedicationDto;
import com.example.droneapi.exceptions.CustomException;
import com.example.droneapi.model.Medication;
import com.example.droneapi.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MedicationService {
    @Autowired
    private MedicationRepository medicationRepository;

    public void saveMedicationDetails(MedicationDto medicationDto) {
        String regex1 = "([A-Za-z0-9-_\\s*]+)";
        String regex2 = "([A-Z0-9_]+)";

        Pattern pattern1 = Pattern.compile(regex1);
        Pattern pattern2 = Pattern.compile(regex2);


        Medication medication = new Medication();
        medication.setCode(medicationDto.getCode());
        medication.setWeight(medicationDto.getMedication_weight());
        medication.setName(medicationDto.getName());

        if (pattern1.matcher(medicationDto.getName()).matches() && pattern2.matcher(medicationDto.getCode()).matches()) {
            medicationRepository.save(medication);
        }
        if (!pattern1.matcher(medicationDto.getName()).matches()) {
            throw new CustomException("Medication name can only contain alphanumeric letters, _ and - characters");
        }
        if (!pattern1.matcher(medicationDto.getCode()).matches()) {
            throw new CustomException("Code can only contain contain Upper case, numbers and _ characters");
        }
    }
}

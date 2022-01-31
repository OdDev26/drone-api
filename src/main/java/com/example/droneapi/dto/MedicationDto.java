package com.example.droneapi.dto;

import com.example.droneapi.model.Drone;
import lombok.Data;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

@Data
public class MedicationDto {
    private String name;
    private Integer medication_weight;
    private String code;

}

package com.example.droneapi.dto;

import com.example.droneapi.model.Model;
import com.example.droneapi.model.State;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class DroneDto {
    private String serialNo;
    private Model model;
    private Integer drone_weight;
    private Integer batteryCapacity;
    private Integer batteryLevel;
}

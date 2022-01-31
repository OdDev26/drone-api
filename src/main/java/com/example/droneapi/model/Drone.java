package com.example.droneapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Integer id;
    private String serialNo;
    @Enumerated(EnumType.STRING)
    private Model model;
    private Integer weight;
    private Integer availableCapacity;
    private Integer batteryCapacity;
    @Enumerated(EnumType.STRING)
    private State droneState;
    private Integer batteryLevel;
}

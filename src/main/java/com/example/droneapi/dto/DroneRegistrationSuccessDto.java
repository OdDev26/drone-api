package com.example.droneapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class DroneRegistrationSuccessDto {
    private String message;
    private HttpStatus status;
}

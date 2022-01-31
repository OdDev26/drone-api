package com.example.droneapi.controller;

import com.example.droneapi.dto.*;
import com.example.droneapi.exceptions.CustomException;
import com.example.droneapi.service.DroneService;
import com.example.droneapi.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DispatchController {
    @Autowired
    public DroneService droneService;

    @Autowired
    private MedicationService medicationService;

    @PostMapping("/saveDroneData")
    public ResponseEntity<?> saveDroneData(@Valid @RequestBody DroneDto droneDto) {
        droneService.registerDrone(droneDto);
        if (HttpStatus.ACCEPTED.is2xxSuccessful()) {
            DroneRegistrationSuccessDto droneRegistrationSuccessDto = new DroneRegistrationSuccessDto();
            droneRegistrationSuccessDto.setMessage("Drone details successfully registered");
            droneRegistrationSuccessDto.setStatus(HttpStatus.OK);

            return new ResponseEntity<>(droneRegistrationSuccessDto, HttpStatus.OK);

        }
       return null;
    }


    @PostMapping("/saveMedicationDetails")
    public ResponseEntity<?> saveMedicationDetails(@Valid @RequestBody MedicationDto medicationDto) {
        medicationService.saveMedicationDetails(medicationDto);
        if (HttpStatus.ACCEPTED.is2xxSuccessful()) {
            MedicationRegistrationSuccessDto medicationRegistrationSuccessDto = new MedicationRegistrationSuccessDto();
            medicationRegistrationSuccessDto.setMessage("Medication details successfully registered");
            medicationRegistrationSuccessDto.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(medicationRegistrationSuccessDto, HttpStatus.OK);
        }
        return null;
    }
    @PostMapping("/loadDrone/{serialNo}/{medicationId}")
    public ResponseEntity<?> loadDrone(@PathVariable String serialNo, @PathVariable Integer medicationId) {
        droneService.loadDrone(serialNo, medicationId);
        if (HttpStatus.ACCEPTED.is2xxSuccessful()) {
            DroneLoadingSuccessDto droneLoadingSuccessDto = new DroneLoadingSuccessDto();
            droneLoadingSuccessDto.setMessage("Drone successfully loaded");
            droneLoadingSuccessDto.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(droneLoadingSuccessDto, HttpStatus.OK);
        } else {
            throw new CustomException("Drone could not be loaded");
        }
    }

    @GetMapping("/listOfLoadedMedications/{droneId}")
    public ResponseEntity<?> getListOfLoadedMedications(@PathVariable Integer droneId){
        return droneService.getLoadedMedicationItems(droneId);
    }

    @GetMapping("/fetchIdleDrones/{state}")
    public ResponseEntity<?> fetchIdleDrones(@PathVariable String state){
        return droneService.availableDronesForLoading(state);
    }
    @PutMapping("/updateBatteryLevel/{serialNo}")
    public ResponseEntity<?> updateDroneBatteryLevel(@RequestBody BatteryDto batteryDto, @PathVariable String serialNo) {
        droneService.updateBatteryLevel(batteryDto, serialNo);
        if (HttpStatus.ACCEPTED.is2xxSuccessful()) {
            BatteryLevelUpdateSuccessDto batteryLevelUpdateSuccessDto = new BatteryLevelUpdateSuccessDto();
            batteryLevelUpdateSuccessDto.setMessage("Battery level details successfully updated");
            batteryLevelUpdateSuccessDto.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(batteryLevelUpdateSuccessDto, HttpStatus.OK);
        } else {
            return null;
        }
    }
    @GetMapping("/getBatteryLevel/{serialNo}")
    public ResponseEntity<?> getBatteryLevel(@PathVariable String serialNo){
        return droneService.returnBatteryLevel(serialNo);
    }

}

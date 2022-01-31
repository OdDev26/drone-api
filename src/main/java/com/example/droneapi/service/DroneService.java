package com.example.droneapi.service;

import com.example.droneapi.dto.BatteryDto;
import com.example.droneapi.dto.DroneDto;
import com.example.droneapi.exceptions.CustomException;
import com.example.droneapi.model.Drone;
import com.example.droneapi.model.Medication;
import com.example.droneapi.model.State;
import com.example.droneapi.repository.DroneRepository;
import com.example.droneapi.repository.MedicationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.droneapi.model.State.*;

@Service
public class DroneService {
    private DroneRepository droneRepository;
    private MedicationRepository medicationRepository;


    public DroneService(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository= medicationRepository;
    }

    public ResponseEntity<?> registerDrone(DroneDto droneDto) {
        Drone drone = new Drone();
        if (droneDto.getDrone_weight() < 500 && droneDto.getSerialNo().length()<100) {
            drone.setWeight(droneDto.getDrone_weight());
            drone.setSerialNo(droneDto.getSerialNo());
            drone.setDroneState(IDLE);
            drone.setModel(droneDto.getModel());
            drone.setBatteryCapacity(droneDto.getBatteryCapacity());
            drone.setAvailableCapacity(droneDto.getDrone_weight());
            drone.setBatteryLevel(droneDto.getBatteryLevel());
            droneRepository.save(drone);
        }
        if (droneDto.getDrone_weight() > 500) {
            throw new CustomException("Weight cannot exceed 500");
        }
        if(droneDto.getSerialNo().length()>100){
            throw new CustomException("Serial number cannot exceed 100 characters");
        }
        return null;
    }

    public Boolean droneBatteryLevelIsAtLeast25Percent(String serialNo) {

        Drone droneBySerialNo = droneRepository.findDroneBySerialNo(serialNo);
        Integer batteryLevel = droneBySerialNo.getBatteryLevel();

        if (batteryLevel >= 25) {
            return true;
        }
        return false;
    }

    public void updateBatteryLevel(BatteryDto batteryDto, String serialNo){
        Drone drone= droneRepository.findDroneBySerialNo(serialNo);
        drone.setBatteryLevel(batteryDto.getBatteryLevel());
        droneRepository.save(drone);
    }

    public boolean droneStateCanBeSetToLoading(Drone drone) {
        Boolean droneBatteryIsAtLeast25percent = droneBatteryLevelIsAtLeast25Percent(drone.getSerialNo());
        if (droneBatteryIsAtLeast25percent) {
            drone.setDroneState(LOADING);
        }
        if(drone.getDroneState()==LOADING){
            return true;
        }

        return false;
    }

    public void loadDrone(String serialNo, Integer medicationId) {
        Drone drone = droneRepository.findDroneBySerialNo(serialNo);
        boolean stateCanBeChanged = droneStateCanBeSetToLoading(drone);

        if (stateCanBeChanged) {
            Medication medication = medicationRepository.findMedicationById(medicationId);

            if (drone.getWeight() > medication.getWeight() && drone.getAvailableCapacity() > medication.getWeight()) {
                drone.setAvailableCapacity(drone.getAvailableCapacity() - medication.getWeight());
                medication.setDrone(drone);
                drone.setDroneState(LOADED);
                medicationRepository.save(medication);
            }
        }
        else {
            throw new CustomException("Cannot load drone, battery level less than 25%");
        }
    }

    public ResponseEntity<?> getLoadedMedicationItems(Integer droneId){
       List<Medication> medications= medicationRepository.findMedicationsByDrone_Id(droneId);
       return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    public ResponseEntity<?> availableDronesForLoading(String state){


        State droneState1= State.valueOf(State.class,state);
        List<Drone> drones= droneRepository.findDronesByDroneState(droneState1);
        return new ResponseEntity<>(drones,HttpStatus.OK);
    }

    public ResponseEntity<?> returnBatteryLevel(String serialNo) {
        Drone drone= droneRepository.findDroneBySerialNo(serialNo);
        Integer batteryLevel= drone.getBatteryLevel();
        return new ResponseEntity<>(batteryLevel,HttpStatus.OK);
    }
}

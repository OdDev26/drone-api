package com.example.droneapi.repository;

import com.example.droneapi.model.Drone;
import com.example.droneapi.model.Medication;
import com.example.droneapi.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone,Integer> {
   Drone findDroneBySerialNo(String serialNo);
   List<Drone> findDronesByDroneState(State state);
   Drone findDroneByWeight(Integer weight);
  // Drone findDronesByDroneStateEquals(State droneState);
}

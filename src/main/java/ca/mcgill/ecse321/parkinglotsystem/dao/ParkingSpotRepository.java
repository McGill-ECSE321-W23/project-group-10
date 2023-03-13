package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;


public interface ParkingSpotRepository extends CrudRepository<ParkingSpot, Integer> {

    //find a parking spot by id
    ParkingSpot findParkingSpotById(int id);

    //find parking spots by type
    List<ParkingSpot> findParkingSpotByType(ParkingSpotType type);


}

package ca.mcgill.ecse321.parkinglotsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;


public interface ParkingSpotTypeRepository extends CrudRepository<ParkingSpotType, String> {

    //find a parking spot type by name
    ParkingSpotType findParkingSpotTypeByName(String name);
}

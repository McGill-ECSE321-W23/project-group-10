package ca.mcgill.ecse321.parkinglotsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;



public interface ParkingSpotTypeRepository extends CrudRepository<ParkingSpotType, String>{
    
    ParkingSpotType findParkingSpotTypeByName(String name);
}

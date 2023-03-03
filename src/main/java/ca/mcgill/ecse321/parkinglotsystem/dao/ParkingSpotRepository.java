package ca.mcgill.ecse321.parkinglotsystem.dao;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;



public interface ParkingSpotRepository extends CrudRepository<ParkingSpot, Integer>{

    // List<ParkingSpot> findParkingSpotByParkingSpotType(ParkingSpotType parkingSpotType); 
    ParkingSpot findParkingSpotById(int id);   
    List<ParkingSpot> findParkingSpotByType(ParkingSpotType type);

    
}

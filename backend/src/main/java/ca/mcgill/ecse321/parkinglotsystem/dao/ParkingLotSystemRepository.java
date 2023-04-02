package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.sql.Time;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingLotSystem;


public interface ParkingLotSystemRepository extends CrudRepository<ParkingLotSystem, Integer> {

    // count ParkingLotsystems with id
    int countParkingLotSystemById(int id);

    //find parking lot system by id
    ParkingLotSystem findParkingLotSystemById(int id);

    // count ParkingLotsystems with open time
    int countParkingLotSystemByOpenTime(Time openTime);
    
    //find parking lot system by open time
    List<ParkingLotSystem> findParkingLotSystemByOpenTime(Time openTime);

    // count ParkingLotsystems with close time
    int countParkingLotSystemByCloseTime(Time closeTime);
    
    //find parking lot system by close time
    List<ParkingLotSystem> findParkingLotSystemByCloseTime(Time closeTime);

}

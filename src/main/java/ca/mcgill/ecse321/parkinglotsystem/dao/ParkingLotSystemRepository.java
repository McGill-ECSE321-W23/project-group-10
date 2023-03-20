package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.sql.Time;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingLotSystem;


public interface ParkingLotSystemRepository extends CrudRepository<ParkingLotSystem, Integer> {

    // is there is a ParkingLotsystem with id
    boolean isParkingLotSystemById(int id);

    //find parking lot system by id
    ParkingLotSystem findParkingLotSystemById(int id);

    // is there is a ParkingLotsystem with open time
    boolean isParkingLotSystemByOpenTime(Time openTime);
    
    //find parking lot system by open time
    List<ParkingLotSystem> findParkingLotSystemByOpenTime(Time openTime);

    // is there is a ParkingLotsystem with close time
    boolean isParkingLotSystemByCloseTime(Time closeTime);
    
    //find parking lot system by close time
    List<ParkingLotSystem> findParkingLotSystemByCloseTime(Time closeTime);

}

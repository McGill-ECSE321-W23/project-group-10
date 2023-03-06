package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.sql.Time;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingLotSystem;


public interface ParkingLotSystemRepository extends CrudRepository<ParkingLotSystem, Integer> {

    ParkingLotSystem findParkingLotSystemById(int id);

    List<ParkingLotSystem> findParkingLotSystemByOpenTime(Time openTime);

    List<ParkingLotSystem> findParkingLotSystemByCloseTime(Time closeTime);

}

package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.access.InvalidInvocationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;

@Service
public class ParkingSpotService {
    
    @Autowired
    ParkingSpotRepository parkingSpotRepository;
    @Autowired
    ParkingSpotTypeRepository parkingSpotTypeRepository;

    /**
     * method to a create parking spot
     * @param id
     * @param parkingSpotType
     * @return
     */
    public ParkingSpot createParkingSpot(int id, ParkingSpotType parkingSpotType){

        // Input validation
        String error = "";

        // we need to start id at 1
        if (id < 1) {
            error = error + "Id must be greater than zero! ";
        }
        if (parkingSpotType == null){
            error = error + "Parking spot type cannot be null!";
        }

        if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

        // // check if id is unique
        // if (parkingSpotRepository.findById(id) != null) {
        //     error += "A parking spot id must be unique! ";
        // }

        // check if parking spot type exist
        if (parkingSpotTypeRepository.findParkingSpotTypeByName(parkingSpotType.getName()) == null) {
            error += "There is no such parking spot type! ";
        }

        if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

        //check id?
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setId(id);
        parkingSpot.setType(parkingSpotType);
        parkingSpotRepository.save(parkingSpot);

        return parkingSpot;
    }

    public ParkingSpot getParkingSpotById(int id) {
        // TODO: implement this method
        return null;
    }

    public List<ParkingSpot> getAllParkingSpots() {

        Iterable<ParkingSpot> pIterable = parkingSpotRepository.findAll();
        return HelperMethods.toList(pIterable);

    }
    public ParkingSpot getParkingSpot(int parkingSpotId) {
        // TODO: Implement this
        return null;
    }
}

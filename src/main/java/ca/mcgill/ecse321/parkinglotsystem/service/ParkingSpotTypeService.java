package ca.mcgill.ecse321.parkinglotsystem.service;


import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;


/**
 * author Shaun Soobagrah
 */
@Service
public class ParkingSpotTypeService {
    
    @Autowired
    ParkingSpotTypeRepository parkingSpotTypeRepository;

    @Autowired
    ParkingSpotRepository parkingSpotRepository;

    /**
     * Controller method to get all parking spots.
     * @return a list of parking spots DTO
     */
    @Transactional
    public ParkingSpotType createParkingSpotType(String name, double fee){
        // input validation
        if (name.trim().length() == 0) {
            throw new CustomException("Parking spot type name cannot be empty! ", HttpStatus.BAD_REQUEST);
        }
        if (fee < 0.0) {
            throw new CustomException("Parking spot type fee cannot be less than zero! ", HttpStatus.BAD_REQUEST);
        }

        ParkingSpotType parkingSpotType = new ParkingSpotType();
        parkingSpotType.setFee(fee);
        parkingSpotType.setName(name);
        parkingSpotTypeRepository.save(parkingSpotType);    
        return parkingSpotType;
    } 

    // method to get all parking spot type instances
    @Transactional
    public List<ParkingSpotType> getAllParkingSpotTypes() {
        Iterable <ParkingSpotType> parkingSTs = parkingSpotTypeRepository.findAll();
        return HelperMethods.toList(parkingSTs) ;
    }

    // method to delete a parking spot type 
    @Transactional
    public ParkingSpotType deleteParkingSpotType(String name){
        
        if (name.trim().length() == 0) {
            throw new CustomException("a name must be mention to delete parking spot type! ", HttpStatus.BAD_REQUEST);
        } 
        ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(name);
        if (parkingSpotType == null) {
            throw new CustomException("no such parking spot type exist! ", HttpStatus.BAD_REQUEST);
        }

        //we must delete the parking spot as a parking spot must have a parking spot type
        List<ParkingSpot> parkingSpots = new ArrayList<ParkingSpot>();
        parkingSpots = parkingSpotRepository.findParkingSpotByType(parkingSpotType);
        if (parkingSpots.get(0).getType().getName().equals(name)) {
            throw new CustomException("This type is assigned to a parking spot ", HttpStatus.BAD_REQUEST);
        }

        parkingSpotTypeRepository.delete(parkingSpotType);
        return parkingSpotType;
    }

    // method to update the fee of a parking spot type
    @Transactional
    public ParkingSpotType updateParkingSpotTypeFee(String name, double fee){
        ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(name);
        // Input validation
        if (name.trim().length() == 0) {
            throw new CustomException("Parking spot type name cannot be empty! ", HttpStatus.BAD_REQUEST);
        }
        if (fee < 0.0) {
            throw new CustomException("Parking spot type fee cannot be less than zero! ", HttpStatus.BAD_REQUEST);
        }
        if (parkingSpotType == null) {
            throw new CustomException("Could not find a parking spot type by this name to update! ", HttpStatus.BAD_REQUEST);
        }

        parkingSpotType.setFee(fee);
        parkingSpotTypeRepository.save(parkingSpotType);
        return parkingSpotType;
        
    }
    // method to get a parking spot type by name
    @Transactional
    public ParkingSpotType getParkingSpotTypeByName(String name) {
        // Input validation
        if (name.trim().length() == 0) {
            throw new CustomException("Parking spot type name cannot be empty! ", HttpStatus.BAD_REQUEST);
        }

        ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(name);
        return parkingSpotType;
    }

}

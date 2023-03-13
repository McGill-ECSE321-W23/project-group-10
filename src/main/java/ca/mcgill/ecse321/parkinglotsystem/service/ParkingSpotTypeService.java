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
import ca.mcgill.ecse321.parkinglotsystem.dto.ParkingSpotTypeDto;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;


@Service
public class ParkingSpotTypeService {
    
    @Autowired
    ParkingSpotTypeRepository parkingSpotTypeRepository;

    @Autowired
    ParkingSpotRepository parkingSpotRepository;

    // method to create parking spot type objects
    @Transactional
    public ParkingSpotType createParkingSpotType(String name, double fee){

        // Input validation
        String error = "";
        if (name == null || name.trim().length() == 0) {
            error = error + "Parking spot type name cannot be empty! ";
        }

        if (fee < 0.0 || fee == 0.0) {
            error = error + "Parking spot type fee cannot be less than or equal to zero! ";
        }


        if (error.length() > 0) {
			throw new IllegalArgumentException(error);
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
        return toList(parkingSTs) ;
    }

    // method to delete a parking spot type 
    @Transactional
    public ParkingSpotType deleteParkingSpotType(String name){

        // Input validation
        String error = "";
        if (name == null || name.trim().length() == 0) {
            error = error + "a name must be mention to delete parking spot type! ";
        }

        

        ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(name);

        if (parkingSpotType == null) {
            error = error + "no such parking spot type exist! ";
        }

        if (error.length() >0) {
			throw new IllegalArgumentException(error);
		}

        //we must delete the parking spot as a parking spot must have a parking spot type
        if (parkingSpotRepository.findAll() != null) {
            Iterable<ParkingSpot> parkingSpots = parkingSpotRepository.findAll();
            for (ParkingSpot p: parkingSpots) {
                if (p.getType().getName() == name) {
                    parkingSpotRepository.delete(p);
                }
            }

        }
        parkingSpotTypeRepository.delete(parkingSpotType);

        return parkingSpotType;
    }

    // method to update the fee of a parking spot type
    @Transactional
    public ParkingSpotType updateParkingSpotTypeFee(String name, double fee){

        // Input validation
        String error = "";
        if (name == null || name.trim().length() == 0) {
            error = error + "Parking spot type name cannot be empty! ";
        }

        if (fee < 0.0) {
            error = error + "Parking spot type fee cannot be less than zero! ";
        }

        ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(name);

        if (parkingSpotType == null) {
            error = error + "Could not find a parking spot type by this name to update! ";
        }
        if (error.length()>0) {
			throw new IllegalArgumentException(error);
		}

        parkingSpotType.setFee(fee);
        parkingSpotTypeRepository.save(parkingSpotType);
        return parkingSpotType;
        
    }
    // method to get a parking spot type by name
    @Transactional
    public ParkingSpotType getParkingSpotTypeByName(String name) {
        // Input validation
        String error = "";
        if (name == null || name.trim().length() == 0) {
            error = error + "Parking spot type name cannot be empty! ";
        }

        if (error.length()>0) {
			throw new IllegalArgumentException(error);
		}

        ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(name);

        return parkingSpotType;

    }

    // Helper method that converts iterable to list
    // @param iterable item
    // @return list
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}

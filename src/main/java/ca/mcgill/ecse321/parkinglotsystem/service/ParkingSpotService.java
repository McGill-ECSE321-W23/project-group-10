package ca.mcgill.ecse321.parkinglotsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

/*
 * author Shaun Soobagrah
 */
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
    @Transactional
    public ParkingSpot createParkingSpot(int id, ParkingSpotType parkingSpotType){

        // Input validation
        String error = "";

        // we need to start id at 1
        error += checkId(id);

        error += checkType(parkingSpotType);

        if (error.length() > 0) {
			throw new CustomException(error, HttpStatus.BAD_REQUEST);   
		}

        // check if parking spot type exist
        if (parkingSpotTypeRepository.findParkingSpotTypeByName(parkingSpotType.getName()) == null) {
            error += "There is no such parking spot type! ";
        }

        if (error.length() > 0) {
	        throw new CustomException(error, HttpStatus.NOT_FOUND);
		}
 
        ParkingSpot parkingSpot = new ParkingSpot();   
        parkingSpot.setId(id);
        parkingSpot.setType(parkingSpotType);
        parkingSpotRepository.save(parkingSpot);
        
        

        return parkingSpot;
    }


    /**
     * method to get all parking spots
     * @return list of parking spot
     */
    @Transactional
    public List<ParkingSpot> getAllParkingSpots() {

        Iterable<ParkingSpot> pIterable = parkingSpotRepository.findAll();
        return HelperMethods.toList(pIterable);

    }

    /**
     * method to get parking spot by id
     * @param id
     * @return parking spot 
     */
    @Transactional
    public ParkingSpot getParkingSpotById(int id) {

        // input validation
        ParkingSpot parkingSpot = parkingSpotRepository.findParkingSpotById(id);

        // if no parking spot found, throw execption
        if (parkingSpot == null) {
            throw new CustomException("No parking spot with that id was found! ", HttpStatus.NOT_FOUND);
        }
        return parkingSpot;
    }

    /**
     * method to delete a parking spot using its id
     * @param id
     * @return parking spot
     */
    @Transactional
    public ParkingSpot deleteParkingSpotBy(int id) {

        // if no parking spot found, throw execption
        ParkingSpot spot = parkingSpotRepository.findParkingSpotById(id);
        if (spot == null) {
			throw new CustomException("No parking spot with that id was found! ", HttpStatus.NOT_FOUND);
		}
        parkingSpotRepository.delete(spot);
        
        return spot;
    }

    /**
     * method to get parking spots by type
     * @param parkingSpotType
     * @return list of parking spot
     */
    @Transactional
    public List<ParkingSpot> getParkingSpotByType(ParkingSpotType parkingSpotType) {

        List<ParkingSpot> pList = parkingSpotRepository.findParkingSpotByType(parkingSpotType);
        if (pList.size() == 0) throw new CustomException("List is empty! ", HttpStatus.NOT_FOUND);
        return pList;
    }

    /**
     * method to update a parking spot type
     * @param id
     * @param parkingSpotType
     * @return ParkingSpot
     */
    @Transactional
    public ParkingSpot updateParkingSpot(int id, ParkingSpotType parkingSpotType) {
        String error = "";
        //check if spot exists
        ParkingSpot spot = parkingSpotRepository.findParkingSpotById(id);
        if (parkingSpotRepository.findParkingSpotById(id) == null) {
			throw new CustomException("no parking spot with that id exists! ", HttpStatus.NOT_FOUND);
		}
   
        spot.setType(parkingSpotType);
        parkingSpotRepository.save(spot);
        return spot;
    }


    // Helper method //

    /**
     * method to check if id is valid
     * @param id
     * @return error
     */
    private String checkId(int id) {
        String error = "";
        if (id < 1) {
            error = error + "Id must be greater than zero! ";
        }
        return error;
    }

    /**
     * check if parking type exist
     * @param parkingSpotType
     * @return error
     */
    private String checkType(ParkingSpotType parkingSpotType) {
        String error = "";
        if (parkingSpotType == null){
            error = error + "Parking spot type cannot be null!";
        }
        return error;
    }
}

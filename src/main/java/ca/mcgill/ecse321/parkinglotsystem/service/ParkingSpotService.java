package ca.mcgill.ecse321.parkinglotsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

/*
 * @author Shaun Soobagrah
 */
@Service
public class ParkingSpotService {
    
    @Autowired
    ParkingSpotRepository parkingSpotRepository;
    @Autowired
    ParkingSpotTypeRepository parkingSpotTypeRepository;
    @Autowired
    ReservationRepository reservationRepository;

    /**
     * Method to create a parking spot.
     * @author Shaun Soobagrah
     * @param id the id of the parking spot
     * @param parkingSpotTypeName the parking spot type of the parking spot
     * @return A ParkingSpot
     * @throws CustomException if to create the parking spot fail
     */
    @Transactional
    public ParkingSpot createParkingSpot(int id, String parkingSpotTypeName){

        // Input validation
        String error = "";

        // we need to start id at 1
        error += checkId(id);
        // error += checkType(parkingSpotType);
        if (error.length() > 0) {
			throw new CustomException(error, HttpStatus.BAD_REQUEST);   
		}

        ParkingSpotType parkingSpotType  = parkingSpotTypeRepository.findParkingSpotTypeByName(parkingSpotTypeName);
        // check if parking spot type exist
        if (parkingSpotType == null) {
	        throw new CustomException("No parking spot type with name " + parkingSpotTypeName + " exists! ", HttpStatus.NOT_FOUND);
		}

        ParkingSpot parkingSpot = new ParkingSpot();   
        parkingSpot.setId(id);
        parkingSpot.setType(parkingSpotType);
        parkingSpotRepository.save(parkingSpot); 
        return parkingSpot;
    }


    /**
     * Method to get all parking spots.
     * @author Shaun Soobagrah
     * @return A List of ParkingSpot
     */
    @Transactional
    public List<ParkingSpot> getAllParkingSpots() {

        Iterable<ParkingSpot> pIterable = parkingSpotRepository.findAll();
        return HelperMethods.toList(pIterable);

    }

    /**
     * Method to get the parking spot by id.
     * @author Shaun Soobagrah
     * @param id the id of the parking spot
     * @return A ParkingSpot
     * @throws CustomException if to get the parking spot fail
     */
    @Transactional
    public ParkingSpot getParkingSpotById(int id) {

        // input validation
        ParkingSpot parkingSpot = parkingSpotRepository.findParkingSpotById(id);

        // if no parking spot found, throw exception
        if (parkingSpot == null) {
            throw new CustomException("No parking spot with that id was found! ", HttpStatus.NOT_FOUND);
        }
        return parkingSpot;
    }

    /**
     * Method to delete a parking spot by id.
     * @author Shaun Soobagrah
     * @param id the id of the parking spot
     * @return the deleted ParkingSpot
     * @throws CustomException if to delete the parking spot fail
     */
    @Transactional
    public ParkingSpot deleteParkingSpotById(int id) {
        // if no parking spot found, throw exception
        ParkingSpot spot = parkingSpotRepository.findParkingSpotById(id);
        if (spot == null) {
			throw new CustomException("No parking spot with that id was found! ", HttpStatus.NOT_FOUND);
		}
        List<Reservation> reservations = reservationRepository.findReservationsByParkingSpot(spot);
        if (reservations.get(0).getParkingSpot().getId() == id ) {
            throw new CustomException("Cannot delete as parking spot has 1 or more reservation! ", HttpStatus.BAD_REQUEST);
        }     
        parkingSpotRepository.delete(spot);   
        return spot;
    }

    /**
     * Method to get parking spots by type.
     * @author Shaun Soobagrah
     * @param parkingSpotTypeName the parking spot type of the parking spot
     * @return A List of ParkingSpot
     * @throws CustomException if to get parking spot fail
     */
    @Transactional
    public List<ParkingSpot> getParkingSpotByType(String parkingSpotTypeName) {
        ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(parkingSpotTypeName);
        if (parkingSpotType == null) {
            throw new CustomException("Cannot find parking spot type by that name! ", HttpStatus.NOT_FOUND);
        }
        List<ParkingSpot> pList = parkingSpotRepository.findParkingSpotByType(parkingSpotType);
        return pList;
    }

    /**
     * Method to update a parking spot.
     * @author Shaun Soobagrah
     * @param id the id of the parking spot
     * @param parkingSpotTypeName the parking spot type of the parking spot
     * @return the updated ParkingSpot
     * @throws CustomException if to update the parking spot fail
     */

    @Transactional
    public ParkingSpot updateParkingSpot(int id, String parkingSpotTypeName) {
        //check if spot exists
        ParkingSpot spot = parkingSpotRepository.findParkingSpotById(id);
        if (parkingSpotRepository.findParkingSpotById(id) == null) {
			throw new CustomException("no parking spot with that id exists! ", HttpStatus.NOT_FOUND);
		}
        ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(parkingSpotTypeName);
        if (parkingSpotType == null) {
            throw new CustomException("Cannot find parking spot type by that name! ", HttpStatus.NOT_FOUND);
        }
        spot.setType(parkingSpotType);
        parkingSpotRepository.save(spot);
        return spot;
    }


    // Helper method //

    /**
     * Method to check if id is valid.
     * @author Shaun Soobagrah
     * @param id the input id
     * @return error message
     */
    private String checkId(int id) {
        String error = "";
        if (id < 1) {
            error = error + "Id must be greater than zero! ";
        }
        return error;
    }

}
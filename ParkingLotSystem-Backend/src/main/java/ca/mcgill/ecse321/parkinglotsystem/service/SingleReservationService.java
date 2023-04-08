package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.SingleReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;


@Service
public class SingleReservationService {
    @Autowired
    private SingleReservationRepository singleReservationRepository;

    @Autowired
    private ParkingSpotService parkingSpotService;
    /**
     * Method to create a single reservation.
     * @author Mike Zhang
     * @param licenseNumber the license number of the single reservation
     * @param parkingTime the parking time of the single reservation
     * @param parkingSpotId the parking spot id of the single reservation
     * @return A SingleReservation
     * @throws CustomException if to create the single reservation fail
     */
    @Transactional
    public SingleReservation createSingleReservation(String licenseNumber,
            int parkingTime, int parkingSpotId) {
        if ((parkingSpotId >= 2000 && parkingSpotId < 4000)){
            throw new CustomException(
                "The parking spot is only available for monthly customers.", HttpStatus.BAD_REQUEST);
        }
        if (licenseNumber == null || licenseNumber.length() == 0) {
            throw new CustomException("license number cannot be empty", HttpStatus.BAD_GATEWAY);

        } else if (!licenseNumber.matches("^[a-zA-Z0-9]*$") || licenseNumber.length() > 7) {
            throw new CustomException("Incorrect format for licenseNumber", HttpStatus.BAD_REQUEST);
        } else if (parkingTime < 0) {
            throw new CustomException("ParkingTime cannot be negative", HttpStatus.BAD_REQUEST);
        } 
        ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(parkingSpotId);
        for(SingleReservation singleReservation : singleReservationRepository.findSingleReservationsByParkingSpot(parkingSpot)) {
            if (isActive(singleReservation)) {
                throw new CustomException(
                    "The parking spot is currently reserved by another customer.", HttpStatus.BAD_REQUEST);
            }
        }
        for(SingleReservation singleReservation : singleReservationRepository.findSingleReservationsByLicenseNumber(licenseNumber)) {
            if (isActive(singleReservation)) {
                throw new CustomException(
                    "The license number already has an active subscription.", HttpStatus.BAD_REQUEST);
            }
        }
            Date date = new Date((new java.util.Date().getTime()));
            SingleReservation singleReservation = new SingleReservation();
            singleReservation.setDate(date);
            singleReservation.setLicenseNumber(licenseNumber);
            singleReservation.setParkingTime(parkingTime);
            singleReservation.setParkingSpot(parkingSpotService.getParkingSpotById(parkingSpotId));
            singleReservationRepository.save(singleReservation);
            return singleReservation;
        
    }

    /**
     * Method to get a single reservation by id.
     * @author Mike Zhang
     * @param reservationId the reservation id of the single reservation
     * @return A SingleReservation
     * @throws CustomException if to get the single reservation fail
     */
    @Transactional
    public SingleReservation getSingleReservationById(int reservationId) {
        SingleReservation singleReservation = singleReservationRepository.findSingleReservationById(reservationId);
        if (singleReservation == null) {
            throw new CustomException("SingleReservation not found", HttpStatus.BAD_REQUEST);
        }
        return singleReservation;
   }

    /**
     * Method to get single reservations by date.
     * @author Mike Zhang
     * @param date the reservation date of the single reservation
     * @return A List of SingleReservation
     */
    @Transactional
    public List<SingleReservation> getSingleReservationsByDate(Date date) {
        List<SingleReservation> singleReservations = singleReservationRepository.findSingleReservationsByDate(date);
        return singleReservations;
    }

    /**
     * Method to get single reservations by license number.
     * @author Mike Zhang
     * @param licenseNumber the license number of the single reservation
     * @return A List of SingleReservation
     */
    @Transactional
    public List<SingleReservation> getSingleReservationsByLicenseNumber(String licenseNumber) {
        List<SingleReservation> singleReservations = singleReservationRepository
                .findSingleReservationsByLicenseNumber(licenseNumber);
        return singleReservations;
    }

    /**
     * Method to get single reservations by parking spot id.
     * @author Mike Zhang
     * @param parkingSpotId the parking spot id of the single reservation
     * @return A List of SingleReservation sorted by date (ascending)
     */
    @Transactional
    public List<SingleReservation> getSingleReservationsByParkingSpot(int parkingSpotId) {
        List<SingleReservation> singleReservations = singleReservationRepository
                .findSingleReservationsByParkingSpot(parkingSpotService.getParkingSpotById(parkingSpotId));
        Collections.sort(singleReservations, Comparator.comparing(SingleReservation::getDate));
        return singleReservations;
    }

    /**
     * Method to get all single reservations.
     * @author Mike Zhang
     * @return A List of SingleReservation
     */
    @Transactional
    public List<SingleReservation> getAllSingleReservations() {
        return toList(singleReservationRepository.findAll());
    }

    /**
     * Method to update a single reservation.
     * @author Mike Zhang
     * @param licenseNumber the license number of the single reservation
     * @param parkingTime the parking time of the single reservation
     * @return the updated SingleReservation
     * @throws CustomException if to update the single reservation fail
     */
    @Transactional
    public SingleReservation updateSingleReservation (String licenseNumber, int parkingTime) {
        if(licenseNumber == null || licenseNumber.length() == 0){
            throw new CustomException("licenseNumber cannot be empty", HttpStatus.BAD_REQUEST);
        }
        else if(!licenseNumber.matches("^[a-zA-Z0-9]*$") || licenseNumber.length() > 7){
            throw new CustomException("Incorrect licenseNumber format", HttpStatus.BAD_REQUEST);
        }
        else if (parkingTime < 0) {
            throw new CustomException("ParkingTime cannot be negative", HttpStatus.BAD_REQUEST);
        }
        SingleReservation singleReservation = getActiveByLicenseNumber(licenseNumber);
        if (singleReservation == null) {
            throw new CustomException("There is no active subscription with this License number", HttpStatus.NOT_FOUND);
        }
        singleReservation.setParkingTime(singleReservation.getParkingTime() + parkingTime);
        singleReservationRepository.save(singleReservation);
        return singleReservation;
    }

    /**
     * Method to delete a single reservation.
     * @author Mike Zhang
     * @param reservationId the reservation id of the single reservation
     * @return the deleted SingleReservation
     * @throws CustomException if to delete the single reservation fail
     */
    @Transactional
    public SingleReservation deleteSingleReservation(int reservationId) {
        if (reservationId < 0) {
            throw new CustomException("ReservationId cannot be negative.", HttpStatus.BAD_REQUEST);
        } 

        SingleReservation singleReservation = singleReservationRepository.findSingleReservationById(reservationId);
        if (singleReservation == null) {
            throw new CustomException("Reservation does not exist.", HttpStatus.NOT_FOUND);
        }

        singleReservationRepository.delete(singleReservation);
        return singleReservation;

    }

    /**
     * Delete all the reservations
     * @author Mike
     * @return the list of all the single reservation deleted
     */
    @Transactional
    public List<SingleReservation> deleteAllSingleReservations() {
        Iterable<SingleReservation> singleReservations = singleReservationRepository.findAll();
        singleReservationRepository.deleteAll();
        return toList(singleReservations);
    }


    /**
     * get current active reservation from licenseNumber
     * @author Mike
     * @return the active singleReservation found with licenseNumber
     */
    @Transactional
    public SingleReservation getActiveByLicenseNumber(String licenseNumber) {

        List<SingleReservation> singleReservations = getSingleReservationsByLicenseNumber(licenseNumber);
        if(singleReservations == null || singleReservations.size() <= 0 ) {
            throw new CustomException("There is no active subscription with this License number", HttpStatus.NOT_FOUND);
        }
        SingleReservation latestSingleReservation = singleReservations.get(singleReservations.size() - 1);
        if (!isActive(latestSingleReservation)) {
            throw new CustomException("There is no active subscription with this License number", HttpStatus.NOT_FOUND);
        }

        return latestSingleReservation;
    }

    /**
     * get current active reservation from parkingSpot
     * @param parkingpSpotId
     * @return the active singleRservation found with parkingSpot
     */
    @Transactional
    public SingleReservation getActiveByParkingSpot(int parkingpSpotId) {
        List<SingleReservation> singleReservations = getSingleReservationsByParkingSpot(parkingpSpotId);
        if(singleReservations == null || singleReservations.size() <= 0 ) {
            throw new CustomException("There is no active subscription with this parking spot", HttpStatus.NOT_FOUND);
        }
        SingleReservation latestSingleReservation = singleReservations.get(singleReservations.size() - 1);
        if (!isActive(latestSingleReservation)) {
            throw new CustomException("There is no active subscription with this parking spot", HttpStatus.NOT_FOUND);
        }

        return latestSingleReservation;
    }

    /**
     * Service method to check is there is an active subscription with the given parking spot.
     * @param parkingSpotId the ID of the parking spot
     * @return true if there is an active subscription with the given parking spot.
     */
    @Transactional
    public boolean hasActiveByParkingSpot(int parkingSpotId) {

        List<SingleReservation> subs = getSingleReservationsByParkingSpot(parkingSpotId);
        if(subs.size() <= 0) {
            return false;
        }
        SingleReservation latestSub = subs.get(subs.size() - 1);
        return isActive(latestSub);
    }
    
    /**
     * calculate the fee of a singleReservation
     * @author Mike
     * @param startTime - the start time of the singleReservation
     * @param singleReservationId - the id of the singleReservation
     * @return fee
     */
    @Transactional
    public Double calculateFee(Time startTime, int singleReservationId) {
        Time nowTime = Time.valueOf(LocalTime.now());
        long parkingTime;
        parkingTime = nowTime.getTime() - startTime.getTime();
        SingleReservation sR = singleReservationRepository.findSingleReservationById(singleReservationId);
        if (sR == null){
            throw new CustomException("singleReservation not found", HttpStatus.NOT_FOUND);
        }
        Double fee = parkingTime * sR.getParkingSpot().getType().getFee();
        return fee;
    }

    /**
     * check if the singleReservation is active
     * @author Mike
     * @param single - the singleReservation to be checked
     * @return if the singleReservation is active
     */
    private boolean isActive(SingleReservation single) {
        Date date = Date.valueOf(LocalDate.now());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(single.getDate());
        calendar.add(Calendar.MINUTE, single.getParkingTime());
        Date lastDate = new Date(calendar.getTimeInMillis());        
        return lastDate.after(date);
    }

    /**
     * helper method that converts iterable to list
     * @author Mike
     * @param <T>
     * @param iterable
     * @return a list
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}

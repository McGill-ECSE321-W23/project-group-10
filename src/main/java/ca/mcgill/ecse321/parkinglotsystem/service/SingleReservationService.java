package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.SingleReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;


@Service
public class SingleReservationService extends ReservationService {
    @Autowired
    private SingleReservationRepository singleReservationRepository;

    @Autowired
    private ParkingSpotService parkingSpotService;
    /**
     * Create a SingleReservation
     * 
     * @author Mike Zhang
     * @params reservationId
     * @params date
     *         return a reservation created
     */
    @Transactional
    public SingleReservation createSingleReservation(String licenseNumber,
            int parkingTime, int parkingSpotId) {
                // TODO: remove id validation and parameters
        if ((parkingSpotId >= 2000 && parkingSpotId < 3000)){
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
     * find singlereservation by id
     * 
     * @author Mike Zhang
     * @params reservationId
     *         return the Singlereservations found
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
     * Find singlereservations by date
     * 
     * @author Mike Zhang
     * @param date
     * @return a list of reservations
     */
    @Transactional
    public List<SingleReservation> getSingleReservationsByDate(Date date) {
        List<SingleReservation> singleReservations = singleReservationRepository.findSingleReservationsByDate(date);
        return singleReservations;
    }

    @Transactional
    public List<SingleReservation> getSingleReservationsByLicenseNumber(String licenseNumber) {
        List<SingleReservation> singleReservations = singleReservationRepository
                .findSingleReservationsByLicenseNumber(licenseNumber);
        return singleReservations;
    }

    @Transactional
    public List<SingleReservation> getSingleReservationsByParkingSpot(int parkingSpotId) {
        List<SingleReservation> singleReservations = singleReservationRepository
                .findSingleReservationsByParkingSpot(parkingSpotService.getParkingSpotById(parkingSpotId));
        return singleReservations;
    }
    /**
     * Find all reservations
     * 
     * @author Mike
     * @return List of all accounts
     */
    @Transactional
    public List<SingleReservation> getAllSingleReservations() {
        return toList(singleReservationRepository.findAll());
    }

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
            throw new CustomException("Single reservation not found", HttpStatus.BAD_REQUEST);
        }
        singleReservation.setParkingTime(singleReservation.getParkingTime() + parkingTime);
        singleReservationRepository.save(singleReservation);
        return singleReservation;
    }

    /**
     * Delete a reservation
     * 
     * @author Mike
     * @param reservationId
     * @return reservation deleted
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
     * 
     * @return
     */
    @Transactional
    public List<SingleReservation> deleteAllSingleReservations() {
        Iterable<SingleReservation> singleReservations = singleReservationRepository.findAll();
        singleReservationRepository.deleteAll();
        return toList(singleReservations);
    }

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

    @Transactional
    public SingleReservation getActiveByParkingSpot(int parkingSpotId) {

        List<SingleReservation> singleReservations = getSingleReservationsByParkingSpot(parkingSpotId);
        if(singleReservations.size() <= 0) {
            throw new CustomException("There is no active subscription", HttpStatus.NOT_FOUND);
        }
        SingleReservation latestSingleReservation = singleReservations.get(singleReservations.size() - 1);
        if (!isActive(latestSingleReservation)) {
            throw new CustomException("There is no active subscription", HttpStatus.NOT_FOUND);
        }

        return latestSingleReservation;
    }
    /**
     * @author Mike
     * @param time
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

    private boolean isActive(SingleReservation single) {

        Date date = new Date((new java.util.Date()).getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(Calendar.MINUTE, single.getParkingTime());
        Date lastDate = new Date(calendar.getTimeInMillis());        
        return lastDate.after(date);
    }

    /**
     * helper method that converts iterable to list
     * 
     * @param <T>
     * @param iterable
     * @return
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}

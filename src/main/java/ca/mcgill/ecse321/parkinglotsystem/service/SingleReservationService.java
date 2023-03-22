package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.SingleReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;

@Service
public class SingleReservationService extends ReservationService {

    @Autowired
    private SingleReservationRepository singleReservationRepository;

    /**
     * Create a SingleReservation
     * 
     * @author Mike Zhang
     * @params reservationId
     * @params date
     *         return a reservation created
     */
    @Transactional
    public SingleReservation createSingleReservation(int reservationId, Date date, String licenseNumber,
            int parkingTime, ParkingSpot parkingSpot) {
        if (reservationId < 0) {
            throw new IllegalArgumentException("ReservationId cannot be negative.");
        } else if (reservationRepository.findReservationById(reservationId) != null) {
            throw new IllegalArgumentException("ReservationId is in use.");
        } else if (date == null) {
            throw new IllegalArgumentException("date cannot be empty.");
        } else if (licenseNumber == null || licenseNumber.length() == 0) {
            throw new IllegalArgumentException("license number cannot be empty");

        } else if (!licenseNumber.matches("^[a-zA-Z0-9]*$") || licenseNumber.length() > 7) {
            throw new IllegalArgumentException("Incorrect format for licenseNumber");
        } else if (parkingTime < 0) {
            throw new IllegalArgumentException("ParkingTime cannot be negative");
        } else {
            SingleReservation singleReservation = new SingleReservation();
            singleReservation.setId(reservationId);
            singleReservation.setDate(date);
            singleReservation.setLicenseNumber(licenseNumber);
            singleReservation.setParkingTime(parkingTime);
            singleReservation.setParkingSpot(parkingSpot);
            singleReservationRepository.save(singleReservation);
            return singleReservation;
        }
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
            throw new IllegalArgumentException("SingleReservation not found");
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
    public List<SingleReservation> getSingleReservationsBydate(Date date) {
        List<SingleReservation> singleReservations = singleReservationRepository.findSingleReservationByDate(date);
        return singleReservations;
    }

    @Transactional
    public List<SingleReservation> getSingleReservationsByLicenseNumber(String licenseNumber) {
        List<SingleReservation> singleReservations = singleReservationRepository
                .findSingleReservationByLicenseNumber(licenseNumber);
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
    public SingleReservation updateSingleReservation (int id, Date date,
            String licenseNumber, int parkingTime, ParkingSpot spot) {
                SingleReservation singleReservation = singleReservationRepository.findSingleReservationById(id);
                if (singleReservation == null) {
                    throw new IllegalArgumentException("Single reservation not found");
                }
                singleReservation.setDate(date);
                singleReservation.setLicenseNumber(licenseNumber);
                singleReservation.setParkingTime(parkingTime);
                singleReservation.setParkingSpot(spot);
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
            throw new IllegalArgumentException("ReservationId cannot be negative.");
        } else if (reservationRepository.findReservationById(reservationId) == null) {
            throw new IllegalArgumentException("reservationId does not exist.");
        }

        SingleReservation singleReservation = singleReservationRepository.findSingleReservationById(reservationId);
        if (singleReservation == null) {
            throw new IllegalArgumentException("Reservation does not exist.");
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
            throw new IllegalArgumentException("singleReservation not found");
        }
        Double fee = parkingTime * sR.getParkingSpot().getType().getFee();
        return fee;
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

package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

@Service
public class ReservationService {

    @Autowired
    protected ReservationRepository reservationRepository;
    @Autowired
    protected ParkingSpotService parkingSpotService;
    @Autowired
    protected ParkingSpotTypeService parkingSpotTypeService;
    @Autowired
    protected PaymentReservationService paymentReservationService;

    /**
     * Method to create a reservation.
     * @author Mike Zhang
     * @param parkingSpotId the parking spot id of the reservation
     * @param date the date of the reservation
     * @return A Reservation
     * @throws CustomException if to create the reservation fail
     */
    @Transactional
    public Reservation createReservation(Date date, int parkingSpotId) {
        if(date == null){
            throw new CustomException("date cannot be empty.", HttpStatus.BAD_REQUEST);
        }
        else {
            Reservation reservation = (Reservation) new SingleReservation();
            reservation.setDate(date);
            reservation.setParkingSpot(parkingSpotService.getParkingSpotById(parkingSpotId));
            reservationRepository.save(reservation);
            return reservation;
        }
        
    }

    /**
     * Method to get a reservation by id.
     * @author Mike Zhang
     * @param reservationId the reservation id of the reservation
     * @return A Reservation
     * @throws CustomException if to get the reservation fail
     */
    @Transactional
    public Reservation getReservationById(int reservationId){
        Reservation reservation = reservationRepository.findReservationById(reservationId);
        if (reservation == null){
            throw new CustomException("Reservation is not found.", HttpStatus.NOT_FOUND);
        }
        
        return reservation;
    }

    /**
	 * Method to get reservations by date.
     * @author Mike Zhang
	 * @param date the date of the reservation
	 * @return A List of Reservation
     * @throws CustomException if to get the reservation fail
	 */
	@Transactional
	public List<Reservation> getReservationsByDate(Date date) {
		List<Reservation> reservations = reservationRepository.findReservationsByDate(date);
        if (reservations == null){
            throw new CustomException("Reservation is not found.", HttpStatus.NOT_FOUND);
        }
		return reservations;
	}

    /**
	 * Method to get all reservations.
     * @author Mike Zhang
	 * @return A List of Reservation
	 */
	@Transactional
	public List<Reservation> getAllReservations() {
        
		return toList(reservationRepository.findAll());
	}

    /**
	 * Method to get reservations by parking spot.
     * @author Mike Zhang
	 * @param parkingSpotId the parking spot id of the reservation
	 * @return A List of Reservation
	 */
	@Transactional
	public List<Reservation> getReservationsByParkingSpot(int parkingSpotId) {
		List<Reservation> reservations = reservationRepository.findReservationsByParkingSpot(parkingSpotService.getParkingSpotById(parkingSpotId));
		return reservations;
	}

    /**
     * Method to delete a reservation.
     * @author Mike Zhang
     * @param reservationId the reservation id of the reservation
     * @return the deleted Reservation
     * @throws CustomException if to delete reservation fail
     */

    public Reservation deleteReservation(int reservationId){
        if (reservationId < 0){
            throw new CustomException("ReservationId cannot be negative.", HttpStatus.BAD_REQUEST);
        }

        Reservation reservation = reservationRepository.findReservationById(reservationId);
        if(reservation == null){
            throw new CustomException("ReservationId does not exist.", HttpStatus.NOT_FOUND);
        }

        reservationRepository.delete(reservation);
        return reservation;

    }

    /**
	 * Method to delete all reservations.
     * @author Mike Zhang
	 * @return the deleted reservations
	 */
	@Transactional
	public List<Reservation> deleteAllReservations() {
		Iterable<Reservation> reservations = reservationRepository.findAll();
		reservationRepository.deleteAll();
		return toList(reservations);
	}


    /**
	 * Helper method that converts iterable to list.
     * @author Mike Zhang
     * @param <T> input
     * @param iterable iterable
	 * @return A List
	 */
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}



    
}

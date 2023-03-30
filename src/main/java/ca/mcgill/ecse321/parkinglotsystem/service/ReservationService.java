package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
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
     * Create a Reservation
     * @author Mike Zhang
     * @params reservationId
     * @params date
     * return a reservation created
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
     * find reservation by id
     * @author Mike Zhang
     * @params reservationId
     * return the reservation found
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
	 * Find reservations by date
	 * @author Mike Zhang
	 * @param date
	 * @return a list of reservations
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
	 * Find all reservations
	 * @author Mike
	 * @return List of all accounts
	 */
	@Transactional
	public List<Reservation> getAllReservations() {
        
		return toList(reservationRepository.findAll());
	}

    /**
	 * Find reservations by parkingSpot
	 * @author Mike Zhang
	 * @param parkingSpot
	 * @return a list of reservations
	 */
	@Transactional
	public List<Reservation> getReservationsByParkingSpot(int parkingSpotId) {
		List<Reservation> reservations = reservationRepository.findReservationsByParkingSpot(parkingSpotService.getParkingSpotById(parkingSpotId));
		return reservations;
	}

    /**
     * Delete a reservation
     * @author Mike
     * @param reservationId
     * @return reservation deleted
     */

    public Reservation deleteReservation(int reservationId){
        if (reservationId < 0){
            throw new CustomException("ReservationId cannot be negative.", HttpStatus.BAD_REQUEST);
        }

        Reservation reservation = reservationRepository.findReservationById(reservationId);
        if(reservation == null){
            throw new CustomException("ReservationId does not exist.", HttpStatus.NOT_FOUND);
        }

        // // find the payment reservation
        // List<PaymentReservation> paymentReservations = paymentReservationService.getPaymentReservationByReservation(reservationId);
        // for (PaymentReservation paymentReservation: paymentReservations) {
        //     paymentReservationService.deletePaymentReservation(paymentReservation.getId());
        // }

        reservationRepository.delete(reservation);
        return reservation;

    }

    /**
	 * Delete all the reservations
	 * @return
	 */
	@Transactional
	public List<Reservation> deleteAllReservations() {
		Iterable<Reservation> reservations = reservationRepository.findAll();
		reservationRepository.deleteAll();
		return toList(reservations);
	}


    /**
	 * helper method that converts iterable to list
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}



    
}

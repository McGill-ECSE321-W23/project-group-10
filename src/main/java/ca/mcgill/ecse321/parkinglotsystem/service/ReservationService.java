package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;

@Service
public class ReservationService {

    @Autowired
    protected ReservationRepository reservationRepository;
    @Autowired
    protected ParkingSpotRepository parkingSpotRepository;
    @Autowired
    protected ParkingSpotTypeRepository parkingSpotTypeRepository;


    /**
     * @author Mike
     * @param name
     * @param fee
     * @return a parkingspottype
     */
    @Transactional
    public ParkingSpotType createParkingSpotType(String name, Double fee){
        if (name == null || name.length() == 0){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        ParkingSpotType type = new ParkingSpotType();
        type.setName(name);
        type.setFee(fee);
        parkingSpotTypeRepository.save(type);
        return type;

    }

    public ParkingSpotType getParkingSpotTypebyName(String name){
        ParkingSpotType type = parkingSpotTypeRepository.findParkingSpotTypeByName(name);
        return type;

    }

    public List<ParkingSpotType> getAllParkingSpotTypes(){
        return toList(parkingSpotTypeRepository.findAll());

    }

    /**
     * @author Mike
     * @param parkingSpotId
     * @return a parkingspot
     */
    @Transactional
    public ParkingSpot createParkingSpot(int parkingSpotId, String typeName, Double typeFee){
        if (parkingSpotId < 0){
            throw new IllegalArgumentException("Id must be positive");
        }
        ParkingSpotType type = createParkingSpotType(typeName, typeFee);
        ParkingSpot spot = new ParkingSpot();
        spot.setId(parkingSpotId);
        spot.setType(type);
        parkingSpotRepository.save(spot);
        return spot;

    }

    public ParkingSpot getParkingSpotbyId(int id){
        ParkingSpot spot = parkingSpotRepository.findParkingSpotById(id);
        return spot;

    }

    public List<ParkingSpot> getAllParkingSpots(){
        return toList(parkingSpotRepository.findAll());

    }
    /**
     * Create a Reservation
     * @author Mike Zhang
     * @params reservationId
     * @params date
     * return a reservation created
     */
    @Transactional
    public Reservation createReservation(int reservationId, Date date) {
        if (reservationId < 0){
            throw new IllegalArgumentException("ReservationId cannot be negative.");
        }
        else if(reservationRepository.findReservationById(reservationId) != null){
            throw new IllegalArgumentException("ReservationId is in use.");
        }
        else if(date == null){
            throw new IllegalArgumentException("date cannot be empty.");
        }
        else {
            Reservation reservation = (Reservation) new SingleReservation();
            reservation.setId(reservationId); 
            reservation.setDate(date);
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
        return reservation;
    }

    /**
	 * Find reservations by date
	 * @author Mike Zhang
	 * @param date
	 * @return a list of reservations
	 */
	@Transactional
	public List<Reservation> getReservationsBydate(Date date) {
		List<Reservation> reservations = reservationRepository.findReservationsByDate(date);
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
	public List<Reservation> getReservationsByParkingSpot(ParkingSpot parkingSpot) {
		List<Reservation> reservations = reservationRepository.findReservationsByParkingSpot(parkingSpot);
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
            throw new IllegalArgumentException("ReservationId cannot be negative.");
        }
        else if(reservationRepository.findReservationById(reservationId) == null){
            throw new IllegalArgumentException("reservationId does not exist.");
        }

        Reservation reservation = reservationRepository.findReservationById(reservationId);
        if(reservation == null){
            throw new IllegalArgumentException("Reservation does not exist.");
        }

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

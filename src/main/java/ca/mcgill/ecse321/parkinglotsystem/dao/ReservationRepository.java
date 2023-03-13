package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    //find a reservation by id
    Reservation findReservationById(int id);

    //find reservations by date
    List<Reservation> findReservationByDate(Date date);

    //find reservations by parking spot
    List<Reservation> findReservationByParkingSpot(ParkingSpot parkingSpot);

}

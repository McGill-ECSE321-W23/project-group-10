package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer>{

    Reservation findReservationById(int id);
    List<Reservation> findReservationByDate(Date date);
    List<Reservation> findReservationByParkingSpot(ParkingSpot parkingSpot);
    
}

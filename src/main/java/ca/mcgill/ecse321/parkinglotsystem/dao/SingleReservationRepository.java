package ca.mcgill.ecse321.parkinglotsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;

import java.util.List;
import java.util.Date;

public interface SingleReservationRepository extends CrudRepository<SingleReservation, Integer> {

    //find a single reservation by id
    SingleReservation findSingleReservationById(int id);

    //find single reservations by date
    List<SingleReservation> findSingleReservationsByDate(Date date);

    //find single reservations by license number
    List<SingleReservation> findSingleReservationsByLicenseNumber(String licenseNumber);

    List<SingleReservation> findSingleReservationsByParkingSpot(ParkingSpot parkingSpot);

}

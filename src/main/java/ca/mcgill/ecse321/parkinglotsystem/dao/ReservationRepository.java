package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;

public interface ReservationRepository extends CrudRepository<Reservation, Integer>{
    
    SingleReservation findSingleReservationById(int id);
    SubWithAccount findSubWithAccountById(int id);
    SubWithoutAccount findSubWithoutAccountById(int id);

    List<Reservation> findReservationsByDate(Date date);

    // List<Reservation> findReservationsByLicenseNumber(String licenseNumber);
}

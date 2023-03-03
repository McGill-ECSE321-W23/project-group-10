package ca.mcgill.ecse321.parkinglotsystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.PaymentReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import java.util.List;
import java.util.Date;

public interface SingleReservationRepository extends CrudRepository<SingleReservation, Integer> {
    
    SingleReservation findSingleReservationById(int id);

    List<SingleReservation> findSingleReservationsByDate(Date date);

    List<SingleReservation> findSingleReservationsByLicenseNumber(String licenseNumber);
    
}

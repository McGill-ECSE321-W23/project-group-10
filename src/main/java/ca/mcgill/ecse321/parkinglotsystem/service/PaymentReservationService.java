package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.PaymentReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.PaymentReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;

/*
 * author Shaun Soobagrah
 */
@Service
public class PaymentReservationService {

    @Autowired
    PaymentReservationRepository paymentReservationRepository;

    @Transactional
    public List<PaymentReservation> getAllPaymentReservation() {
        Iterable<PaymentReservation> pIterable = paymentReservationRepository.findAll();
        return HelperMethods.toList(pIterable);
    }
    
    @Transactional
    public PaymentReservation createPaymentReservation(Timestamp dateTime, double amount, Reservation reservation ) {
        
        //input validation
        String error = "";
        if (dateTime == null) {
            error += "no date and time entered! ";
        }
        if (amount < 0) {
            error += "amount should be greater than 0! ";
        }
        // if (reservation == null) {
        //     error += "reservation cannot be empty! ";
        // }

        if (error.length() > 0) {
			throw new IllegalArgumentException(error);
        }

        PaymentReservation paymentReservation = new PaymentReservation();
        paymentReservation.setAmount(amount);
        paymentReservation.setDateTime(dateTime);
        paymentReservation.setReservation(reservation);
        paymentReservationRepository.save(paymentReservation);
        return paymentReservation;
    }


  
}

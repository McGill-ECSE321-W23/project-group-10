package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Timestamp; 

import ca.mcgill.ecse321.parkinglotsystem.model.PaymentReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;

@SpringBootTest
public class PaymentReservationRepositoryTests {
    
    @Autowired
    PaymentReservationRepository paymentReservationRepository;


    //should I create a reservation repository or a repo for each subclass
    @Autowired
    private Reservation reservation;

    @AfterEach
    public void clearDatabase() {
        paymentReservationRepository.deleteAll();

    }

    // @Test
    // public void testPersistAndLoadPaymentReservation() {

    //     String time="2018-09-01 09:01:15"; 
    //     Timestamp timestamp= Timestamp.valueOf(time); 
    //     double amount = 70.0;


    //     PaymentReservation paymentReservation = new PaymentReservation();
    //     paymentReservation.setAmount(amount);
    //     paymentReservation.setDateTime(timestamp);

    //     paymentReservation = paymentReservationRepository.save(paymentReservation);
    // }

}

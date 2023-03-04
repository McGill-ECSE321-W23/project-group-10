package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Timestamp;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.PaymentReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;

@SpringBootTest
public class PaymentReservationRepositoryTests {
    
    @Autowired
    PaymentReservationRepository paymentReservationRepository;
    @Autowired
    ParkingSpotTypeRepository parkingSpotTypeRepository;
    @Autowired
    ParkingSpotRepository parkingSpotRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @AfterEach
    public void clearDatabase() {
        paymentReservationRepository.deleteAll();
        reservationRepository.deleteAll();
        parkingSpotRepository.deleteAll();
        parkingSpotTypeRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadPaymentReservation() {
        // Create dummy data
        int id = 1;
        String time="2018-09-01 09:01:15"; 
        Timestamp timestamp= Timestamp.valueOf(time); 
        double amount = 70.0;

        // Save objects
        ParkingSpotType parkingSpotType = new ParkingSpotType();
        parkingSpotType.setName("someType");
        parkingSpotType.setFee(10);
        parkingSpotType = parkingSpotTypeRepository.save(parkingSpotType);

        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setType(parkingSpotType);
        parkingSpot = parkingSpotRepository.save(parkingSpot);

        SingleReservation reservation = new SingleReservation();
        reservation.setDate(Date.valueOf("2018-09-01"));
        reservation.setLicenseNumber("123");
        reservation.setParkingTime(15);
        reservation.setParkingSpot(parkingSpot);
        reservation = (SingleReservation) reservationRepository.save(reservation);
        int reservationId = reservation.getId();

        PaymentReservation paymentReservation = new PaymentReservation();
        paymentReservation.setId(id);
        paymentReservation.setAmount(amount);
        paymentReservation.setDateTime(timestamp);
        paymentReservation.setReservation(reservation);
        paymentReservation = paymentReservationRepository.save(paymentReservation);

        // Load objects
        paymentReservation = paymentReservationRepository.findPaymentReservationById(id);
        
        assertNotNull(paymentReservation);
        assertEquals(amount, paymentReservation.getAmount());
        assertEquals(timestamp, paymentReservation.getDateTime());
        assertEquals(reservationId, paymentReservation.getReservation().getId());
    }

}

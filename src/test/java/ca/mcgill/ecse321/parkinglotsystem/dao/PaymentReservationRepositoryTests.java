package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.sql.Date;

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
    SingleReservationRepository singleReservationRepository;

    @AfterEach
    public void clearDatabase() {
        paymentReservationRepository.deleteAll();
        singleReservationRepository.deleteAll();
        parkingSpotRepository.deleteAll();
        parkingSpotTypeRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadPaymentReservation() {

        // create a parking spot type
        ParkingSpotType parkingSpotType = new ParkingSpotType();
        parkingSpotType.setFee(10.0);
        parkingSpotType.setName("regular");
        parkingSpotType = parkingSpotTypeRepository.save(parkingSpotType);

        // create a parking spot 
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setType(parkingSpotType);
        parkingSpot = parkingSpotRepository.save(parkingSpot);

        // Create a new SingleReservation
        SingleReservation singleReservation = new SingleReservation();

        singleReservation.setDate(Date.valueOf("2023-02-27"));
        singleReservation.setLicenseNumber("ABC123");
        singleReservation.setParkingSpot(parkingSpot);
        singleReservation = singleReservationRepository.save(singleReservation);
        int reservationId = singleReservation.getId();

        String time = "2018-09-01 09:01:15";
        Timestamp timestamp = Timestamp.valueOf(time);
        double amount = 70.0;
        int id = 9;

        // Create a PaymentReservation object
        PaymentReservation paymentReservation = new PaymentReservation();
        paymentReservation.setAmount(amount);
        paymentReservation.setDateTime(timestamp);
        paymentReservation.setId(id);
        paymentReservation.setReservation(singleReservation);

        // Save object
        paymentReservationRepository.save(paymentReservation);

        // Load object
        paymentReservation = paymentReservationRepository.findPaymentReservationById(id);

        // Assertions
        assertNotNull(paymentReservation);
        assertEquals(amount, paymentReservation.getAmount());
        assertEquals(1, paymentReservationRepository.
                findPaymentReservationByReservation(singleReservation).size());
        assertEquals(reservationId, paymentReservation.getReservation().getId());
    }

}

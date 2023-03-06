package ca.mcgill.ecse321.parkinglotsystem.dao;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SingleReservationRepositoryTests {
    @Autowired
    private SingleReservationRepository singleReservationRepository;
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;
    @Autowired
    private ParkingSpotTypeRepository parkingSpotTypeRepository;

    @AfterEach
    public void clearDataBase() {
        singleReservationRepository.deleteAll();
        parkingSpotRepository.deleteAll();
        parkingSpotTypeRepository.deleteAll();
    }

    @Test
    public void testPersistenceAndLoadSingleReservation() {

        // create dummy data
        ParkingSpotType type = new ParkingSpotType();
        type.setFee(3.50);
        type.setName("regular");
        type = parkingSpotTypeRepository.save(type);
        ParkingSpot spot = new ParkingSpot();
        spot.setType(type);
        spot = parkingSpotRepository.save(spot);

        // Create a new SingleReservation
        SingleReservation singleReservation = new SingleReservation();
        singleReservation.setDate(Date.valueOf("2023-02-27"));
        singleReservation.setLicenseNumber("ABC123");
        singleReservation.setParkingSpot(spot);

        // Save the SingleReservation
        singleReservation = singleReservationRepository.save(singleReservation);
        int id = singleReservation.getId();

        // Read singleReservation from database
        singleReservation = singleReservationRepository.findSingleReservationById(id);

        // Assert that singleReservation has correct attributes
        assertNotNull(singleReservation);
        assertEquals("ABC123", singleReservation.getLicenseNumber());
        assertEquals(spot.getId(), singleReservation.getParkingSpot().getId());
        assertEquals(Date.valueOf("2023-02-27"), singleReservation.getDate());
        assertEquals(1, singleReservationRepository.findSingleReservationsByLicenseNumber("ABC123").size());
        assertEquals(1, singleReservationRepository.findSingleReservationsByDate(Date.valueOf("2023-02-27")).size());

    }


}


    


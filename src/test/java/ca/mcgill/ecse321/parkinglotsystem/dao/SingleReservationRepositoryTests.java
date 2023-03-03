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

    @AfterEach
    public void clearDataBase() {
        singleReservationRepository.deleteAll();
    }
    @Test
    public void testPersistenceAndLoadSingleReservation() {
        // create data
        ParkingSpot spot = new ParkingSpot();
        spot.setId(99);
        ParkingSpotType type = new ParkingSpotType();
        type.setFee(3.50);
        type.setName("regular");
        // TODO: Merge and uncomment this line spot = ParkingSpotRepository.save(spot);
        // TODO: Merge and uncomment this line type = ParkingSpotTypeRepository.save(type);

        // Create a new SingleReservation
        SingleReservation singleReservation = new SingleReservation();
        singleReservation.setId(1);
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
        assertEquals(1, singleReservation.getId());
        assertEquals("ABC123", singleReservation.getLicenseNumber());
        assertEquals(spot, singleReservation.getParkingSpot());
        assertEquals(Date.valueOf("2023-02-27"), singleReservation.getDate());
        assertEquals(1, singleReservationRepository.findSingleReservationsByLicenseNumber("ABC123").size());
        assertEquals(1, singleReservationRepository.findSingleReservationsByDate(Date.valueOf("2023-02-27")).size());


    }

    
}


    


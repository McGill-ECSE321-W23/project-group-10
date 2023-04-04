package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;

@SpringBootTest
public class ParkingSpotRepositoryTests {


    @Autowired
    private ParkingSpotRepository parkingSpotRepository;
    @Autowired
    private ParkingSpotTypeRepository parkingSpotTypeRepository;

    @AfterEach
    public void clearDatabase() {
        parkingSpotRepository.deleteAll();
        parkingSpotTypeRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadParkingSpot() {
        int id = 1000;
        String typeName = "regular";
        double fee = 30.0;

        //create parkingSpotType
        ParkingSpotType parkingSpotType = new ParkingSpotType();
        parkingSpotType.setName(typeName);
        parkingSpotType.setFee(fee);
        parkingSpotType = parkingSpotTypeRepository.save(parkingSpotType);

        //create Parking spot instance
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setId(id);
        parkingSpot.setType(parkingSpotType);

        parkingSpot = parkingSpotRepository.save(parkingSpot);

        parkingSpot = parkingSpotRepository.findParkingSpotById(id);

        // Assert that object has correct attributes
        assertNotNull(parkingSpot);
        assertEquals("regular", parkingSpot.getType().getName());
        assertEquals(1, parkingSpotRepository.findParkingSpotByType(parkingSpotType).size());
    }

}

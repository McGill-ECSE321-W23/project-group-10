package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;

@SpringBootTest
public class ParkingSpotTypeRepositoryTests {

    @Autowired
    private ParkingSpotTypeRepository parkingSpotTypeRepository;


    @AfterEach
    public void clearDatabase() {
        parkingSpotTypeRepository.deleteAll();

    }

    @Test
    public void testPersistAndLoadParkingSpotType() {
        String typeName = "regular";
        double fee = 30.0;

        //create parkingSpotType
        ParkingSpotType parkingSpotType = new ParkingSpotType();
        parkingSpotType.setName(typeName);
        parkingSpotType.setFee(fee);

        // Save object
        parkingSpotType = parkingSpotTypeRepository.save(parkingSpotType);

        // Assert that object has correct attributes
        assertEquals(typeName, parkingSpotType.getName());
        assertEquals(typeName, parkingSpotTypeRepository.findParkingSpotTypeByName(typeName).getName());
        assertEquals(fee, parkingSpotTypeRepository.findParkingSpotTypeByName(typeName).getFee());
    }


}
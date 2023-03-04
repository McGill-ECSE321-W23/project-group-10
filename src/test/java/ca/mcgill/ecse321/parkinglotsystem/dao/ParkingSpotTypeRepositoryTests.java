package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        System.out.println("cleaning db");
        parkingSpotTypeRepository.deleteAll();

    }

    @Test
    public void testPersistAndLoadParkingSpotType() {
        String typeName = "regular";
        double fee = 30.0;

        ParkingSpotType parkingSpotType = new ParkingSpotType();
        parkingSpotType.setName(typeName);
        parkingSpotType.setFee(fee);
        parkingSpotType = parkingSpotTypeRepository.save(parkingSpotType);

        assertNotNull(parkingSpotType);
        assertEquals(typeName, parkingSpotType.getName());
        assertEquals(fee, parkingSpotType.getFee());
        assertEquals(typeName, parkingSpotTypeRepository.findParkingSpotTypeByName(typeName).getName());
    }


}
package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.sql.Time;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingLotSystem;

@SpringBootTest
public class ParkingLotSystemRepositoryTests {
    @Autowired
    private ParkingLotSystemRepository parkingLotSystemRepository;

    @AfterEach
    public void clearDatabase() {
        parkingLotSystemRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadParkingLotSystem() {
        int id = 1234;
        Time openTime = Time.valueOf("11:24:56");
        Time closeTime = Time.valueOf("14:24:56");

        // Create object
        ParkingLotSystem obj = new ParkingLotSystem();
        //obj.setId([id_value]);
        obj.setId(id);
        obj.setOpenTime(openTime);
        obj.setCloseTime(closeTime);

        // Save object
        obj = parkingLotSystemRepository.save(obj);

        // Read object from database
        List<ParkingLotSystem> objs = new ArrayList<ParkingLotSystem>();
        List<List<ParkingLotSystem>> all_objs = new ArrayList<List<ParkingLotSystem>>();
        List<ParkingLotSystem> all_obj = new ArrayList<ParkingLotSystem>();
        obj = parkingLotSystemRepository.findParkingLotSystemById(id);
        all_obj.add(obj);
        objs = parkingLotSystemRepository.findParkingLotSystemByOpenTime(openTime);
        all_objs.add(objs);
        objs = parkingLotSystemRepository.findParkingLotSystemByCloseTime(closeTime);
        all_objs.add(objs);

        // Assert that object has correct attributes
        for (ParkingLotSystem aParkingLotSystem : all_obj) {
            assertNotNull(aParkingLotSystem);
            assertEquals(id, aParkingLotSystem.getId());
            assertEquals(openTime, aParkingLotSystem.getOpenTime());
            assertEquals(closeTime, aParkingLotSystem.getCloseTime());
        }

        for (List<ParkingLotSystem> aParkingLotSystemList : all_objs) {
            for (ParkingLotSystem aParkingLotSystem : aParkingLotSystemList) {
                assertNotNull(aParkingLotSystem);
                assertEquals(id, aParkingLotSystem.getId());
                assertEquals(openTime, aParkingLotSystem.getOpenTime());
                assertEquals(closeTime, aParkingLotSystem.getCloseTime());
            }
        }
    }
}
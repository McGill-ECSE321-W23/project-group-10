package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;

@SpringBootTest
public class SubWithoutAccountRepositoryTests {
    @Autowired
    private SubWithoutAccountRepository subWithoutAccountRepository;
    @Autowired
    private ParkingSpotTypeRepository parkingSpotTypeRepository;
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @AfterEach
    public void clearDatabase() {
        subWithoutAccountRepository.deleteAll();
        parkingSpotRepository.deleteAll();
        parkingSpotTypeRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadSubWithoutAccount() {
        // Create dummy data
        Date date = Date.valueOf("2023-02-22");
        String licenseNumber = "12345";
        int nbrMonths = 1;
        ParkingSpotType pSpotType = new ParkingSpotType();
        pSpotType.setName("regular");
        pSpotType.setFee(5.0);
        parkingSpotTypeRepository.save(pSpotType);
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setType(pSpotType);
        parkingSpotRepository.save(parkingSpot);

        // Create object
        SubWithoutAccount obj = new SubWithoutAccount();
        obj.setDate(date);
        obj.setLicenseNumber(licenseNumber);
        obj.setNbrMonths(nbrMonths);
        obj.setParkingSpot(parkingSpot);

        // Save object
        obj = subWithoutAccountRepository.save(obj);
        int id = obj.getId();

        // Read object from database
        obj = subWithoutAccountRepository.findSubWithoutAccountById(id);

        // Assert that object has correct attributes
        assertNotNull(obj);
        assertEquals(id, obj.getId());
        assertEquals(date, obj.getDate());
        assertEquals(licenseNumber, obj.getLicenseNumber());
        assertEquals(nbrMonths, obj.getNbrMonths());
        assertEquals(1, subWithoutAccountRepository.
            findSubWithoutAccountByLicenseNumber(licenseNumber).size());
        assertEquals(1, subWithoutAccountRepository.
            findSubWithoutAccountByParkingSpot(parkingSpot).size());
    }
}
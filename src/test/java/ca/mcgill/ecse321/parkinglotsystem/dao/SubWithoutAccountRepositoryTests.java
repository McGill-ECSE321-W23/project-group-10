package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;

@SpringBootTest
public class SubWithoutAccountRepositoryTests {
    @Autowired
    private SubWithoutAccountRepository subWithoutAccountRepository;

    @AfterEach
    public void clearDatabase() {
        subWithoutAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadSubWithoutAccount() {
        Date date = Date.valueOf("2023-02-22");
        String licenseNumber = "12345";
        int nbrMonths = 1;

        // Create object
        SubWithoutAccount obj = new SubWithoutAccount();
        //obj.setId([id_value]);
        obj.setDate(date);
        obj.setLicenseNumber(licenseNumber);
        obj.setNbrMonths(nbrMonths);
        // TODO: Set parking spot

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
    }
}
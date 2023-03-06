package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithAccount;

@SpringBootTest
public class SubWithAccountRepositoryTests {
    @Autowired
    private SubWithAccountRepository subWithAccountRepository;
    @Autowired
    private ParkingSpotTypeRepository parkingSpotTypeRepository;
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;
    @Autowired
    private MonthlyCustomerRepository monthlyCustomerRepository;

    @AfterEach
    public void clearDatabase() {
        subWithAccountRepository.deleteAll();
        parkingSpotRepository.deleteAll();
        parkingSpotTypeRepository.deleteAll();
        monthlyCustomerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadSubWithAccount() {
        // Create dummy data
        Date date = Date.valueOf("2023-02-22");
        int nbrMonths = 1;
        ParkingSpotType pSpotType = new ParkingSpotType();
        pSpotType.setName("regular");
        pSpotType.setFee(5.0);
        parkingSpotTypeRepository.save(pSpotType);
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setType(pSpotType);
        parkingSpotRepository.save(parkingSpot);
        MonthlyCustomer customer = new MonthlyCustomer();
        customer.setEmail("hello@world.com");
        customer.setLicenseNumber("12345");
        customer.setName("Marc");
        customer.setPassword("456");
        customer.setPhone("450");
        customer = monthlyCustomerRepository.save(customer);

        // Create object
        SubWithAccount obj = new SubWithAccount();
        obj.setDate(date);
        obj.setNbrMonths(nbrMonths);
        obj.setParkingSpot(parkingSpot);
        obj.setCustomer(customer);

        // Save object
        obj = subWithAccountRepository.save(obj);
        int id = obj.getId();

        // Read object from database
        obj = subWithAccountRepository.findSubWithAccountById(id);

        // Assert that object has correct attributes
        assertNotNull(obj);
        assertEquals(date, obj.getDate());
        assertEquals(nbrMonths, obj.getNbrMonths());
        assertEquals(customer.getEmail(), obj.getCustomer().getEmail());
        assertEquals(1, subWithAccountRepository.
                findSubWithAccountByParkingSpot(parkingSpot).size());
        assertEquals(1, subWithAccountRepository.
                findSubWithAccountByCustomer(customer).size());
    }
}
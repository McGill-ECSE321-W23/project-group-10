package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;

@SpringBootTest
public class MonthlyCustomerRepositoryTests {
    @Autowired
    private MonthlyCustomerRepository monthlyCustomerRepository;

    @AfterEach
    public void clearDatabase() {
        monthlyCustomerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadMonthlyCustomer() {
        // Create object
        String name = "Jonas";
        String email = "jonas@outlook.com";
        String phone = "3333";
        String password = "password3";
        String token = "123";
        MonthlyCustomer jonas = new MonthlyCustomer();
        jonas.setName(name);
        jonas.setEmail(email);
        jonas.setPhone(phone);
        jonas.setPassword(password);
        jonas.setToken(token);

        // Save object
        jonas = monthlyCustomerRepository.save(jonas);
        String id = jonas.getEmail();

        // Read object from database
        jonas = monthlyCustomerRepository.findMonthlyCustomerByEmail(id);
        var objsByName = monthlyCustomerRepository.findMonthlyCustomerByName(name);
        var objsByPass = monthlyCustomerRepository.findMonthlyCustomerByPassword(password);
        var objsByPhone = monthlyCustomerRepository.findMonthlyCustomerByPhone(phone);
        var objsByToken = monthlyCustomerRepository.findMonthlyCustomerByToken(token);

        // Assert that object has correct attributes
        assertNotNull(jonas);
        assertEquals(name, jonas.getName());
        assertEquals(phone, jonas.getPhone());
        assertEquals(password, jonas.getPassword());
        assertEquals(token, jonas.getToken());
        assertEquals(1, objsByName.size());
        assertEquals(1, objsByPass.size());
        assertEquals(1, objsByPhone.size());
        assertEquals(1, objsByToken.size());
    }
}

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
	public void testPersistAndLoadManager() {
        // Create object
		String name = "Jonas";
        String email="jonas@outlook.com";
        String phone="3333";
        String password="password3";
		MonthlyCustomer jonas = new MonthlyCustomer();
		jonas.setName(name);
        jonas.setEmail(email);
        jonas.setPhone(phone);
        jonas.setPassword(password);

        // Save object
		jonas = monthlyCustomerRepository.save(jonas);
		String id = jonas.getEmail();

		// Read object from database
		jonas = monthlyCustomerRepository.findMonthlyCustomerById(id);

		// Assert that object has correct attributes
		assertNotNull(jonas);
		assertEquals(name, jonas.getName());
		assertEquals(phone, jonas.getPhone());
		assertEquals(password, jonas.getPassword());
	}
}

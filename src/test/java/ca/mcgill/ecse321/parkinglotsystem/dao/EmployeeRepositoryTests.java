package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.Employee;

@SpringBootTest
public class EmployeeRepositoryTests {
    @Autowired
	private EmployeeRepository employeeRepository;

    @AfterEach
	public void clearDatabase() {
		employeeRepository.deleteAll();
	}

    @Test
	public void testPersistAndLoadManager() {
        // Create object
		String name = "Jesse Pinkman";
        String email="jesse@outlook.com";
        String phone="2222";
        String password="password2";
		Employee jesse = new Employee();
		jesse.setName(name);
        jesse.setEmail(email);
        jesse.setPhone(phone);
        jesse.setPassword(password);

        // Save object
		jesse = employeeRepository.save(jesse);
		String id = jesse.getEmail();

		// Read object from database
		jesse = employeeRepository.findEmployeeById(id);

		// Assert that object has correct attributes
		assertNotNull(jesse);
		assertEquals(name, jesse.getName());
		assertEquals(phone, jesse.getPhone());
		assertEquals(password, jesse.getPassword());
	}
}

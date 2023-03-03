package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.Manager;

@SpringBootTest
public class ManagerRepositoryTests {
    @Autowired
	private ManagerRepository managerRepository;

    @AfterEach
	public void clearDatabase() {
		managerRepository.deleteAll();
	}

    @Test
	public void testPersistAndLoadManager() {
        // Create object
		String name = "Walter White";
        String email="walter@outlook.com";
        String phone="1111";
        String password="password1";
		Manager walter = new Manager();
		walter.setName(name);
        walter.setEmail(email);
        walter.setPhone(phone);
        walter.setPassword(password);

        // Save object
		walter = managerRepository.save(walter);
		String id = walter.getEmail();

		// Read object from database
		walter = managerRepository.findManagerByEmail(id);

		// Assert that object has correct attributes
		assertNotNull(walter);
		assertEquals(name, walter.getName());
		assertEquals(phone, walter.getPhone());
		assertEquals(password, walter.getPassword());
	}
}

package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.parkinglotsystem.model.Services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceRepositoryTests {
    @Autowired
    private ServicesRepository serviceRepository;

    @AfterEach
    public void clearDatabase() {
        serviceRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadService() {
        // Create object
        String service_description = "this is the Genesis service";
        int service_price = 30;
        Services obj = new Services();
        obj.setPrice(service_price);
        obj.setDescription(service_description);
        // Save object
        obj = serviceRepository.save(obj);
        String return_description = obj.getDescription();
        // Read object from database
        obj = serviceRepository.findServiceByDescription(return_description);

        // Assert that object has correct attributes
        assertNotNull(obj);
        assertEquals(service_price, obj.getPrice());
        assertEquals(service_description, obj.getDescription());
        assertEquals(1, serviceRepository.findServiceByPrice(service_price).size());
    }
}
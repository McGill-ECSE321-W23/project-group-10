package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.Service;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithoutAccount;

@SpringBootTest
public class ServiceRequestRepositoryTests {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @AfterEach
    public void clearDatabase() {
        serviceRequestRepository.deleteAll();
        serviceRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadServiceRequest() {
        // Create dummy data
        boolean isAssigned = false;
        String licenseNumber = "123";
        
        Service service = new Service();
        service.setDescription("someService");
        service.setPrice(50);
        service = serviceRepository.save(service);

        // Create object
        ServiceReqWithoutAccount obj = new ServiceReqWithoutAccount();
        obj.setIsAssigned(isAssigned);
        obj.setLicenseNumber(licenseNumber);
        obj.setService(service);

        // Save object
        obj = serviceRequestRepository.save(obj);
        int id = obj.getId();

        // Read object from database
        obj = (ServiceReqWithoutAccount) serviceRequestRepository.findServiceRequestById(id);

        // Assert that object has correct attributes
        assertNotNull(obj);
        assertEquals(isAssigned, obj.getIsAssigned());
        assertEquals(licenseNumber, obj.getLicenseNumber());
        assertEquals(service.getDescription(), obj.getService().getDescription());
    }
}
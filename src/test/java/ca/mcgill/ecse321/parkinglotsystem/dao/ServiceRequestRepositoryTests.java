package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.parkinglotsystem.model.Services;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithoutAccount;

@SpringBootTest
public class ServiceRequestRepositoryTests {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ServicesRepository serviceRepository;

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

        Services services = new Services();
        services.setDescription("someService");
        services.setPrice(50);
        services = serviceRepository.save(services);

        // Create object
        ServiceReqWithoutAccount obj = new ServiceReqWithoutAccount();
        obj.setIsAssigned(isAssigned);
        obj.setLicenseNumber(licenseNumber);
        obj.setService(services);

        // Save object
        obj = serviceRequestRepository.save(obj);
        int id = obj.getId();

        // Read object from database
        obj = (ServiceReqWithoutAccount) serviceRequestRepository.findServiceRequestById(id);
        var objsIsAssigned = serviceRequestRepository.findServiceRequestByIsAssigned(isAssigned);
        var objsService = serviceRequestRepository.findServiceRequestByService(services);

        // Assert that object has correct attributes
        assertNotNull(obj);
        assertEquals(isAssigned, obj.getIsAssigned());
        assertEquals(licenseNumber, obj.getLicenseNumber());
        assertEquals(services.getDescription(), obj.getService().getDescription());
        assertEquals(1, objsIsAssigned.size());
        assertEquals(1, objsService.size());
    }
}
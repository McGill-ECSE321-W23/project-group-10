package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.parkinglotsystem.model.Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithoutAccount;

@SpringBootTest
public class ServiceReqWithoutAccountRepositoryTests {
    @Autowired
    private ServiceReqWithoutAccountRepository serviceReqWithoutAccountRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @AfterEach
    public void clearDatabase() {
        serviceReqWithoutAccountRepository.deleteAll();

        serviceRepository.deleteAll();

    }

    @Test
    public void testPersistAndLoadServiceReqWithoutAccount() {
        boolean isAssigned = true;
        String licenseNumber = "abcd";
        String serviceDesc = "someService";
        Service services = new Service();
        services.setDescription(serviceDesc);
        services.setPrice(50);
        services = serviceRepository.save(services);

        // Create object
        ServiceReqWithoutAccount obj = new ServiceReqWithoutAccount();
        obj.setIsAssigned(isAssigned);
        obj.setLicenseNumber(licenseNumber);
        obj.setService(services);

        // Save object
        obj = serviceReqWithoutAccountRepository.save(obj);

        int id = obj.getId();


        // Read object from database
        obj = serviceReqWithoutAccountRepository.findServiceReqWithoutAccountById(id);
        var objs1 = serviceReqWithoutAccountRepository.
                findServiceReqWithoutAccountByIsAssigned(isAssigned);
        var objs2 = serviceReqWithoutAccountRepository.
                findServiceReqWithoutAccountByService(services);
        var objs3 = serviceReqWithoutAccountRepository.
                findServiceReqWithoutAccountByLicenseNumber(licenseNumber);

        // Assertions
        assertNotNull(obj);
        assertEquals(isAssigned, obj.getIsAssigned());
        assertEquals(licenseNumber, obj.getLicenseNumber());
        assertEquals(serviceDesc, obj.getService().getDescription());
        assertEquals(1, objs1.size());
        assertEquals(1, objs2.size());
        assertEquals(1, objs3.size());
        assertEquals(id, objs1.get(0).getId());
        assertEquals(id, objs2.get(0).getId());
        assertEquals(id, objs3.get(0).getId());
    }
}
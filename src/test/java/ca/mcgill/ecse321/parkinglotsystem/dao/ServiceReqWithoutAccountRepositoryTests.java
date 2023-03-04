package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithoutAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;

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
        Service service = new Service();
        service.setDescription(serviceDesc);
        service.setPrice(50);
        service = serviceRepository.save(service);

        // Create object
        ServiceReqWithoutAccount obj = new ServiceReqWithoutAccount();
        obj.setIsAssigned(isAssigned);
        obj.setLicenseNumber(licenseNumber);
        obj.setService(service);

        // Save object
        obj = serviceReqWithoutAccountRepository.save(obj);
        int id = obj.getId();

        // Read object from database
        obj = serviceReqWithoutAccountRepository.findServiceReqWithoutAccountById(id);
        var objs1 = serviceReqWithoutAccountRepository.
            findServiceReqWithoutAccountByIsAssigned(isAssigned);
        var objs2 = serviceReqWithoutAccountRepository.
            findServiceReqWithoutAccountByService(service);
        var objs3 = serviceReqWithoutAccountRepository.
            findServiceReqWithoutAccountBylicenseNumber(licenseNumber);

        // Assertions
        assertNotNull(obj);
        assertEquals(obj.getIsAssigned(), isAssigned);
        assertEquals(obj.getLicenseNumber(), licenseNumber);
        assertEquals(obj.getService().getDescription(), serviceDesc);
        assertEquals(objs1.size(), 1);
        assertEquals(objs2.size(), 1);
        assertEquals(objs3.size(), 1);
        assertEquals(objs1.get(0).getId(), id);
        assertEquals(objs2.get(0).getId(), id);
        assertEquals(objs3.get(0).getId(), id);
    }
}
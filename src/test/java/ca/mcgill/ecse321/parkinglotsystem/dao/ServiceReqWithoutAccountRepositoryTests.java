package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.ArrayList;

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

    @AfterEach
    public void clearDatabase() {
        serviceReqWithoutAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadServiceReqWithoutAccount() {
        int id = 1234;
        boolean isAssigned = true;
        String licenseNumber = "abcd"; 
        Service service = new Service();

        // Create object
        ServiceReqWithoutAccount obj = new ServiceReqWithoutAccount();
        obj.setAssigned(isAssigned);
        obj.setLicenseNumber(licenseNumber);
        obj.setService(service);

        // Save object
        obj = serviceReqWithoutAccountRepository.save(obj);
        serviceRepository.save(service);

        // Read object from database
        List<ServiceReqWithoutAccount> objs = new ArrayList<ServiceReqWithoutAccount>();
        List<List<ServiceReqWithoutAccount>> all_objs = new ArrayList<List<ServiceReqWithoutAccount>>();
        List<ServiceReqWithoutAccount> all_obj = new ArrayList<ServiceReqWithoutAccount>();
        obj = serviceReqWithoutAccountRepository.findServiceReqWithoutAccountById(id);
        all_obj.add(obj);
        objs = serviceReqWithoutAccountRepository.findServiceReqWithoutAccountByIsAssigned(isAssigned);
        all_objs.add(objs);
        objs = serviceReqWithoutAccountRepository.findServiceReqWithoutAccountByService(service);
        all_objs.add(objs);
        obj = serviceReqWithoutAccountRepository.findServiceReqWithoutAccountBylicenseNumber(licenseNumber);
        all_obj.add(obj);

        // Assert that object has correct attributes
        for (ServiceReqWithoutAccount aServiceReqWithoutAccount : all_obj){
            assertNotNull(aServiceReqWithoutAccount);
            assertEquals(id, aServiceReqWithoutAccount.getId());
            assertEquals(isAssigned, aServiceReqWithoutAccount.isAssigned());
            assertEquals(licenseNumber, aServiceReqWithoutAccount.getLicenseNumber());
            assertEquals(service, aServiceReqWithoutAccount.getService());
        }

        for (List<ServiceReqWithoutAccount> aServiceReqWithoutAccountList : all_objs){
            for (ServiceReqWithoutAccount aServiceReqWithoutAccount : aServiceReqWithoutAccountList){
                assertNotNull(aServiceReqWithoutAccount);
                assertEquals(id, aServiceReqWithoutAccount.getId());
                assertEquals(isAssigned, aServiceReqWithoutAccount.isAssigned());
                assertEquals(licenseNumber, aServiceReqWithoutAccount.getLicenseNumber());
                assertEquals(service, aServiceReqWithoutAccount.getService());
            }
        }
    }
}
package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;

@SpringBootTest
public class ServiceReqWithAccountRepositoryTests {
    @Autowired
    private ServiceReqWithAccountRepository serviceReqWithAccountRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private MonthlyCustomerRepository monthlyCustomerRepository;

    @AfterEach
    public void clearDatabase() {
        serviceReqWithAccountRepository.deleteAll();
        
        serviceRepository.deleteAll();
        monthlyCustomerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadServiceReqWithAccount() {
        //int id = 1234;
        boolean isAssigned = true;

        Service service = new Service();
        service.setDescription("someService");
        service.setPrice(50);
        service = serviceRepository.save(service);

        MonthlyCustomer monthlyCustomer = new MonthlyCustomer();
        monthlyCustomer.setEmail("hello@world.com");
        monthlyCustomer.setLicenseNumber("123");
        monthlyCustomer.setName("Marc");
        monthlyCustomer.setPassword("456");
        monthlyCustomer.setPhone("450");
        monthlyCustomer = monthlyCustomerRepository.save(monthlyCustomer);

        // Create object
        ServiceReqWithAccount obj = new ServiceReqWithAccount();
        obj.setIsAssigned(isAssigned);
        obj.setCustomer(monthlyCustomer);
        obj.setService(service);

        // Save object
        obj = serviceReqWithAccountRepository.save(obj);

        int id = obj.getId();


        // Read object from database
        obj = serviceReqWithAccountRepository.findServiceReqWithAccountById(id);
        var objs1 = serviceReqWithAccountRepository.findServiceReqWithAccountByIsAssigned(isAssigned);
        var objs2 = serviceReqWithAccountRepository.findServiceReqWithAccountByService(service);
        var objs3 = serviceReqWithAccountRepository.findServiceReqWithAccountByCustomer(monthlyCustomer);

        // Assertions
        assertNotNull(obj);
        assertEquals(isAssigned, obj.getIsAssigned());
        assertEquals(service.getDescription(), obj.getService().getDescription());
        assertEquals(monthlyCustomer.getEmail(), obj.getCustomer().getEmail());
        assertEquals(1, objs1.size());
        assertEquals(1, objs2.size());
        assertEquals(1, objs3.size());
        assertEquals(id, objs1.get(0).getId());
        assertEquals(id, objs2.get(0).getId());
        assertEquals(id, objs3.get(0).getId());
    }
}
package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.ArrayList;

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

    @AfterEach
    public void clearDatabase() {
        serviceReqWithAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadServiceReqWithAccount() {
        int id = 1234;
        boolean isAssigned = true;
        Service service = new Service();
        MonthlyCustomer monthlyCustomer = new MonthlyCustomer();

        // Create object
        ServiceReqWithAccount obj = new ServiceReqWithAccount();
        //obj.setId([id_value]);
        obj.setId(id);
        obj.setAssigned(isAssigned);
        obj.setCustomer(monthlyCustomer);
        obj.setService(service);

        // Save object
        obj = serviceReqWithAccountRepository.save(obj);

        // Read object from database
        List<ServiceReqWithAccount> objs = new ArrayList<ServiceReqWithAccount>();
        List<List<ServiceReqWithAccount>> all_objs = new ArrayList<List<ServiceReqWithAccount>>();
        List<ServiceReqWithAccount> all_obj = new ArrayList<ServiceReqWithAccount>();
        obj = serviceReqWithAccountRepository.findServiceReqWithAccountById(id);
        all_obj.add(obj);
        objs = serviceReqWithAccountRepository.findServiceReqWithAccountByIsAssigned(isAssigned);
        all_objs.add(objs);
        objs = serviceReqWithAccountRepository.findServiceReqWithAccountByService(service);
        all_objs.add(objs);
        objs = serviceReqWithAccountRepository.findServiceReqWithAccountByMonthlyCustomer(monthlyCustomer);
        all_objs.add(objs);

        // Assert that object has correct attributes
        for (ServiceReqWithAccount aServiceReqWithAccount : all_obj){
            assertNotNull(aServiceReqWithAccount);
            assertEquals(id, aServiceReqWithAccount.getId());
            assertEquals(isAssigned, aServiceReqWithAccount.isAssigned());
            assertEquals(monthlyCustomer, aServiceReqWithAccount.getCustomer());
            assertEquals(service, aServiceReqWithAccount.getService());
        }

        for (List<ServiceReqWithAccount> aServiceReqWithAccountList : all_objs){
            for (ServiceReqWithAccount aServiceReqWithAccount : aServiceReqWithAccountList){
                assertNotNull(aServiceReqWithAccount);
                assertEquals(id, aServiceReqWithAccount.getId());
                assertEquals(isAssigned, aServiceReqWithAccount.isAssigned());
                assertEquals(monthlyCustomer, aServiceReqWithAccount.getCustomer());
                assertEquals(service, aServiceReqWithAccount.getService());
            }
        }
    }
}
package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.PaymentService;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;

import java.sql.Timestamp;
import java.sql.Date;

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentServiceRepositoryTests {
    @Autowired
    private PaymentServiceRepository paymentServiceRepository;
    @Autowired
    private ServiceReqWithAccountRepository serviceRequestRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private MonthlyCustomerRepository customerRepository;

    @AfterEach
    public void clearDatabase() {
        paymentServiceRepository.deleteAll();
        serviceRequestRepository.deleteAll();
        serviceRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadPaymentService() {
        // Create object
        int id = 180;
        double amount = 99.99;
        Date date_date = Date.valueOf("2023-03-01");
        Timestamp date = new Timestamp(date_date.getTime());

        Service service = new Service();
        service.setDescription("someService");
        service.setPrice(100);
        service = serviceRepository.save(service);

        MonthlyCustomer customer = new MonthlyCustomer();
        customer.setEmail("hello@world.com");
        customer.setLicenseNumber("1234");
        customer.setName("Marc");
        customer.setPassword("123");
        customer.setPhone("567");
        customer = customerRepository.save(customer);

        ServiceReqWithAccount service_request = new ServiceReqWithAccount();
        service_request.setIsAssigned(false);
        service_request.setService(service);
        service_request.setCustomer(customer);
        service_request = serviceRequestRepository.save(service_request);

        PaymentService obj = new PaymentService();
        obj.setId(id);
        obj.setAmount(amount);
        obj.setDateTime(date);
        obj.setServiceReq(service_request);

        // Save object

        obj = paymentServiceRepository.save(obj);
        int payment_service_id = obj.getId();

        // Read object from database
        obj = paymentServiceRepository.findPaymentServiceById(payment_service_id);

        // Assert that object has correct attributes
        assertNotNull(obj);
        assertEquals(amount, obj.getAmount());
        assertEquals(date, obj.getDateTime());
        assertEquals(service_request.getId(), obj.getServiceReq().getId());
        assertEquals(1, paymentServiceRepository.findPaymentServiceByAmount(amount).size());
        assertEquals(1, paymentServiceRepository.findPaymentServiceByDateTime(date).size());
        assertEquals(1, paymentServiceRepository.findPaymentServiceByServiceReq(service_request).size());
    }
}
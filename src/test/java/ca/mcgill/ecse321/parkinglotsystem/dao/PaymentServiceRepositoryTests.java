package ca.mcgill.ecse321.parkinglotsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.parkinglotsystem.model.PaymentService;
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

    @AfterEach
    public void clearDatabase() {
        paymentServiceRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadPaymentService() {
        // Create object
        int id = 180;
        double amount = 99.99;
        Date date = Date.valueOf("2023-03-01");
        ServiceReqWithAccount service_request = new ServiceReqWithAccount();
        PaymentService obj = new PaymentService ();
        obj.setId (id);
        obj.setAmount(amount);
        obj.setDateTime(date);
        obj.setServiceReq(service_request);

        // Save object
        obj = paymentServiceRepository.save(obj);
        int payment_service_id = obj.getId ();

        // Read object from database
        obj = paymentServiceRepository.findPaymentServiceById (payment_service_id);

        // Assert that object has correct attributes
        assertNotNull(obj);
        assertEquals(amount, obj.getAmount());
        assertEquals(date, obj.getDateTime());
        assertEquals(service_request, obj.getServiceReq());
        assertEquals(1, paymentServiceRepository.findPaymentServiceByAmount(amount).size());
        assertEquals(1, paymentServiceRepository.findPaymentServiceByDateTime(date).size());
        assertEquals(1, paymentServiceRepository.findPaymentServiceByServiceReq(service_request).size());
    }
}
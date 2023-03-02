package ca.mcgill.ecse321.parkinglotsystem.dao;

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import ca.mcgill.ecse321.parkinglotsystem.model.PaymentService;

public interface PaymentServiceRepository extends CrudRepository<PaymentService, Integer> {
    PaymentService findPaymentServiceById (Integer id);
    List<PaymentService> findPaymentServiceByDateTime (Timestamp DateTime);
    List<PaymentService> findPaymentServiceByAmount (Double amount);
    List<PaymentService> findPaymentServiceByServiceReq(ServiceRequest serviceReq);


}

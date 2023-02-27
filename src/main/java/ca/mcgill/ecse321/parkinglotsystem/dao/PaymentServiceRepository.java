package ca.mcgill.ecse321.parkinglotsystem.dao;

import ca.mcgill.ecse321.parkinglotsystem.model.Service;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import ca.mcgill.ecse321.parkinglotsystem.model.PaymentService;

public interface PaymentServiceRepository extends CrudRepository<PaymentService, Integer> {
    PaymentService findPaymentServiceById (Integer id);
    List<PaymentService> findPaymentServiceByDateTime (Date DateTime);
    List<PaymentService> findPaymentServiceByAmount (Double amount);
    List<PaymentService> findPaymentServiceByServiceReq(ServiceRequest serviceReq);


}

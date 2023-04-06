package ca.mcgill.ecse321.parkinglotsystem.dao;

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.model.PaymentService;

public interface PaymentServiceRepository extends CrudRepository<PaymentService, Integer> {

    //find a payment service by id
    PaymentService findPaymentServiceById(Integer id);

    //find payment services by date time
    List<PaymentService> findPaymentServiceByDateTime(Timestamp DateTime);

    //find payment services by amount
    List<PaymentService> findPaymentServiceByAmount(Double amount);

    //find payment services by service request
    List<PaymentService> findPaymentServiceByServiceReq(ServiceRequest serviceReq);


}

package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Timestamp;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dao.*;
import ca.mcgill.ecse321.parkinglotsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;

@Service
public class PaymentServiceService {

    @Autowired
    PaymentServiceRepository paymentServiceRepository;
    @Autowired
    ServiceRequestRepository serviceRequestRepository;

    /**
     * method to create a payment service
     *
     * @param id
     * @param amount
     * @param dateTime
     * @param serviceRequest
     * @return
     */
    @Transactional
    public PaymentService createPaymentService(int id, double amount, Timestamp dateTime, ServiceRequest serviceRequest) {
        // Input validation
        String val_int = id + "";
        String val_double = amount + "";
        Timestamp create_time = new Timestamp(1648094400);
        Timestamp current_time = new Timestamp(System.currentTimeMillis());
        int compare_create = dateTime.compareTo(create_time);
        int compare_current = dateTime.compareTo(current_time);
        if (val_int == null || id < 0) {
            throw new IllegalArgumentException("payment service id cannot be null or negative!");
        } else if (paymentServiceRepository.findPaymentServiceById(id) != null) {
            throw new IllegalArgumentException("payment service id already exist!");
        }
        if (val_double == null|| amount < 0) {
            throw new IllegalArgumentException("payment amount cannot be negative!");
        }
        if (dateTime == null||compare_create < 0||compare_current>0) {
            throw new IllegalArgumentException("payment service date Time is wrong!");
        }
        if (serviceRequestRepository.findServiceRequestById(serviceRequest.getId()) == null) {
            throw new IllegalArgumentException("payment service does not exist in service request repository!");
        }
        PaymentService paymentService = new PaymentService();
        paymentService.setId(id);
        paymentService.setAmount(amount);
        paymentService.setDateTime(dateTime);
        paymentService.setServiceReq(serviceRequest);
        paymentServiceRepository.save(paymentService);

        return paymentService;
    }

    public List<PaymentService> getAllPaymentService() {

        Iterable<PaymentService> pIterable = paymentServiceRepository.findAll();
        return HelperMethods.toList(pIterable);

    }

    // method to find a payment service by id
    @Transactional
    public PaymentService getPaymentServiceById(Integer id) {
        return paymentServiceRepository.findPaymentServiceById(id);
    }

    // method to find a payment service by payment amount
    @Transactional
    public List<PaymentService> getPaymentServiceByAmount(Double amount) {
        return paymentServiceRepository.findPaymentServiceByAmount(amount);
    }

    // method to find a payment service by date time
    @Transactional
    public List<PaymentService> getPaymentServiceByDateTime(Timestamp DateTime) {
        return paymentServiceRepository.findPaymentServiceByDateTime(DateTime);
    }


    // method to find a payment service by service request
    @Transactional
    public List<PaymentService> getPaymentServiceByServiceRequest(ServiceRequest serviceReq) {
        return paymentServiceRepository.findPaymentServiceByServiceReq(serviceReq);
    }

    // method to delete a payment service
    @Transactional
    public PaymentService deletePaymentService(Integer id) {

        // Input validation
        String error = "";
        String val = id + "";
        if (val == "" || val.length() == 0) {
            error = error + "a id must be mention to delete a payment service! ";
        }


        PaymentService paymentService = paymentServiceRepository.findPaymentServiceById(id);

        if (paymentService == null) {
            error = error + "no such payment service exist! ";
        }

        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        //we must delete the payment service as a payment service must have a service request
        if (serviceRequestRepository.findAll() != null) {
            Iterable<ServiceRequest> serviceRequests = serviceRequestRepository.findAll();
            for (ServiceRequest p : serviceRequests) {
                if (p.getId() == id) {
                    serviceRequestRepository.delete(p);
                }
            }

        }
        paymentServiceRepository.delete(paymentService);

        return paymentService;
    }

    // method to update the payment service
    @Transactional
    public PaymentService updatePaymentService(int id, Timestamp dateTime, double amount, ServiceRequest serviceRequest) {
        // Input validation
        String val_double_1 = amount + "";
        Timestamp create_time = new Timestamp(1648094400);
        Timestamp current_time = new Timestamp(System.currentTimeMillis());
        int compare_create = dateTime.compareTo(create_time);
        int compare_current = dateTime.compareTo(current_time);
        PaymentService paymentService = paymentServiceRepository.findPaymentServiceById(id);
        if (val_double_1 == null||amount<0) {
            throw new IllegalArgumentException("payment amount cannot be negative!");
        }
        if (dateTime == null||compare_create < 0||compare_current>0) {
            throw new IllegalArgumentException("payment service date Time cannot be null!");
        }
        if (serviceRequestRepository.findServiceRequestById(serviceRequest.getId()) == null) {
            throw new IllegalArgumentException("payment service does not exist in service request repository!");
        }
        paymentService.setAmount(amount);
        paymentService.setDateTime(dateTime);
        paymentService.setServiceReq(serviceRequest);

        return paymentService;

    }
}

package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Timestamp;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dao.*;
import ca.mcgill.ecse321.parkinglotsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

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
    public PaymentService createPaymentService(double amount, Timestamp dateTime, ServiceRequest serviceRequest) {
        // Input validation
        String val_double = amount + "";
        Timestamp create_time = new Timestamp(1648094400);
        Timestamp current_time = new Timestamp(System.currentTimeMillis());
        int compare_create = dateTime.compareTo(create_time);
        int compare_current = dateTime.compareTo(current_time);
        if (val_double == null|| amount < 0) {
            throw new IllegalArgumentException("payment amount cannot be negative!");
        }
        if (dateTime == null||compare_create < 0||compare_current>0) {
            throw new IllegalArgumentException("payment service date time is wrong!");
        }
        if (serviceRequestRepository.findServiceRequestById(serviceRequest.getId()) == null) {
            throw new IllegalArgumentException("payment service does not exist in service request repository!");
        }
        PaymentService paymentService = new PaymentService();
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
    public List<PaymentService> getPaymentServiceByServiceRequest(int serviceReqId) {
        List<PaymentService> paymentServiceList = paymentServiceRepository.findPaymentServiceByServiceReq(
            serviceRequestRepository.findServiceRequestById(serviceReqId));
        return paymentServiceList;
    }

    // method to delete a payment service
    @Transactional
    public PaymentService deletePaymentService(Integer id) {
        // Input validation
        String error = "";
        if (id == 0) {
            throw new CustomException("a id must be mention to delete a payment service! ", HttpStatus.BAD_REQUEST);
        }
        PaymentService paymentService = paymentServiceRepository.findPaymentServiceById(id);

        if (paymentService == null) {
            throw new CustomException("no such payment service exist! ", HttpStatus.NOT_FOUND);
        }
        paymentServiceRepository.delete(paymentService);

        return paymentService;
    }

    // method to update the payment service
    @Transactional
    public PaymentService updatePaymentService(int id, Timestamp dateTime, double amount, ServiceRequest serviceRequest) {
        // Input validation
        Timestamp create_time = new Timestamp(1648094400);
        Timestamp current_time = new Timestamp(System.currentTimeMillis());
        int compare_create = dateTime.compareTo(create_time);
        int compare_current = dateTime.compareTo(current_time);
        PaymentService paymentService = paymentServiceRepository.findPaymentServiceById(id);
        if (amount<0) {
            throw new IllegalArgumentException("payment amount cannot be negative!");
        }
        if (dateTime == null||compare_create < 0||compare_current>0) {
            throw new IllegalArgumentException("payment service date time is wrong!");
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

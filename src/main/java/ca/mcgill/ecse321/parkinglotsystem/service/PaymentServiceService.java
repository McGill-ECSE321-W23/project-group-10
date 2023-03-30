package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Timestamp;
import java.util.ArrayList;
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
     * Method to create a payment service.
     * @param amount the amount of the payment service
     * @param dateTime the date time of the payment service
     * @param serviceRequest the associated service request of the payment service
     * @return A PaymentService
     * @throws CustomException if to create the payment service fail
     */
    @Transactional
    public PaymentService createPaymentService(double amount, Timestamp dateTime, int serviceRequestId) {
        // Input validation
        String val_double = amount + "";
        Timestamp create_time = new Timestamp(1648094400);
        Timestamp current_time = new Timestamp(System.currentTimeMillis());
        int compare_create = dateTime.compareTo(create_time);
        int compare_current = dateTime.compareTo(current_time);
        if (val_double == null|| amount < 0) {
            throw new CustomException("payment amount cannot be negative!", HttpStatus.BAD_REQUEST);
        }
        if (dateTime == null||compare_create < 0||compare_current>0) {
            throw new CustomException("payment service date time is wrong!", HttpStatus.BAD_REQUEST);
        }
        if (serviceRequestRepository.findServiceRequestById(serviceRequestId) == null) {
            throw new CustomException("payment service does not exist in service request repository!", HttpStatus.NOT_FOUND);
        }
        ServiceRequest serviceRequest = serviceRequestRepository.findServiceRequestById(serviceRequestId);
        PaymentService paymentService = new PaymentService();
        paymentService.setAmount(amount);
        paymentService.setDateTime(dateTime);
        paymentService.setServiceReq(serviceRequest);
        paymentServiceRepository.save(paymentService);

        return paymentService;
    }

    /**
     * Method to get all payment services.
     * @return A List of PaymentService
     */
    public List<PaymentService> getAllPaymentService() {

        Iterable<PaymentService> pIterable = paymentServiceRepository.findAll();
        return HelperMethods.toList(pIterable);

    }

    /**
     * Method to get a payment service by id.
     * @param id the id of the payment service
     * @return A PaymentService or null
     */
    @Transactional
    public PaymentService getPaymentServiceById(Integer id) {
        return paymentServiceRepository.findPaymentServiceById(id);
    }

    /**
     * Method to get payment services by amount.
     * @param amount the amount of the payment service
     * @return A List of PaymentService or null
     */
    @Transactional
    public List<PaymentService> getPaymentServiceByAmount(Double amount) {
        return paymentServiceRepository.findPaymentServiceByAmount(amount);
    }

    /**
     * Method to get payment services by date time.
     * @param DateTime the date time of the payment service
     * @return A List of PaymentService or null
     */
    @Transactional
    public List<PaymentService> getPaymentServiceByDateTime(Timestamp DateTime) {
        return paymentServiceRepository.findPaymentServiceByDateTime(DateTime);
    }


    /**
     * Method to get payment services by service request.
     * @param serviceReqId the id of the associated service request
     * @return A List of PaymentService or null
     */
    @Transactional
    public List<PaymentService> getPaymentServiceByServiceRequest(int serviceReqId) {
        List<PaymentService> paymentServiceList = paymentServiceRepository.findPaymentServiceByServiceReq(
            serviceRequestRepository.findServiceRequestById(serviceReqId));
        return paymentServiceList;
    }

    /**
     * Method to delete a payment service.
     * @param id the id of the payment service
     * @return the deleted PaymentService
     * @throws CustomException if to delete the payment service fail
     */
    @Transactional
    public PaymentService deletePaymentService(Integer id) {
        // Input validation
        String error = "";
        if (id == 0) {
            throw new CustomException("a id must be mention to delete a payment service! ", HttpStatus.BAD_REQUEST);
        }
        PaymentService paymentService = paymentServiceRepository.findPaymentServiceById(id);

        if (paymentService == null) {

            error = error + "no such payment service exist! ";
        }

        if (error.length() > 0) {
            throw new CustomException(error, HttpStatus.NOT_FOUND);
        }

        paymentServiceRepository.delete(paymentService);

        return paymentService;
    }

    /**
     * Method to update a payment service.
     * @param amount the amount of the payment service
     * @param dateTime the date time of the payment service
     * @param serviceRequest the associated service request of the payment service
     * @return the updated PaymentService
     * @throws CustomException if to update the payment service fail
     */
    @Transactional
    public PaymentService updatePaymentService(int id, Timestamp dateTime, double amount, int serviceRequestId) {
        // Input validation
        Timestamp create_time = new Timestamp(1648094400);
        Timestamp current_time = new Timestamp(System.currentTimeMillis());
        int compare_create = dateTime.compareTo(create_time);
        int compare_current = dateTime.compareTo(current_time);
        PaymentService paymentService = paymentServiceRepository.findPaymentServiceById(id);
        if (amount<0) {
            throw new CustomException("payment amount cannot be negative!", HttpStatus.BAD_REQUEST);
        }
        if (dateTime == null||compare_create < 0||compare_current>0) {
            throw new CustomException("payment service date time is wrong!", HttpStatus.BAD_REQUEST);
        }
        if (serviceRequestRepository.findServiceRequestById(serviceRequestId) == null) {
            throw new CustomException("payment service does not exist in service request repository!", HttpStatus.NOT_FOUND);
        }
        ServiceRequest serviceRequest = serviceRequestRepository.findServiceRequestById(serviceRequestId);
        paymentService.setAmount(amount);
        paymentService.setDateTime(dateTime);
        paymentService.setServiceReq(serviceRequest);

        return paymentService;

    }
}

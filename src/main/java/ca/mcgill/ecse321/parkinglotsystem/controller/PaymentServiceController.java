package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dto.PaymentServiceDto;
import ca.mcgill.ecse321.parkinglotsystem.service.PaymentServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.model.*;
import ca.mcgill.ecse321.parkinglotsystem.service.AuthenticationService;

import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/payment-service")
public class PaymentServiceController {

    @Autowired
    PaymentServiceService paymentServiceService;

    @Autowired
    AuthenticationService authService;

    /**
     * method to get all payment services
     *
     * @return List<PaymentServiceDto>
     * @throws Exception
     */
    @GetMapping(value = {"", "/"})
    public List<PaymentServiceDto> getAllPaymentService(@RequestHeader String token) throws Exception {
        authService.authenticateManager(token);
        List<PaymentServiceDto> paList = new ArrayList<>();
        for (PaymentService paymentService : paymentServiceService.getAllPaymentService()) {
            paList.add(convertPaymentServiceToDto(paymentService));
        }
        if (paList.size() == 0) throw new Exception("There are no payment service");
        return paList;
    }

    /**
     * create a payment service
     *
     * @param id
     * @param amount
     * @param dateTime
     * @param serviceRequest
     * @return PaymentServiceDto
     */
    @PostMapping(value = {"/{id}", "/{id}/"})
    public PaymentServiceDto createPaymentService(
        @PathVariable("id") int id, 
        @RequestParam("amount") double amount, 
        @RequestParam("dateTime") Timestamp dateTime, 
        @RequestParam("serviceRequest") ServiceRequest serviceRequest) {
        // TODO: Remove id parameter (since id is generated automatically)
        try {
            PaymentService paymentService = paymentServiceService.createPaymentService(id, amount, dateTime, serviceRequest);
            return convertPaymentServiceToDto(paymentService);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());

        }
    }

    /**
     * method to get a payment service by id
     *
     * @param id
     * @return PaymentServiceDto
     */
    @GetMapping(value = {"/{id}", "/{id}/"})
    public PaymentServiceDto getPaymentServiceById(@PathVariable("id") int id, @RequestHeader String token) {
        authService.authenticateManager(token);
        try {
            PaymentService paymentService = paymentServiceService.getPaymentServiceById(id);
            return convertPaymentServiceToDto(paymentService);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * method to get payment services by amount
     *
     * @param amount
     * @return List<PaymentServiceDto>
     * @throws Exception
     */
    @GetMapping(value = {"/all-by-amount/{amount}", "/all-by-amount/{amount}/"})
    public List<PaymentServiceDto> getPaymentServiceByAmount(
        @PathVariable("amount") double amount, 
        @RequestHeader String token) {
        authService.authenticateManager(token);
        List<PaymentServiceDto> amList = new ArrayList<>();
        try {
            List<PaymentService> paymentService = paymentServiceService.getPaymentServiceByAmount(amount);
            if (paymentService.size() != 0) {
                for (PaymentService pa : paymentService) {
                    amList.add(convertPaymentServiceToDto(pa));
                }
            }
            return amList;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * method to get payment services by date time
     *
     * @param dateTime
     * @return List<PaymentServiceDto>
     * @throws Exception
     */
    @GetMapping(value = {"/all-by-datetime/{dateTime}", "/all-by-datetime/{dateTime}/"})
    public List<PaymentServiceDto> getPaymentServiceByDateTime(
        @PathVariable("dateTime") Timestamp dateTime,
        @RequestHeader String token) {
        authService.authenticateManager(token);
        List<PaymentServiceDto> daList = new ArrayList<>();
        try {
            List<PaymentService> paymentService = paymentServiceService.getPaymentServiceByDateTime(dateTime);
            if (paymentService.size() != 0) {
                for (PaymentService da : paymentService) {
                    daList.add(convertPaymentServiceToDto(da));
                }
            }
            return daList;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * method to get payment services by service request
     *
     * @param serviceRequest
     * @return List<PaymentServiceDto>
     * @throws Exception
     */
    @GetMapping(value = {"/all-by-service-request/{serviceRequest}", "/all-by-service-request/{serviceRequest}/"})
    public PaymentServiceDto getPaymentServiceByServiceRequest(@PathVariable("serviceRequest") ServiceRequest serviceRequest) {
        // TODO: Use IDs as arguments, not model objects
        try {
            PaymentService paymentService = paymentServiceService.getPaymentServiceByServiceRequest(serviceRequest);
            return convertPaymentServiceToDto(paymentService);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * delete a service request
     *
     * @param id
     * @return service request deleted
     */
    @DeleteMapping(value = {"/{id}", "/{id}/"})
    public PaymentServiceDto deletePaymentServiceById(@PathVariable("id") Integer id, @RequestHeader String token) {
        authService.authenticateManager(token);
        try {
            PaymentService paymentService = paymentServiceService.deletePaymentService(id);
            return convertPaymentServiceToDto(paymentService);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());

        }
    }

    /**
     * update a service request
     *
     * @param id
     * @param amount
     * @param dateTime
     * @param serviceRequest
     * @return service request updated
     */
    @PutMapping(value = {"/{id}", "/{id}/"})
    public PaymentServiceDto updatePaymentServiceById(
        @PathVariable("id") int id, 
        @RequestParam("amount") double amount, 
        @RequestParam("dateTime") Timestamp dateTime, 
        @RequestParam("serviceRequest") ServiceRequest serviceRequest,
        @RequestParam String token) {
        authService.authenticateManager(token);
        try {
            PaymentService paymentService = paymentServiceService.updatePaymentService(id, dateTime, amount, serviceRequest);
            return convertPaymentServiceToDto(paymentService);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

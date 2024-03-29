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
     * Controller method to get all payment services
     * @author Luke
     * @return A List of PaymentServiceDto
     * @throws Exception if to get payment services fail
     */
    @GetMapping(value = {"", "/"})
    public List<PaymentServiceDto> getAllPaymentService(@RequestHeader String token) throws Exception {
        authService.authenticateManager(token);
        List<PaymentServiceDto> paList = new ArrayList<>();
        for (PaymentService paymentService : paymentServiceService.getAllPaymentService()) {
            paList.add(convertPaymentServiceToDto(paymentService));
        }
        return paList;
    }

    /**
     * Controller method to create a payment service
     * @author Luke
     * @param amount the amount of the payment service
     * @param dateTime the date time of the payment service
     * @param serviceRequestId the associated service request id of the payment service
     * @return A PaymentServiceDto
     */
    @PostMapping(value = {"/", ""})
    public PaymentServiceDto createPaymentService(
        @RequestParam("serviceRequest") int serviceRequestId) {
        
        PaymentService paymentService = paymentServiceService.createPaymentService(serviceRequestId);
        return convertPaymentServiceToDto(paymentService);
        
    }

    /**
     * Controller method to get a payment service by id
     * @author Luke
     * @param id the id of the payment service
     * @return A PaymentServiceDto
    */
    @GetMapping(value = {"/{id}", "/{id}/"})
    public PaymentServiceDto getPaymentServiceById(@PathVariable("id") int id, @RequestHeader String token) {
        authService.authenticateManager(token);
        PaymentService paymentService = paymentServiceService.getPaymentServiceById(id);
        return convertPaymentServiceToDto(paymentService);
        
    }

    /**
     * Controller method method to get payment services by amount
     * @author Luke
     * @param amount the amount of the payment service
     * @return A List of PaymentServiceDto
     */
    @GetMapping(value = {"/all-by-amount/{amount}", "/all-by-amount/{amount}/"})
    public List<PaymentServiceDto> getPaymentServiceByAmount(
        @PathVariable("amount") double amount, 
        @RequestHeader String token) {
        authService.authenticateManager(token);
        List<PaymentServiceDto> amList = new ArrayList<>();
        List<PaymentService> paymentService = paymentServiceService.getPaymentServiceByAmount(amount);
        if (paymentService.size() != 0) {
            for (PaymentService pa : paymentService) {
                amList.add(convertPaymentServiceToDto(pa));
            }
        }
        return amList;
        
    }

    /**
     * Controller method to get payment services by date time
     * @author Luke
     * @param dateTime the date time of the payment service
     * @return A List PaymentServiceDto
     */
    @GetMapping(value = {"/all-by-datetime/{dateTime}", "/all-by-datetime/{dateTime}/"})
    public List<PaymentServiceDto> getPaymentServiceByDateTime(
        @PathVariable("dateTime") Timestamp dateTime,
        @RequestHeader String token) {
        authService.authenticateManager(token);
        List<PaymentServiceDto> daList = new ArrayList<>();
        List<PaymentService> paymentService = paymentServiceService.getPaymentServiceByDateTime(dateTime);
        if (paymentService.size() != 0) {
            for (PaymentService da : paymentService) {
                daList.add(convertPaymentServiceToDto(da));
            }
        }
        return daList;
        
    }

    /**
     * Controller to get payment services by service request
     * @author Luke
     * @param serviceRequestId the id of the service request
     * @return List<PaymentServiceDto>
     */
    @GetMapping(value = {"/all-by-service-request/{serviceRequestId}", "/all-by-service-request/{serviceRequestId}/"})
    public List<PaymentServiceDto> getPaymentServiceByServiceRequest(@PathVariable int serviceRequestId) {
        List<PaymentService> paymentServices = paymentServiceService.getPaymentServiceByServiceRequest(serviceRequestId);
        List<PaymentServiceDto> paymentServiceDtos = new ArrayList<>();
        for (PaymentService paymentService: paymentServices) {
            paymentServiceDtos.add(convertPaymentServiceToDto(paymentService));
        }
        return paymentServiceDtos;
    }

    /**
     * Controller method to delete a payment service by id
     * @author Luke
     * @param id the id of the payment service
     * @return the deleted PaymentServiceDto

     */
    @DeleteMapping(value = {"/{id}", "/{id}/"})
    public PaymentServiceDto deletePaymentServiceById(@PathVariable("id") Integer id, @RequestHeader String token) {
        authService.authenticateManager(token);
        PaymentService paymentService = paymentServiceService.deletePaymentService(id);
        return convertPaymentServiceToDto(paymentService);
       
    }

    /**
     * Controller method to update a payment service
     * @author Luke
     * @param id the id of the payment service
     * @param amount the amount of the payment service
     * @param dateTime the date time of the payment service
     * @param serviceRequestId the id of the associated service request of the payment service
     * @return the updated PaymentServiceDto
     */
    @PutMapping(value = {"/{id}", "/{id}/"})
    public PaymentServiceDto updatePaymentServiceById(
        @PathVariable("id") int id, 
        @RequestParam("amount") double amount, 
        @RequestParam("dateTime") Timestamp dateTime, 
        @RequestParam("serviceRequest") int serviceRequestId,
        @RequestParam String token) {
        authService.authenticateManager(token);
        PaymentService paymentService = paymentServiceService.updatePaymentService(id, dateTime, amount, serviceRequestId);
        return convertPaymentServiceToDto(paymentService);
        
    }
}

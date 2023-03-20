package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dto.PaymentServiceDto;
import ca.mcgill.ecse321.parkinglotsystem.dto.ServiceRequestDto;
import ca.mcgill.ecse321.parkinglotsystem.dto.ServicesDto;
import ca.mcgill.ecse321.parkinglotsystem.service.PaymentServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.dto.ParkingSpotTypeDto;
import ca.mcgill.ecse321.parkinglotsystem.model.*;
import ca.mcgill.ecse321.parkinglotsystem.service.ParkingSpotTypeService;

import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/api/payment_service", "/api/payment_service/"})
public class PaymentServiceController {

    @Autowired
    PaymentServiceService paymentServiceService;

    /**
     * method to get all payment services
     *
     * @return List<PaymentServiceDto>
     * @throws Exception
     */
    @GetMapping(value = {"/all", "/all/"})
    public List<PaymentServiceDto> getAllPaymentService() throws Exception {
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
    @PostMapping(value = {"/create/{id}/{amount}/{dateTime}/{serviceRequest}", "/create/{id}/{amount}/{dateTime}/{serviceRequest}/"})
    public PaymentServiceDto createPaymentService(@PathVariable("id") int id, @PathVariable("amount") double amount, @PathVariable("dateTime") Timestamp dateTime, @PathVariable("serviceRequest") ServiceRequest serviceRequest) {
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
    @GetMapping(value = {"/getById/{id}", "/getById/{id}/"})
    public PaymentServiceDto getPaymentServiceById(@PathVariable("id") int id) {
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
    @GetMapping(value = {"/getByAmount/{amount}", "/getByAmount/{amount}/"})
    public List<PaymentServiceDto> getPaymentServiceByAmount(@PathVariable("amount") double amount) {
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
    @GetMapping(value = {"/getByDateTime/{dateTime}", "/getByDateTime/{dateTime}/"})
    public List<PaymentServiceDto> getPaymentServiceByDateTime(@PathVariable("dateTime") Timestamp dateTime) {
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
    @GetMapping(value = {"/getByServiceRequest/{serviceRequest}", "/getByServiceRequest/{serviceRequest}/"})
    public List<PaymentServiceDto> getPaymentServiceByServiceRequest(@PathVariable("serviceRequest") ServiceRequest serviceRequest) {
        List<PaymentServiceDto> seList = new ArrayList<>();
        try {
            List<PaymentService> paymentService = paymentServiceService.getPaymentServiceByServiceRequest(serviceRequest);
            if (paymentService.size() != 0) {
                for (PaymentService se : paymentService) {
                    seList.add(convertPaymentServiceToDto(se));
                }
            }
            return seList;
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
    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public PaymentServiceDto deletePaymentServiceById(@PathVariable("id") Integer id) {
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
    @PutMapping(value = {"/update/{id}/{amount}/{dateTime}/{serviceRequest}", "/update/{id}/{amount}/{dateTime}/{serviceRequest}/"})
    public PaymentServiceDto updatePaymentServiceById(@PathVariable("id") int id, @PathVariable("amount") double amount, @PathVariable("dateTime") Timestamp dateTime, @PathVariable("serviceRequest") ServiceRequest serviceRequest) {
        try {
            PaymentService paymentService = paymentServiceService.updatePaymentService(id, dateTime, amount, serviceRequest);
            return convertPaymentServiceToDto(paymentService);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

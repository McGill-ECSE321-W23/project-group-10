package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;


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

import ca.mcgill.ecse321.parkinglotsystem.dto.PaymentReservationDto;
import ca.mcgill.ecse321.parkinglotsystem.model.PaymentReservation;
import ca.mcgill.ecse321.parkinglotsystem.service.AuthenticationService;
import ca.mcgill.ecse321.parkinglotsystem.service.PaymentReservationService;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;


/**
 * author Shaun Soobagrah
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/payment-reservation")
public class PaymentReservationController {
    
    @Autowired
    PaymentReservationService paymentReservationService;

    @Autowired
    AuthenticationService authService;

    /**
     * Controller method to get all payment reservations.
     * @return a list of parking spots DTO
     */
    @GetMapping(value = {"", "/"})
    public List<PaymentReservationDto> getAllPaymentReservationDtos(@RequestHeader String token) {
        authService.authenticateManager(token);
        return paymentReservationService.getAllPaymentReservation().stream().map(
            pRes -> HelperMethods.convertPaymentReservationToDto(pRes)).collect(Collectors.toList());
    }

    /**
     * Controller method to create a payment reservation.
     * @param timestamp 
     * @param amount
     * @param reservationId
     * @return
     */
    @PostMapping(value = {"", "/"})
    public PaymentReservationDto createPaymentReservationDto(
        @RequestParam("dateTime") Timestamp timestamp, 
        @RequestParam("amount") double amount, 
        @RequestParam("reservationId") int reservationId) {
        PaymentReservation paymentReservation = paymentReservationService.createPaymentReservation(timestamp, amount, reservationId); // change reservation
        return HelperMethods.convertPaymentReservationToDto(paymentReservation);

    }
    /**
     * 
     * @param paymentReservationId
     * @param token
     * @return
     */
    @DeleteMapping(value = {"/{paymentReservationId}/","/{paymentReservationId}" })
    public PaymentReservationDto deletePaymentReservationDto(
        @PathVariable("paymentReservationId") int paymentReservationId,
        @RequestHeader String token) {
        authService.authenticateManager(token);
        PaymentReservation paymentReservation = paymentReservationService.deletePaymentReservation(paymentReservationId);
        return HelperMethods.convertPaymentReservationToDto(paymentReservation);
        
    }

    /**
     * 
     * @param paymentReservationId
     * @param timestamp
     * @param amount
     * @param reservationId
     * @param token
     * @return
     */
    @PutMapping(value = {"/{paymentReservationId}", "/{paymentReservationId}/"})
    public PaymentReservationDto updatePayemPaymentReservationDto(
        @PathVariable("paymentReservationId") int paymentReservationId ,
        @RequestParam("dateTime") Timestamp timestamp, 
        @RequestParam("amount") double amount, 
        @RequestParam("reservationId") int reservationId,
        @RequestHeader String token) {
        authService.authenticateManager(token);
        PaymentReservation paymentReservation = paymentReservationService.updatePaymentReservation(paymentReservationId, timestamp, amount, reservationId);
        return HelperMethods.convertPaymentReservationToDto(paymentReservation);
    }

    /**
     * 
     * @param reservationId
     * @return
     */
    @GetMapping(value = {"/all-by-reservation/{reservationId}", "/all-by-reservation/{reservationId}/"})
    public List<PaymentReservationDto> getAllPaymentReservationDtosByReservationId(
        @PathVariable("reservationId") int reservationId) {
        return paymentReservationService.getPaymentReservationByReservation(reservationId).stream().map(
            pRes -> HelperMethods.convertPaymentReservationToDto(pRes)).collect(Collectors.toList());
    }

    /**
     * 
     * @param timestamp
     * @return
     */
    @GetMapping(value = {"/all-by-by-date/{dateTime}", "/all-by-date/{dateTime}/"})
    public List<PaymentReservationDto> getAllPaymentReservationDtosByDate(
        @PathVariable("dateTime") String timestamp) {
        return paymentReservationService.getPaymentReservationByDateTime(Timestamp.valueOf(timestamp)).stream().map(
            pRes -> HelperMethods.convertPaymentReservationToDto(pRes)).collect(Collectors.toList());
    }
    
    /**
     * 
     * @param amount
     * @return
     */
    @GetMapping(value = {"/all-by-amount/{amount}", "/all-by-amount/{amount}/"})
    public List<PaymentReservationDto> getAllPaymentReservationDtosByAmount(
        @PathVariable("amount") double amount) {
        return paymentReservationService.getPaymentReservationByAmout(amount).stream().map(
            pRes -> HelperMethods.convertPaymentReservationToDto(pRes)).collect(Collectors.toList());
    }
}

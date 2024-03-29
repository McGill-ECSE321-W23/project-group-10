package ca.mcgill.ecse321.parkinglotsystem.controller;

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
 * @author Shaun Soobagrah
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
     * @author Shaun Soobagrah
     * @return A List of PaymentReservationDto
     */
    @GetMapping(value = {"", "/"})
    public List<PaymentReservationDto> getAllPaymentReservationDtos(@RequestHeader String token) {
        authService.authenticateManager(token);
        return paymentReservationService.getAllPaymentReservation().stream().map(
            pRes -> HelperMethods.convertPaymentReservationToDto(pRes)).collect(Collectors.toList());
    }

    /**
     * Controller method to create a payment reservation.
     * @author Shaun Soobagrah
     * @param timestamp the date time of the payment reservation
     * @param amount the amount of the payment reservation
     * @param reservationId the reservation id of the payment reservation
     * @return A PaymentReservationDto
     */
    @PostMapping(value = {"", "/"})
    public PaymentReservationDto createPaymentReservationDto(
        @RequestParam("amount") double amount, 
        @RequestParam("reservationId") int reservationId) {
        PaymentReservation paymentReservation = paymentReservationService.createPaymentReservation(amount, reservationId); // change reservation
        return HelperMethods.convertPaymentReservationToDto(paymentReservation);

    }
    /**
     * Controller method to delete a payment reservation.
     * @author Shaun Soobagrah
     * @param paymentReservationId the reservation id of the payment reservation
     * @return the deleted PaymentReservationDto
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
     * Controller method to update a payment reservation
     * @author Shaun Soobagrah
     * @param paymentReservationId the payment reservation id of the payment reservation
     * @param timestamp the date time of the payment reservation
     * @param amount the amount of the payment reservation
     * @param reservationId the reservation id of the payment reservation
     * @return the updated PaymentReservationDto
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
     * Controller method to get all payment reservations
     * @author Shaun Soobagrah
     * @param reservationId the reservation id of the payment reservation
     * @return A List of PaymentReservationDto
     */
    @GetMapping(value = {"/all-by-reservation/{reservationId}", "/all-by-reservation/{reservationId}/"})
    public List<PaymentReservationDto> getAllPaymentReservationDtosByReservationId(
        @PathVariable("reservationId") int reservationId) {
        return paymentReservationService.getPaymentReservationByReservation(reservationId).stream().map(
            pRes -> HelperMethods.convertPaymentReservationToDto(pRes)).collect(Collectors.toList());
    }

    /**
     * Controller method to get payment reservations by date
     * @author Shaun Soobagrah
     * @param timestamp the date time of the payment reservation
     * @return A List of PaymentReservationDto
     */
    @GetMapping(value = {"/all-by-by-date/{dateTime}", "/all-by-date/{dateTime}/"})
    public List<PaymentReservationDto> getAllPaymentReservationDtosByDate(
        @PathVariable("dateTime") String timestamp) {
        return paymentReservationService.getPaymentReservationByDateTime(Timestamp.valueOf(timestamp)).stream().map(
            pRes -> HelperMethods.convertPaymentReservationToDto(pRes)).collect(Collectors.toList());
    }
    
    /**
     * Controller method to get payment reservations by amount
     * @author Shaun Soobagrah
     * @param amount the amount of the payment reservation
     * @return A List of PaymentReservationDto
     */
    @GetMapping(value = {"/all-by-amount/{amount}", "/all-by-amount/{amount}/"})
    public List<PaymentReservationDto> getAllPaymentReservationDtosByAmount(
        @PathVariable("amount") double amount) {
        return paymentReservationService.getPaymentReservationByAmout(amount).stream().map(
            pRes -> HelperMethods.convertPaymentReservationToDto(pRes)).collect(Collectors.toList());
    }
}

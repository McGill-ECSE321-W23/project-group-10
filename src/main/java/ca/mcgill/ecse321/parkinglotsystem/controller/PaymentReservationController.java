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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.dto.PaymentReservationDto;
import ca.mcgill.ecse321.parkinglotsystem.model.PaymentReservation;
import ca.mcgill.ecse321.parkinglotsystem.service.PaymentReservationService;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;


/**
 * author Shaun Soobagrah
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/api/payment-reservation", "/api/payment-reservation/"})
public class PaymentReservationController {
    
    @Autowired
    PaymentReservationService paymentReservationService;

    @GetMapping(value = {"", "/"})
    public List<PaymentReservationDto> getAllPaymentReservationDtos() {
        return paymentReservationService.getAllPaymentReservation().stream().map(
            pRes -> HelperMethods.convertPaymentReservationToDto(pRes)).collect(Collectors.toList());
    }


    @PostMapping(value = {"/{dateTime}/{amount}/{reservationId}", "/{dateTime}/{amount}/{reservationId}/"})
    public PaymentReservationDto createPaymentReservationDto(@PathVariable("dateTime") Timestamp timestamp, @PathVariable(
        "amount") double amount, @PathVariable("reservationId") int reservationId) {

        // String time = "2018-09-01 09:01:15"; //change this
        PaymentReservation paymentReservation = paymentReservationService.createPaymentReservation(timestamp, amount, reservationId); // change reservation
        return HelperMethods.convertPaymentReservationToDto(paymentReservation);

    }
    @DeleteMapping(value = {"/{paymentReservationId}/","/{paymentReservationId}" })
    public PaymentReservationDto deletePaymentReservationDto(@PathVariable("paymentReservationId") int paymentReservationId) {
        
        PaymentReservation paymentReservation = paymentReservationService.deletePaymentReservation(paymentReservationId);
        return HelperMethods.convertPaymentReservationToDto(paymentReservation);
        
    }

    @PutMapping(value = {"/{paymentReservationId}/{dateTime}/{amount}/{reservationId}", "/{dateTime}/{amount}/{reservationId}/"})
    public PaymentReservationDto updatePayemPaymentReservationDto(@PathVariable("paymentReservationId") int paymentReservationId ,
    @PathVariable("dateTime") Timestamp timestamp, @PathVariable("amount") double amount, @PathVariable("reservationId") int reservationId) {

        PaymentReservation paymentReservation = paymentReservationService.updatePaymentReservation(paymentReservationId, timestamp, amount, reservationId);
        return HelperMethods.convertPaymentReservationToDto(paymentReservation);
    }


    @GetMapping(value = {"/by-reservation-id/{reservationId}", "/by-reservation-id/{reservationId}/"})
    public List<PaymentReservationDto> getAllPaymentReservationDtosByReservationId(@PathVariable("reservationId") int reservationId) {
        return paymentReservationService.getPaymentReservationByReservation(reservationId).stream().map(
            pRes -> HelperMethods.convertPaymentReservationToDto(pRes)).collect(Collectors.toList());
    }
}

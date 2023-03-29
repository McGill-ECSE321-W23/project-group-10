package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.PaymentReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.PaymentReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

/*
 * author Shaun Soobagrah
 */
@Service
public class PaymentReservationService {

    @Autowired
    PaymentReservationRepository paymentReservationRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Transactional
    public List<PaymentReservation> getAllPaymentReservation() {
        Iterable<PaymentReservation> pIterable = paymentReservationRepository.findAll();
        return HelperMethods.toList(pIterable);
    }
    
    @Transactional
    public PaymentReservation createPaymentReservation(Timestamp dateTime, double amount, int reservationId ) {
        
        //input validation
        if (dateTime == null) {
            throw new CustomException("no date and time entered! ", HttpStatus.BAD_REQUEST);         
        }
        if (amount < 0) {
            throw new CustomException("amount should be greater than 0! ", HttpStatus.BAD_REQUEST);
        }
        PaymentReservation paymentReservation = new PaymentReservation();
        Reservation reservation = reservationRepository.findReservationById(reservationId);
        if (reservation == null){
            throw new CustomException("Reservation is not found. ", HttpStatus.NOT_FOUND);
        }

        paymentReservation.setAmount(amount);
        paymentReservation.setDateTime(dateTime);
        paymentReservation.setReservation(reservation);
        paymentReservationRepository.save(paymentReservation);
        
        
        return paymentReservation;
    }

    @Transactional
    public PaymentReservation deletePaymentReservation(int paymentId) {
        String error = "";
        if (paymentId < 1) {
            throw new CustomException("Invalid payment id! ", HttpStatus.NOT_FOUND);
        }
        if (paymentReservationRepository.findPaymentReservationById(paymentId) == null){
            throw new CustomException("No payment with that id exists! ", HttpStatus.NOT_FOUND);
        }

        PaymentReservation paymentReservation = paymentReservationRepository.findPaymentReservationById(paymentId);
        paymentReservationRepository.delete(paymentReservation);
        return paymentReservation;
        
     }
     @Transactional
     public PaymentReservation updatePaymentReservation(int paymentResId, Timestamp dateTime, double amount, int reservationId ) {

        //input validation
        String error = "";
        if (dateTime == null) {
            throw new CustomException("No date and time entered! ", HttpStatus.BAD_REQUEST);
        }
        if (amount < 0) {
            throw new CustomException("Amount should be greater than 0! ", HttpStatus.BAD_REQUEST);
        }

        if (error.length() > 0) {
			
        }

        Reservation reservation = reservationRepository.findReservationById(reservationId);
        if (reservation == null){
            throw new CustomException("Reservation not found. ", HttpStatus.NOT_FOUND);
        }
        PaymentReservation paymentReservation = paymentReservationRepository.findPaymentReservationById(paymentResId);
        if (paymentReservation == null){
            throw new CustomException("Payment reservation not found. ", HttpStatus.NOT_FOUND);
        }

        paymentReservation.setAmount(amount);
        paymentReservation.setDateTime(dateTime);
        paymentReservation.setReservation(reservation);
        paymentReservationRepository.save(paymentReservation);
        return paymentReservation;
     }

     @Transactional
     public List<PaymentReservation> getPaymentReservationByReservation(int reservationId) {
        //input validation
        if (reservationId <= 0) {
            throw new CustomException("reservation id should be greater than 0! ", HttpStatus.BAD_REQUEST);
        }
        Reservation reservation = reservationRepository.findReservationById(reservationId);
        if (reservation == null){
            throw new CustomException("Reservation not found. ", HttpStatus.NOT_FOUND);
        }

        List<PaymentReservation> paymentReservations = paymentReservationRepository.findPaymentReservationByReservation(reservation);
        return paymentReservations;
     }

     @Transactional
     public List<PaymentReservation> getPaymentReservationByDateTime(Timestamp dateTime) {

        if (dateTime == null) {
            throw new CustomException("Invalid Date and time! ", HttpStatus.BAD_REQUEST);
        }
       return paymentReservationRepository.findPaymentReservationByDateTime(dateTime);

     }
     
     @Transactional
     public List<PaymentReservation> getPaymentReservationByAmout(double amount ) {

        if (amount < 0) {
            throw new CustomException("Invalid amount! ", HttpStatus.BAD_REQUEST);
        }
       return paymentReservationRepository.findPaymentReservationByAmount(amount);

     }
  
}

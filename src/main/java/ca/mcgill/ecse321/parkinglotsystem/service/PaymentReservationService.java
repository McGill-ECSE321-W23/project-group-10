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
        String error = "";
        if (dateTime == null) {
            error += "no date and time entered! ";
        }
        if (amount < 0) {
            error += "amount should be greater than 0! ";
        }

        if (error.length() > 0) {
			throw new CustomException(error, HttpStatus.BAD_REQUEST);
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
        String error ="";
        if (paymentId < 1) {
            error += "Invalid payment id! ";
        }
        if (paymentReservationRepository.findPaymentReservationById(paymentId) == null){
            error += "No payment with that id exists! ";
        }
        if (error.length() > 0) {
			throw new CustomException(error, HttpStatus.NOT_FOUND);
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
            error += "No date and time entered! ";
        }
        if (amount < 0) {
            error += "Amount should be greater than 0! ";
        }

        if (error.length() > 0) {
			throw new CustomException(error, HttpStatus.BAD_REQUEST);
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
        String error = "";
        if (reservationId <= 0) {
            error += "reservation id should be greater than 0! ";
        }

        if (error.length() > 0) {
			throw new CustomException(error, HttpStatus.BAD_REQUEST);
        }

        Reservation reservation = reservationRepository.findReservationById(reservationId);
        if (reservation == null){
            throw new CustomException("Reservation not found. ", HttpStatus.NOT_FOUND);
        }

        List<PaymentReservation> paymentReservations = paymentReservationRepository.findPaymentReservationByReservation(reservation);

        return paymentReservations;
     }

     // write method for find by date and find by amount
  
}

package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.model.PaymentReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;

import org.springframework.data.repository.CrudRepository;

public interface PaymentReservationRepository extends CrudRepository<PaymentReservation, Integer> {

    //find a payment reservation by id
    PaymentReservation findPaymentReservationById(int id);

    //find payments for reservation by reservation
    List<PaymentReservation> findPaymentReservationByReservation(Reservation reservation);
}

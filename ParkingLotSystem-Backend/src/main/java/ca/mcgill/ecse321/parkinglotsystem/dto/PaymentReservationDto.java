package ca.mcgill.ecse321.parkinglotsystem.dto;

import java.sql.Timestamp;

import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;

/**
 * author Shaun Soobagrah
 */
public class PaymentReservationDto {
    
    //attributes
    private int id;
    private Timestamp dateTime;
    private double amount;
    private Reservation reservation;

    //constructor
    public PaymentReservationDto() {}

    public PaymentReservationDto(int id, Timestamp dateTime, double amount, Reservation reservation ){
        this.id = id;
        this.dateTime = dateTime;
        this.amount = amount;
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Timestamp getDateTime() {
        return dateTime;
    }
    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public Reservation getReservation() {
        return reservation;
    }
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}

package ca.mcgill.ecse321.parkinglotsystem.model;

import javax.persistence.ManyToOne;

public class PaymentReservation extends Payment {
    private Reservation reservation;

    @ManyToOne(optional = false)
    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}

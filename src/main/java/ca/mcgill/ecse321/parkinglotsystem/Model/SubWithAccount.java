package ca.mcgill.ecse321.parkinglotsystem.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("sub_account")
public class SubWithAccount extends Reservation {
    private int nbrMonths;
    private MonthlyCustomer customer;

    public int getNbrMonths() {
        return nbrMonths;
    }
    public void setNbrMonths(int nbrMonths) {
        this.nbrMonths = nbrMonths;
    }
    public MonthlyCustomer getCustomer() {
        return customer;
    }
    public void setCustomer(MonthlyCustomer customer) {
        this.customer = customer;
    }
}

package ca.mcgill.ecse321.parkinglotsystem.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("account")
public class ServiceReqWithAccount extends ServiceRequest {
    private MonthlyCustomer customer;

    @ManyToOne(optional = false)
    public MonthlyCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(MonthlyCustomer customer) {
        this.customer = customer;
    }
}

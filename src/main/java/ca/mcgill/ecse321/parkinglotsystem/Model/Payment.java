package ca.mcgill.ecse321.parkinglotsystem.model;

import java.sql.Timestamp;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Payment{
    private int id;
    private Timestamp dateTime;
    private double amount;

    @Id
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
}

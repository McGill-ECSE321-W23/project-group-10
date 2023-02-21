package ca.mcgill.ecse321.parkinglotsystem.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ParkingSpotType {
    private String name;
    private double fee;

    @Id
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getFee() {
        return fee;
    }
    public void setFee(double fee) {
        this.fee = fee;
    }
}

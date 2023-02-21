package ca.mcgill.ecse321.parkinglotsystem.Model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("single")
public class SingleReservation extends Reservation {
    private String licenseNumber;
    private int parkingTime;
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    public int getParkingTime() {
        return parkingTime;
    }
    public void setParkingTime(int parkingTime) {
        this.parkingTime = parkingTime;
    }
}

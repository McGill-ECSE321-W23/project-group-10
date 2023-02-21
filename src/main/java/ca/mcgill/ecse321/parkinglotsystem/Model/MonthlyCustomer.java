package ca.mcgill.ecse321.parkinglotsystem.Model;

import javax.persistence.Entity;

@Entity
public class MonthlyCustomer extends Person{
    private String licenseNumber;

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}

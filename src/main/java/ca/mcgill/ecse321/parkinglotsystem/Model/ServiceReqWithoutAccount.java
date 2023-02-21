package ca.mcgill.ecse321.parkinglotsystem.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("no_account")
public class ServiceReqWithoutAccount extends ServiceRequest {
    private String licenseNumber;

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}

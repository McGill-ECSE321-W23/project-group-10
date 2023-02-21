package ca.mcgill.ecse321.parkinglotsystem.Model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("sub_no_account")
public class SubWithoutAccount extends Reservation {
    private String licenseNumber;
    private int nbrMonths;

    public String getLicenseNumber() {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    public int getNbrMonths() {
        return nbrMonths;
    }
    public void setNbrMonths(int nbrMonths) {
        this.nbrMonths = nbrMonths;
    }
}

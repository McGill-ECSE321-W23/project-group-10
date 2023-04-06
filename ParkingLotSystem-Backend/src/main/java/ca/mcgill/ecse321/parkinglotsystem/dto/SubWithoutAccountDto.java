package ca.mcgill.ecse321.parkinglotsystem.dto;

import java.sql.Date;

public class SubWithoutAccountDto extends ReservationDto {

    private String licenseNumber;
    private int nbrMonths;

    public SubWithoutAccountDto(){

    }

    public SubWithoutAccountDto(int reservationID, Date date, String licenseNumber, int nbrMonths, ParkingSpotDto parkingSpotDto){
        super(reservationID, date, parkingSpotDto);
        this.licenseNumber = licenseNumber;
        this.nbrMonths = nbrMonths;
    }

    public String getLicenseNumber () {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {

        this.licenseNumber = licenseNumber;

    }

    public int getNbrMonths () {
        return nbrMonths;
    }

    public void setNbrMonths (int nbrMonths) {
        this.nbrMonths = nbrMonths;
    }
    
}
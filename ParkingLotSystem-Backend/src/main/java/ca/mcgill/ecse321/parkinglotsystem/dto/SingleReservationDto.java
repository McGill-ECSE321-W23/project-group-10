package ca.mcgill.ecse321.parkinglotsystem.dto;

import java.sql.Date;

public class SingleReservationDto extends ReservationDto {

    private String licenseNumber;
    private int parkingTime;

    public SingleReservationDto() {

    }

    public SingleReservationDto(int reservationId, Date date, String licenseNumber, int parkingTime, ParkingSpotDto parkingSpotDto){

        super(reservationId, date, parkingSpotDto);
        this.licenseNumber = licenseNumber;
        this.parkingTime = parkingTime;
    }

    public String getLicenseNumber() {

        return licenseNumber;

    }

    public void setLicenseNumber(String licenseNumber){

        this.licenseNumber = licenseNumber;

    }

    public int getParkingTime() {

        return parkingTime;

    }

    public void setParkingTime(int parkingTime){

        this.parkingTime = parkingTime;
    }
    
}

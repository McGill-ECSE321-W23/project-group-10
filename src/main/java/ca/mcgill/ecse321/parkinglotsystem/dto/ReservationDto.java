package ca.mcgill.ecse321.parkinglotsystem.dto;

import java.sql.Date;

public class ReservationDto {

    private int reservationId;
    private Date date;
    private ParkingSpotDto parkingSpotDto;

    public ReservationDto() {

    }

    public ReservationDto(int reservationId, Date date, ParkingSpotDto parkingSpotDto) {

        this.reservationId = reservationId;
        this.date = date;
        this.parkingSpotDto = parkingSpotDto;

    }

    public int getReservationId() {
        
        return reservationId;

    }

    public Date getDate() {
        
        return date;

    }

    public ParkingSpotDto getParkingSpotDto() {

        return parkingSpotDto;

    }

    public void setReservationId(int reservationId){

        this.reservationId = reservationId;

    }

    public void setDate(Date date) {

        this.date = date;

    }
    
    public void setParkingSpotDto(ParkingSpotDto parkingSpotDto) {

        this.parkingSpotDto = parkingSpotDto;
    }



}

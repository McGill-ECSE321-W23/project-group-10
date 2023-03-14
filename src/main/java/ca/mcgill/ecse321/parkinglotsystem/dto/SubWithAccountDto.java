package ca.mcgill.ecse321.parkinglotsystem.dto;

import java.sql.Date;

import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;

public class SubWithAccountDto extends ReservationDto {
    private int nbrMonths;
    private MonthlyCustomerDto monthlyCustomerDto;

    public SubWithAccountDto() {
        super();
    }

    public SubWithAccountDto(int id, Date date, ParkingSpotDto parkingSpot, MonthlyCustomerDto monthlyCustomer) {
        super(id,date, parkingSpot);
        this.monthlyCustomerDto = monthlyCustomer;
    }

    public int getNbrMonths() {
        return nbrMonths;
    }
    public void setNbrMonths(int nbrMonths) {
        this.nbrMonths = nbrMonths;
    }
    public MonthlyCustomerDto getMonthlyCustomerDto() {
        return monthlyCustomerDto;
    }
    public void setMonthlyCustomerDto(MonthlyCustomerDto monthlyCustomerDto) {
        this.monthlyCustomerDto = monthlyCustomerDto;
    }
}

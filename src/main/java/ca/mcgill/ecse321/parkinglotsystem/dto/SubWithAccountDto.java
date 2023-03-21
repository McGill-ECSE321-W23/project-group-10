package ca.mcgill.ecse321.parkinglotsystem.dto;

import java.sql.Date;

public class SubWithAccountDto extends ReservationDto {
    private int nbrMonths;
    private MonthlyCustomerDto monthlyCustomer;

    public SubWithAccountDto() {
        super();
    }

    public SubWithAccountDto(int id, Date date, ParkingSpotDto parkingSpot, int nbrMonths, MonthlyCustomerDto monthlyCustomer) {
        super(id,date, parkingSpot);
        this.nbrMonths = nbrMonths;
        this.monthlyCustomer = monthlyCustomer;
    }

    public int getNbrMonths() {
        return nbrMonths;
    }
    public void setNbrMonths(int nbrMonths) {
        this.nbrMonths = nbrMonths;
    }
    public MonthlyCustomerDto getMonthlyCustomer() {
        return monthlyCustomer;
    }
    public void setMonthlyCustomer(MonthlyCustomerDto monthlyCustomer) {
        this.monthlyCustomer = monthlyCustomer;
    }
}

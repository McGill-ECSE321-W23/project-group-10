package ca.mcgill.ecse321.parkinglotsystem.dto;

import java.sql.Timestamp;

public class PaymentServiceDto {

    private int id;
    private Timestamp dateTime;
    private double amount;
    private ServiceRequestDto serviceRequestDto;

    public PaymentServiceDto() {

    }

    public PaymentServiceDto(ServiceRequestDto serviceRequestDto) {
        this.serviceRequestDto = serviceRequestDto;
    }

    public ServiceRequestDto getServiceRequestDto() {

        return serviceRequestDto;

    }

    public void setServiceRequestDto(ServiceRequestDto serviceRequestDto) {

        this.serviceRequestDto = serviceRequestDto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    

}

package ca.mcgill.ecse321.parkinglotsystem.dto;

import ca.mcgill.ecse321.parkinglotsystem.model.PaymentService;

public class PaymentServiceDto {

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

}

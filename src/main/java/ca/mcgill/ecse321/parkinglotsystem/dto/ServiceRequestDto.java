package ca.mcgill.ecse321.parkinglotsystem.dto;

public class ServiceRequestDto {
    private int id;
    private boolean isAssigned;
    private ServiceDto servicesDto;

    public ServiceRequestDto() {

    }

    public ServiceRequestDto(int id, boolean isAssigned, ServiceDto servicesDto) {
        this.id = id;
        this.isAssigned = isAssigned;
        this.servicesDto = servicesDto;
    }

    public int getId() {
        return id;
    }

    public boolean getIsAssigned() {
        return isAssigned;
    }

    public ServiceDto getServicesDto() {
        return servicesDto;
    }

    public void setId(int id) {
        this.id = this.id;
    }

    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned = this.isAssigned;
    }

    public void setServicesDto(ServiceDto servicesDto) {
        this.servicesDto = this.servicesDto;
    }

}

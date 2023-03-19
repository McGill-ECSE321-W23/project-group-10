package ca.mcgill.ecse321.parkinglotsystem.dto;

public class ServiceRequestDto {
    private int id;
    private boolean isAssigned;
    private ServicesDto servicesDto;

    public ServiceRequestDto() {

    }

    public ServiceRequestDto(int id, boolean isAssigned, ServicesDto servicesDto) {
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

    public ServicesDto getServicesDto() {
        return servicesDto;
    }

    public void setId(int id) {
        this.id = this.id;
    }

    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned = this.isAssigned;
    }

    public void setServicesDto(ServicesDto servicesDto) {
        this.servicesDto = this.servicesDto;
    }

}

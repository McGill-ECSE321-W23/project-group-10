package ca.mcgill.ecse321.parkinglotsystem.dto;

public class ServiceRequestDto {
    private int id;
    private boolean isAssigned;
    private ServiceDto serviceDto;

    public ServiceRequestDto(){

    }

    public ServiceRequestDto(int id, boolean isAssigned, ServiceDto serviceDto){
        this.id = id;
        this.isAssigned = isAssigned;
        this.serviceDto = serviceDto;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public boolean getIsAssigned(){
        return isAssigned;
    }

    public void setIsAssigned(boolean isAssigned){
        this.isAssigned = isAssigned;
    }

    public ServiceDto getServiceDto(){
        return serviceDto;
    }

    public void setServiceDto(ServiceDto serviceDto){
        this.serviceDto = serviceDto;
    }


}

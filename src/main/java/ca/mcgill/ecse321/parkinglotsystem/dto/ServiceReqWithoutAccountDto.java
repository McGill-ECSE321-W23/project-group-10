package ca.mcgill.ecse321.parkinglotsystem.dto;


public class ServiceReqWithoutAccountDto extends ServiceRequestDto{
    String licenseNumber;

    public ServiceReqWithoutAccountDto(){
        super();
    }

    public ServiceReqWithoutAccountDto(int id, boolean isAssigned, ServiceDto serviceDto, String licenseNumber;){
        super(id, isAssigned, serviceDto);
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber(){
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber){
        this.licenseNumber = licenseNumber;
    }



}

package ca.mcgill.ecse321.parkinglotsystem.dto;

public class MonthlyCustomerDto {
    private String email;
    private String name;
    private String phone;
    private String licenseNumber;

    
    public MonthlyCustomerDto(){};


    public MonthlyCustomerDto(String email,String name,String phone,String licenseNumber){
        this.email=email;
        this.name=name;
        this.phone=phone;
        this.licenseNumber = licenseNumber;
    }


    public String getEmail(){
        return email;
    }


    public void setEmail(String email){
        this.email=email;
    }


    public String getName(){
        return name;
    }


    public void setName(String name){
        this.name=name;
    }


    public String getPhone(){
        return phone;
    }


    public void setPhone(String phone){
        this.phone=phone;
    }


    public String getLicenseNumber(){
        return licenseNumber;
    }


    public void setLicenseNumber(String licenseNumber){
        this.licenseNumber=licenseNumber;
    }

}

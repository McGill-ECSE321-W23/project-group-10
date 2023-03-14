package ca.mcgill.ecse321.parkinglotsystem.dto;

public class ManagerDto {
    private String email;
    private String name;
    private String password;
    private String phone;

    
    public ManagerDto(){};


    public ManagerDto(String name,String email,String phone,String password){
        this.email=email;
        this.name=name;
        this.phone=phone;
        this.password=password;
    }


    public String getEmail(){
        return email;
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


    public String getPassword(){
        return password;
    }


    public void setPassword(String password){
        this.password=password;
    }

}

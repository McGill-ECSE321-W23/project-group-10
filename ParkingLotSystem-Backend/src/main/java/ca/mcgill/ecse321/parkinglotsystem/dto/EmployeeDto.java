package ca.mcgill.ecse321.parkinglotsystem.dto;

public class EmployeeDto {
    private String email;
    private String name;
    private String phone;


    public EmployeeDto() {
    }


    public EmployeeDto(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

}

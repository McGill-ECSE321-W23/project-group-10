package ca.mcgill.ecse321.parkinglotsystem.dto;


public class ServiceReqWithAccountDto extends ServiceRequestDto{
    private MonthlyCustomerDto monthlyCustomerDto;


    public ServiceReqWithAccountDto(){
        super();
    }

    public ServiceReqWithAccountDto(int id, boolean isAssigned, ServiceDto serviceDto, MonthlyCustomerDto monthlyCustomerDto){
        super(id, isAssigned, serviceDto);
        this.monthlyCustomerDto = monthlyCustomerDto;
    }

    public MonthlyCustomerDto getMonthlyCustomerDto(){
        return monthlyCustomerDto;
    }

    public void setMonthlyCustomerDto(MonthlyCustomerDto monthlyCustomerDto){
        this.monthlyCustomerDto = monthlyCustomerDto;
    }



}

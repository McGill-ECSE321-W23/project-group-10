package ca.mcgill.ecse321.parkinglotsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;

import java.util.List;

public interface MonthlyCustomerRepository extends CrudRepository<MonthlyCustomer, String> {

    //find a monthly customer by email
    MonthlyCustomer findMonthlyCustomerByEmail(String email);

    //find monthly customers by name
    List<MonthlyCustomer> findMonthlyCustomerByName(String name);

    //find monthly customers by phone number
    List<MonthlyCustomer> findMonthlyCustomerByPhone(String phone);

    //find monthly customers by password
    List<MonthlyCustomer> findMonthlyCustomerByPassword(String password);

    //find monthly customers by password
    List<MonthlyCustomer> findMonthlyCustomerByLicenseNumber(String licenseNumber);

    //find monthly customers by token
    List<MonthlyCustomer> findMonthlyCustomerByToken(String token);

}

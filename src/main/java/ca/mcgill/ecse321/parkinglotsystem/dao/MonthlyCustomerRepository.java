package ca.mcgill.ecse321.parkinglotsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;

import java.util.List;

public interface MonthlyCustomerRepository extends CrudRepository<MonthlyCustomer, String> {

    MonthlyCustomer findMonthlyCustomerByEmail(String email);

    List<MonthlyCustomer> findMonthlyCustomerByName(String name);

    List<MonthlyCustomer> findMonthlyCustomerByPhone(String phone);

    List<MonthlyCustomer> findMonthlyCustomerByPassword(String password);

}

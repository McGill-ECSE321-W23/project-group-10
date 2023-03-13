package ca.mcgill.ecse321.parkinglotsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    //find an employee by email
    Employee findEmployeeByEmail(String email);

    //find employees by name
    List<Employee> findEmployeeByName(String name);

    //find employees by phone number
    List<Employee> findEmployeeByPhone(String phone);

    //find employees by password
    List<Employee> findEmployeeByPassword(String password);

}
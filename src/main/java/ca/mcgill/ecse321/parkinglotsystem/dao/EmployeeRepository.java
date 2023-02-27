package ca.mcgill.ecse321.parkinglotsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, String>{

    Employee findEmployeeById(String id);
    List<Employee> findEmployeeByName(String name);
	List<Employee> findEmployeeByPhone(String phone);
	List<Employee> findEmployeeByPassword(String password);

}
package ca.mcgill.ecse321.parkinglotsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Employee;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import org.springframework.http.HttpStatus;

@Service
public class EmployeeService {
    
    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * method to create an Employee
     * @param email
     * @param name
     * @param phone
     * @param password
     * @return newly created Employee or exception
     */
    @Transactional
     public Employee createEmployee(String email,String name,String phone,String password){
        String error="";
        error=error+HelperMethods.verifyEmail(email)+HelperMethods.verifyName(name)+HelperMethods.verifyPhone(phone)
        +HelperMethods.verifyPassword(password);

        List<Employee> existing=getAllEmployees();
        for(int i=0;i<existing.size();i++){
            if(existing.get(i).getEmail().trim().equals(email.trim())){
                error=error+"Cannot have the same email as an existing account! ";
            }
        }

        if(error.length()>0){
            throw new CustomException(error,HttpStatus.BAD_REQUEST);
        }
        Employee employee=new Employee();
        employee.setEmail(email);
        employee.setName(name);
        employee.setPassword(password);
        employee.setPhone(phone);
        employeeRepository.save(employee);
        return employee;
     }

    /**
     * method to get an Employee by email
     * @param email
     * @return Employee or exception
     */
     @Transactional
     public Employee getEmployeeByEmail(String email){
        Employee em = employeeRepository.findEmployeeByEmail(email);
        if(em == null) {
            throw new CustomException("Invalid employee email! ", HttpStatus.BAD_REQUEST);
        }
        return em;
     }

    /**
     * method to get Employees by name
     * @param name
     * @return List<Employee> or null
     */
     @Transactional
     public List<Employee> getEmployeeByName(String name){
        return employeeRepository.findEmployeeByName(name);
     }

    /**
     * method to get Employees by name
     * @param name
     * @return List<Employee> or null
     */
     @Transactional
     public List<Employee> getEmployeeByPhone(String phone){
        return employeeRepository.findEmployeeByPhone(phone);
     }

    /**
     * method to get all Employees
     * @return List<Employee> or null
     */
     @Transactional
     public List<Employee> getAllEmployees(){
        Iterable<Employee> mIterable=employeeRepository.findAll();
        return HelperMethods.toList(mIterable);
     }

    /**
     * method to delete an Employee
     * @param email
     * @return newly deleted Employee or exception
     */
     @Transactional
     public Employee deleteEmployeeByEmail(String email){
        String error="";
        Employee em=employeeRepository.findEmployeeByEmail(email);
        if(em==null){
            error=error+"No employee with that email was found!";
        }
        if(error.length()>0){
            throw new CustomException(error,HttpStatus.BAD_REQUEST);
        }else{
            employeeRepository.delete(em);
            return em;
        }
     }


    /**
     * method to update an Employee
     * @param email
     * @param name
     * @param phone
     * @param password
     * @return newly updated Employee or exception
     */
     @Transactional
     public Employee updateEmployee(String email,String name,String phone,String password){
        String error="";
        Employee em=employeeRepository.findEmployeeByEmail(email);
        if(employeeRepository.findEmployeeByEmail(email)==null){
            error=error+"No employee with that email exists!";
        }else{
            error=error+HelperMethods.verifyEmail(email)+HelperMethods.verifyName(name)+HelperMethods.verifyPhone(phone)
            +HelperMethods.verifyPassword(password);
        }
        if(error.length()>0){
            throw new CustomException(error,HttpStatus.BAD_REQUEST);
        }else{
            em.setName(name);
            em.setPhone(phone);
            em.setPassword(password);
            return em;
        }
     }
}
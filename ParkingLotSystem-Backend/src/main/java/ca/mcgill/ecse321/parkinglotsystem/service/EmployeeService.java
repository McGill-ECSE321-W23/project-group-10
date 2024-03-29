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
     * @author Louis
     * @param email the email of the employee
     * @param name the name of the employee
     * @param phone the phone number of the employee
     * @param password the password of the employee
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
     * @author Louis
     * @param email the email of the employee
     * @return A Employee
     * @throws CustomException if to get employee fail
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
     * @author Louis
     * @param name the name of the employee
     * @return A List of Employee or null
     */
     @Transactional
     public List<Employee> getEmployeeByName(String name){
        return employeeRepository.findEmployeeByName(name);
     }

    /**
     * method to get Employees by phone number
     * @author Louis
     * @param phone the phone number of the employee
     * @return A List of Employee or null
     */
     @Transactional
     public List<Employee> getEmployeeByPhone(String phone){
        return employeeRepository.findEmployeeByPhone(phone);
     }

    /**
     * method to get all Employees
     * @author Louis
     * @return A List of Employee or null
     */
     @Transactional
     public List<Employee> getAllEmployees(){
        Iterable<Employee> mIterable=employeeRepository.findAll();
        return HelperMethods.toList(mIterable);
     }

    /**
     * method to delete an Employee
     * @author Louis
     * @param email the email of the employee
     * @return  deleted Employee
     * @throws CustomException if to delete the employee fail
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
     * @author Louis
     * @param email the email of the employee
     * @param name the name of the employee
     * @param phone the phone number of the employee
     * @param password the password of the employee
     * @return newly updated Employee
     * @throws CustomException if to update the employee fail
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

     /**
      * method to verify password of an employee
      *@author Shaun
      * @param email
      * @param password
      * @return
      */
     @Transactional
     public boolean verifyPassword(String email,String password){
        Employee em=employeeRepository.findEmployeeByEmail(email);
        if(em==null){
            throw new CustomException("Invalid employee email! ", HttpStatus.BAD_REQUEST);
        }else{
            return em.getPassword().equals(password);
        }
     }

     
}
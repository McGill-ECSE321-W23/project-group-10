package ca.mcgill.ecse321.parkinglotsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.PaymentServiceRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.PaymentService;
import ca.mcgill.ecse321.parkinglotsystem.model.Employee;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;

@Service
public class EmployeeService {
    
    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * method to a create employee
     * @param email
     * @param name
     * @param phone
     * @param password
     * @return newly created Employee or null
     */
    @Transactional
     public Employee createEmployee(String email,String name,String phone,String password){
        String error="";
        error=error+HelperMethods.verifyEmail(email)+HelperMethods.verifyName(name)+HelperMethods.verifyPhone(phone)
        +HelperMethods.verifyPassword(password);
        if(error.length()>0){
            throw new IllegalArgumentException(error);
        }
        Employee employee=new Employee();
        employee.setEmail(email);
        employee.setName(name);
        employee.setPassword(password);
        employee.setPhone(phone);
        employeeRepository.save(employee);
        return employee;
     }


     @Transactional
     public Employee getEmployeeByEmail(String email){
        return employeeRepository.findEmployeeByEmail(email);
     }


     @Transactional
     public List<Employee> getEmployeeByName(String name){
        return employeeRepository.findEmployeeByName(name);
     }


     @Transactional
     public List<Employee> getEmployeeByPhone(String phone){
        return employeeRepository.findEmployeeByPhone(phone);
     }


     @Transactional
     public List<Employee> getEmployeeByPassword(String password){
        return employeeRepository.findEmployeeByName(password);
     }


     @Transactional
     public List<Employee> getAllEmployees(){
        Iterable<Employee> mIterable=employeeRepository.findAll();
        return HelperMethods.toList(mIterable);
     }


     @Transactional
     public Employee deleteEmployeeByEmail(String email){
        String error="";
        Employee em=employeeRepository.findEmployeeByEmail(email);
        if(em==null){
            error=error+"No employee with that email was found!";
        }
        if(error.length()>0){
            throw new IllegalArgumentException(error);
        }else{
            employeeRepository.delete(em);
            return em;
        }
     }

     @Transactional
     public Employee updateEmployee(String email,String name,String phone,String password){
        String error="";

        Employee em=employeeRepository.findEmployeeByEmail(email);
        if(employeeRepository.findEmployeeByEmail(email)==null){
            error=error+"No employee with that email exists!";
        }
        if(error.length()>0){
            throw new IllegalArgumentException(error);
        }else{
            em.setName(name);
            em.setPhone(phone);
            em.setPassword(password);
            return em;
        }
     }
}
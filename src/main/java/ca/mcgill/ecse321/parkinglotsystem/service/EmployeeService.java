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
        if(name==null || name.trim().length()==0){
            error=error+"Employee name cannot be empty!";
        }
        if((email==null || email.trim().length()==0)){
            error=error+"Employee email cannot be empty!";
        }else if(email.indexOf("@")==-1){
            error=error+"Employee email must contain \"@\"!";
        }
        if(phone.trim().length()!=10){
            error=error+"Employee phone must have exactlty 10 digits!";
        }
        if(phone.trim().matches("\\d+")==false){
            error=error+"Employee phone cannot have non-number digits!";
        }
        if(password.trim().length()<8){
            error=error+"Employee password cannot be shorter than 8 digits!";
        }
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
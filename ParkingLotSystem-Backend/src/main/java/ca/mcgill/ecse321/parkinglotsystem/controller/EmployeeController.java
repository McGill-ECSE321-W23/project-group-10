package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import ca.mcgill.ecse321.parkinglotsystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dto.EmployeeDto;
import ca.mcgill.ecse321.parkinglotsystem.model.Employee;
import ca.mcgill.ecse321.parkinglotsystem.service.*;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/employee")

public class EmployeeController {


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    /**
     * Controller method to get all employees
     * @author Louis
     * @return A List of EmployeeDto or null
     */
    @GetMapping(value={"/",""})
    public List<EmployeeDto> getAllEmployeeDtos(){
        return employeeService.getAllEmployees().stream().map(ma -> HelperMethods.convertEmployeeToDto(ma)).collect(Collectors.toList());
    }

    /**
     * Controller method to create an employee
     * @author Louis
     * @param email the email of the employee
     * @param name the name of the employee
     * @param phone the phone number of the employee
     * @param password the password of the employee
     * @return newly created EmployeeDto or exception
     */
    @PostMapping(value = {"/{email}", "/{email}/"})
    public EmployeeDto createEmployeeDto(@PathVariable("email") String email, 
        @RequestParam("name") String name,
        @RequestParam("phone") String phone,
        @RequestParam("password") String password){
        Employee em = employeeService.createEmployee(email,name,phone,password);
        return HelperMethods.convertEmployeeToDto(em);                                  
    }

    /**
     * Controller method to get employees by name
     * @author Louis
     * @param name the name of the employee
     * @return A List of EmployeeDto or null
     */
    @GetMapping(value = {"/all-by-name/{name}", "/all-by-name/{name}/"})
    public List<EmployeeDto> getEmployeeDtoByName(@PathVariable("name") String name) {
        List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
        List<Employee> ems = employeeService.getEmployeeByName(name);
        if (ems.size() != 0){
            for (Employee em: ems){
                employeeDtos.add(HelperMethods.convertEmployeeToDto(em));
            }
        }     
        return employeeDtos;
    }

    /**
     * Controller method to get employees by phone
     * @author Louis
     * @param phone the phone number of the employee
     * @return A List of EmployeeDto or null
     */
    @GetMapping(value = {"/all-by-phone/{phone}", "/all-by-phone/{phone}/"})
    public List<EmployeeDto> getEmployeeDtoByPhone(@PathVariable("phone") String phone) {
        List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
        List<Employee> ems = employeeService.getEmployeeByPhone(phone);
        if (ems.size() != 0){
            for (Employee em: ems){
                employeeDtos.add(HelperMethods.convertEmployeeToDto(em));
            }
        }     
        return employeeDtos;
    }

    /**
     * Controller method to get an employee by email
     * @author Louis
     * @param email the email of the employee
     * @return a EmployeeDto or exception
     */
    @GetMapping(value = {"/{email}", "/{email}/"})
    public EmployeeDto getEmployeeDtoByEmail(@PathVariable("email") String email) {
        Employee em = employeeService.getEmployeeByEmail(email);
        if(em==null){
            return null;
        }
        return HelperMethods.convertEmployeeToDto(em); 
    }

    /**
     * Controller method to delete an employee
     * @author Louis
     * @param email the email of the employee
     * @return newly deleted EmployeeDto or exception
     */
    @DeleteMapping(value = {"/{email}","/{email}/"})
    public EmployeeDto deleteEmployeeDtoByEmail(@PathVariable("email") String email) {
        Employee em = employeeService.deleteEmployeeByEmail(email);
        return HelperMethods.convertEmployeeToDto(em);
    }

     /**
     * Controller method to update an employee
     * @author Louis
     * @param email the email of the employee
     * @param name the name of the employee
     * @param phone the phone number of the employee
     * @param password the password of the employee
     * @return newly updated EmployeeDto or exception
     */
    @PutMapping(value ={"/{email}", "/{email}/"})
    public EmployeeDto updateEmployeeDto(@PathVariable("email") String email, 
        @RequestParam("name") String name,
        @RequestParam("phone") String phone,
        @RequestParam("password") String password) {
        Employee em = employeeService.updateEmployee(email, name, phone, password);
        return HelperMethods.convertEmployeeToDto(em);
    } 


    /**
     * Controller method to verify an employee's password
     * * @author Shaun
     * @param email the email of the employee
     * @param password the password of the employee
     * @return true if password is correct, false otherwise
     */
    @GetMapping(value = {"/verify", "/verify/"})
    public boolean verifyEmployee(@RequestParam("email") String email, @RequestParam("password") String password){
        return employeeService.verifyPassword(email, password);
    }

}
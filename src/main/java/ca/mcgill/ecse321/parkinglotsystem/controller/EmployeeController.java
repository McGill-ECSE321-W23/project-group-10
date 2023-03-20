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
@RequestMapping(value = {"/api/employee", "/api/employee/"})

public class EmployeeController {


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;


    @GetMapping(value={"/all/","/all"})
    public List<EmployeeDto> getAllEmployeeDtos(){
        return employeeService.getAllEmployees().stream().map(ma -> HelperMethods.convertEmployeeToDto(ma)).collect(Collectors.toList());
    }


    @PostMapping(value = {"/create/{email}", "/create/{email}/"})
    public EmployeeDto createEmployeeDto(@PathVariable("email") String email, 
        @RequestParam("name") String name,
        @RequestParam("phone") String phone,
        @RequestParam("password") String password){
        Employee em = employeeService.createEmployee(email,name,phone,password);
        return HelperMethods.convertEmployeeToDto(em);                                  
    }


    @GetMapping(value = {"/getByName/{name}", "/getByName/{name}/"})
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


    @GetMapping(value = {"/getByPhone/{phone}", "/getByPhone/{phone}/"})
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


    @GetMapping(value = {"/getByPassword/{password}", "/getByPassword/{password}/"})
    public List<EmployeeDto> getEmployeeDtoByPassword(@PathVariable("password") String password) {
        List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
        List<Employee> ems = employeeService.getEmployeeByPassword(password);
        if (ems.size() != 0){
            for (Employee em: ems){
                employeeDtos.add(HelperMethods.convertEmployeeToDto(em));
            }
        }     
        return employeeDtos;
    }


    @GetMapping(value = {"/getByEmail/{email}", "/getByEmail/{email}/"})
    public EmployeeDto getEmployeeDtoByEmail(@PathVariable("email") String email) {
        Employee em = employeeService.getEmployeeByEmail(email);
        if(em==null){
            return null;
        }
        return HelperMethods.convertEmployeeToDto(em); 
    }


    @DeleteMapping(value = {"/delete/{email}","/delete/{email}/"})
    public EmployeeDto deleteEmployeeDtoByEmail(@PathVariable("email") String email) {
        Employee em = employeeService.deleteEmployeeByEmail(email);
        return HelperMethods.convertEmployeeToDto(em);
    }


    @PutMapping(value ={"/update/{email}", "/update/{email}/"})
    public EmployeeDto updateEmployeeDto(@PathVariable("email") String email, 
        @RequestParam("name") String name,
        @RequestParam("phone") String phone,
        @RequestParam("password") String password) {
        Employee em = employeeService.updateEmployee(email, name, phone, password);
        return HelperMethods.convertEmployeeToDto(em);
    } 


}

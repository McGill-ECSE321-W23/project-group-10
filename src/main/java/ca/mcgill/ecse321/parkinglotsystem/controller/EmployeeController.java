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


    @PostMapping(value = {"/create/{email}/{name}/{phone}/{password}", "/create/{email}/{name}/{phone}/{password}/"})
    public EmployeeDto createEmployeeDto(@PathVariable("email") String email, 
        @PathVariable("name") String name,
        @PathVariable("phone") String phone,
        @PathVariable("password") String password){
        try {
            //ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(parkingSpotTypeName);
            Employee em = employeeService.createEmployee(email,name,phone,password);
            return HelperMethods.convertEmployeeToDto(em);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }                                   
    }


    @GetMapping(value = {"/getByName/{name}", "/getByName/{name}/"})
    public List<EmployeeDto> getEmployeeDtoByName(@PathVariable("name") String name) {
        List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
        try {
            List<Employee> ems = employeeService.getEmployeeByName(name);
            if (ems.size() != 0){
                for (Employee em: ems){
                    employeeDtos.add(HelperMethods.convertEmployeeToDto(em));
                }
            }     
            return employeeDtos;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @GetMapping(value = {"/getByPhone/{phone}", "/getByPhone/{phone}/"})
    public List<EmployeeDto> getEmployeeDtoByPhone(@PathVariable("phone") String phone) {
        List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
        try {
            List<Employee> ems = employeeService.getEmployeeByPhone(phone);
            if (ems.size() != 0){
                for (Employee em: ems){
                    employeeDtos.add(HelperMethods.convertEmployeeToDto(em));
                }
            }     
            return employeeDtos;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @GetMapping(value = {"/getByPassword/{password}", "/getByPassword/{password}/"})
    public List<EmployeeDto> getEmployeeDtoByPassword(@PathVariable("name") String password) {
        List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
        try {
            List<Employee> ems = employeeService.getEmployeeByPassword(password);
            if (ems.size() != 0){
                for (Employee em: ems){
                    employeeDtos.add(HelperMethods.convertEmployeeToDto(em));
                }
            }     
            return employeeDtos;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @GetMapping(value = {"/getByEmail/{email}", "/getByEmail/{email}/"})
    public EmployeeDto getEmployeeDtoByEmail(@PathVariable("email") String email) {
        try {
            Employee em = employeeService.getEmployeeByEmail(email);
            return HelperMethods.convertEmployeeToDto(em);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @DeleteMapping(value = {"/delete/{email}","/delete/{email}/"})
    public EmployeeDto deleteEmployeeDtoByEmail(@PathVariable("email") String email) {
        try {
            Employee em = employeeService.deleteEmployeeByEmail(email);
            return HelperMethods.convertEmployeeToDto(em);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } 
    }


    @PutMapping(value ={"/update/{email}/{name}/{phone}/{password}", "/update/{email}/{name}/{phone}/{password}/"})
    public EmployeeDto updateEmployeeDto(@PathVariable("email") String email, 
        @PathVariable("name") String name,
        @PathVariable("phone") String phone,
        @PathVariable("password") String password) {
        try {
            Employee em = employeeService.updateEmployee(email, name, phone, password);
            return HelperMethods.convertEmployeeToDto(em);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } 
    } 


}
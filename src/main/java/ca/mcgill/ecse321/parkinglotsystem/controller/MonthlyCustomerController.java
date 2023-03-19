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


import ca.mcgill.ecse321.parkinglotsystem.dao.MonthlyCustomerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dto.MonthlyCustomerDto;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.service.*;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/api/monthlyCustomer", "/api/monthlyCustomer/"})

public class MonthlyCustomerController {


    @Autowired
    MonthlyCustomerRepository monthlyCustomerRepository;

    @Autowired
    MonthlyCustomerService monthlyCustomerService;


    @GetMapping(value={"/all/","/all"})
    public List<MonthlyCustomerDto> getAllMonthlyCustomerDtos(){
        return monthlyCustomerService.getAllMonthlyCustomers().stream().map(mc -> HelperMethods.convertMonthlyCustomerToDto(mc)).collect(Collectors.toList());
    }


    @PostMapping(value = {"/create/{email}/{name}/{phone}/{password}/{licenseNumber}", "/create/{email}/{name}/{phone}/{password}/{licenseNumber}/"})
    public MonthlyCustomerDto createMonthlyCustomerDto(@PathVariable("email") String email, 
        @PathVariable("name") String name,
        @PathVariable("phone") String phone,
        @PathVariable("password") String password,
        @PathVariable("licenseNumber") String licenseNumber){
        try {
            //ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(parkingSpotTypeName);
            MonthlyCustomer mc = monthlyCustomerService.createMonthlyCustomer(email,name,phone,password,licenseNumber);
            return HelperMethods.convertMonthlyCustomerToDto(mc);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }                                   
    }


    @GetMapping(value = {"/getByName/{name}", "/getByName/{name}/"})
    public List<MonthlyCustomerDto> getMonthlyCustomerDtoByName(@PathVariable("name") String name) {
        List<MonthlyCustomerDto> monthlyCustomerDtos = new ArrayList<MonthlyCustomerDto>();
        try {
            List<MonthlyCustomer> mcs = monthlyCustomerService.getMonthlyCustomerByName(name);
            if (mcs.size() != 0){
                for (MonthlyCustomer mc: mcs){
                    monthlyCustomerDtos.add(HelperMethods.convertMonthlyCustomerToDto(mc));
                }
            }     
            return monthlyCustomerDtos;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @GetMapping(value = {"/getByPhone/{phone}", "/getByPhone/{phone}/"})
    public List<MonthlyCustomerDto> getMonthlyCustomerDtoByPhone(@PathVariable("phone") String phone) {
        List<MonthlyCustomerDto> monthlyCustomerDtos = new ArrayList<MonthlyCustomerDto>();
        try {
            List<MonthlyCustomer> mcs = monthlyCustomerService.getMonthlyCustomerByPhone(phone);
            if (mcs.size() != 0){
                for (MonthlyCustomer mc: mcs){
                    monthlyCustomerDtos.add(HelperMethods.convertMonthlyCustomerToDto(mc));
                }
            }     
            return monthlyCustomerDtos;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @GetMapping(value = {"/getByPassword/{password}", "/getByPassword/{password}/"})
    public List<MonthlyCustomerDto> getMonthlyCustomerDtoByPassword(@PathVariable("name") String password) {
        List<MonthlyCustomerDto> monthlyCustomerDtos = new ArrayList<MonthlyCustomerDto>();
        try {
            List<MonthlyCustomer> mcs = monthlyCustomerService.getMonthlyCustomerByPassword(password);
            if (mcs.size() != 0){
                for (MonthlyCustomer mc: mcs){
                    monthlyCustomerDtos.add(HelperMethods.convertMonthlyCustomerToDto(mc));
                }
            }     
            return monthlyCustomerDtos;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    
    @GetMapping(value = {"/getByLicenseNumber/{licenseNumber}", "/getByLicenseNumber/{licenseNumber}/"})
    public List<MonthlyCustomerDto> getMonthlyCustomerDtoByLicenseNumber(@PathVariable("name") String licenseNumber) {
        List<MonthlyCustomerDto> monthlyCustomerDtos = new ArrayList<MonthlyCustomerDto>();
        try {
            List<MonthlyCustomer> mcs = monthlyCustomerService.getMonthlyCustomerByLicenseNumber(licenseNumber);
            if (mcs.size() != 0){
                for (MonthlyCustomer mc: mcs){
                    monthlyCustomerDtos.add(HelperMethods.convertMonthlyCustomerToDto(mc));
                }
            }     
            return monthlyCustomerDtos;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @GetMapping(value = {"/getByEmail/{email}", "/getByEmail/{email}/"})
    public MonthlyCustomerDto getMonthlyCustomerDtoByEmail(@PathVariable("email") String email) {
        try {
            MonthlyCustomer mc = monthlyCustomerService.getMonthlyCustomerByEmail(email);
            return HelperMethods.convertMonthlyCustomerToDto(mc);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @DeleteMapping(value = {"/delete/{email}","/delete/{email}/"})
    public MonthlyCustomerDto deleteMonthlyCustomerDtoByEmail(@PathVariable("email") String email) {
        try {
            MonthlyCustomer mc = monthlyCustomerService.deleteMonthlyCustomerByEmail(email);
            return HelperMethods.convertMonthlyCustomerToDto(mc);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } 
    }


    @PutMapping(value ={"/update/{email}/{name}/{phone}/{password}/{licenseNumber}", "/update/{email}/{name}/{phone}/{password}/{licenseNumber}/"})
    public MonthlyCustomerDto updateMonthlyCustomerDto(@PathVariable("email") String email, 
        @PathVariable("name") String name,
        @PathVariable("phone") String phone,
        @PathVariable("password") String password,
        @PathVariable("licenseNumber") String licenseNumber) {
        try {
            MonthlyCustomer mc = monthlyCustomerService.updateMonthlyCustomer(email, name, phone, password,licenseNumber);
            return HelperMethods.convertMonthlyCustomerToDto(mc);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } 
    } 


}

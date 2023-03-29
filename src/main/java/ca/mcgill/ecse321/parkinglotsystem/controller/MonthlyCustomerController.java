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
import org.springframework.web.bind.annotation.RequestParam;



import ca.mcgill.ecse321.parkinglotsystem.dao.MonthlyCustomerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dto.MonthlyCustomerDto;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.service.*;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/monthly-customer")

public class MonthlyCustomerController {


    @Autowired
    MonthlyCustomerRepository monthlyCustomerRepository;

    @Autowired
    MonthlyCustomerService monthlyCustomerService;


    @GetMapping(value={"","/"})
    public List<MonthlyCustomerDto> getAllMonthlyCustomerDtos(){
        return monthlyCustomerService.getAllMonthlyCustomers().stream().map(mc -> HelperMethods.convertMonthlyCustomerToDto(mc)).collect(Collectors.toList());
    }


    @PostMapping(value = {"/{email}", "/{email}/"})
    public MonthlyCustomerDto createMonthlyCustomerDto(
        @PathVariable("email") String email, 
        @RequestParam("name") String name,
        @RequestParam("phone") String phone,
        @RequestParam("password") String password,
        @RequestParam("licenseNumber") String licenseNumber){
        MonthlyCustomer mc = monthlyCustomerService.createMonthlyCustomer(email,name,phone,password,licenseNumber);
        return HelperMethods.convertMonthlyCustomerToDto(mc);                                
    }


    @GetMapping(value = {"/all-by-name/{name}", "/all-by-name/{name}/"})
    public List<MonthlyCustomerDto> getMonthlyCustomerDtoByName(@PathVariable("name") String name) {
        List<MonthlyCustomerDto> monthlyCustomerDtos = new ArrayList<MonthlyCustomerDto>();
        List<MonthlyCustomer> mcs = monthlyCustomerService.getMonthlyCustomerByName(name);
        if (mcs.size() != 0){
            for (MonthlyCustomer mc: mcs){
                monthlyCustomerDtos.add(HelperMethods.convertMonthlyCustomerToDto(mc));
            }
        }     
        return monthlyCustomerDtos; 
    }


    @GetMapping(value = {"/all-by-phone/{phone}", "/all-by-phone/{phone}/"})
    public List<MonthlyCustomerDto> getMonthlyCustomerDtoByPhone(@PathVariable("phone") String phone) {
        List<MonthlyCustomerDto> monthlyCustomerDtos = new ArrayList<MonthlyCustomerDto>();
        List<MonthlyCustomer> mcs = monthlyCustomerService.getMonthlyCustomerByPhone(phone);
        if (mcs.size() != 0){
            for (MonthlyCustomer mc: mcs){
                monthlyCustomerDtos.add(HelperMethods.convertMonthlyCustomerToDto(mc));
            }
        }     
        return monthlyCustomerDtos; 
    }

    
    @GetMapping(value = {"/all-by-license-number/{licenseNumber}", "/all-by-license-number/{licenseNumber}/"})
    public List<MonthlyCustomerDto> getMonthlyCustomerDtoByLicenseNumber(@PathVariable("licenseNumber") String licenseNumber) {
        List<MonthlyCustomerDto> monthlyCustomerDtos = new ArrayList<MonthlyCustomerDto>();
        List<MonthlyCustomer> mcs = monthlyCustomerService.getMonthlyCustomerByLicenseNumber(licenseNumber);
        if (mcs.size() != 0){
            for (MonthlyCustomer mc: mcs){
                monthlyCustomerDtos.add(HelperMethods.convertMonthlyCustomerToDto(mc));
            }
        }     
        return monthlyCustomerDtos;
    }


    @GetMapping(value = {"/{email}", "/{email}/"})
    public MonthlyCustomerDto getMonthlyCustomerDtoByEmail(@PathVariable("email") String email) {
        MonthlyCustomer mc = monthlyCustomerService.getMonthlyCustomerByEmail(email);
        if(mc==null){
            return null;
        }
        return HelperMethods.convertMonthlyCustomerToDto(mc);
    }


    @DeleteMapping(value = {"/{email}","/{email}/"})
    public MonthlyCustomerDto deleteMonthlyCustomerDtoByEmail(@PathVariable("email") String email) {
        MonthlyCustomer mc = monthlyCustomerService.deleteMonthlyCustomerByEmail(email);
        return HelperMethods.convertMonthlyCustomerToDto(mc);
    }


    @PutMapping(value ={"/{email}", "/{email}/"})
    public MonthlyCustomerDto updateMonthlyCustomerDto(
        @PathVariable("email") String email, 
        @RequestParam("name") String name,
        @RequestParam("phone") String phone,
        @RequestParam("password") String password,
        @RequestParam("licenseNumber") String licenseNumber) {
        MonthlyCustomer mc = monthlyCustomerService.updateMonthlyCustomer(email, name, phone, password,licenseNumber);
        return HelperMethods.convertMonthlyCustomerToDto(mc);
    } 


}
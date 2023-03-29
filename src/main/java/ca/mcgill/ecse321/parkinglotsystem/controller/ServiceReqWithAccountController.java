// Author :Chenxin Xun
// 2023-03-19

package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.dto.ServiceReqWithAccountDto;
import ca.mcgill.ecse321.parkinglotsystem.service.AuthenticationService;
import ca.mcgill.ecse321.parkinglotsystem.service.ServiceReqWithAccountService;

import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.convertServiceReqWithAccountToDto;;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/service-req-with-account")

public class ServiceReqWithAccountController {
    @Autowired
    private ServiceReqWithAccountService service;
    @Autowired
    private AuthenticationService authService;

    @GetMapping(value = {"","/"})
    public List<ServiceReqWithAccountDto> getAll(){
        return service.getAll().stream().map(s -> convertServiceReqWithAccountToDto(s)).collect(Collectors.toList());
    }

    @GetMapping(value = {"/{id}","/{id}/"})
    public ServiceReqWithAccountDto getServiceReqWithAccountById(@PathVariable("id") int id){
        return convertServiceReqWithAccountToDto(service.getServiceReqWithAccountById(id));
    }

    @GetMapping(value = {"/all-by-is-assigned/{isAssigned}","/all-by-is-assigned/{isAssigned}/"})
    public List<ServiceReqWithAccountDto> getServiceReqWithAccountByIsAssigned(@PathVariable("isAssigned") boolean isAssigned){
        return service.getServiceReqWithAccountByIsAssigned(isAssigned).stream()
        .map(s -> convertServiceReqWithAccountToDto(s)).collect(Collectors.toList());
    }

    @GetMapping(value = {"/all-by-customer/{monthlyCustomerEmail}","/all-by-customer/{monthlyCustomerEmail}/"})
    public List<ServiceReqWithAccountDto> getServiceReqWithAccountByCustomer(@PathVariable("monthlyCustomerEmail") String monthlyCustomerEmail){
        return service.getServiceReqWithAccountByCustomer(monthlyCustomerEmail).stream()
        .map(s -> convertServiceReqWithAccountToDto(s)).collect(Collectors.toList());
    }

    @PostMapping(value = {"","/"})
    public ServiceReqWithAccountDto createServiceReqWithAccount(
        @RequestParam(value = "monthlyCustomerEmail") String monthlyCustomerEmail,
        @RequestParam(value = "description") String description,
        @RequestHeader String token
    ){
        authService.authenticateMonthlyCustomer(token);
        return convertServiceReqWithAccountToDto(service.createServiceReqWithAccount(monthlyCustomerEmail, description));
    }

    @PutMapping(value = {"/{id}", "/{id}/"})
    public ServiceReqWithAccountDto updateIsAssignedById(
        @PathVariable(value = "id") int id,
        @RequestParam(value = "isAssigned") boolean isAssigned,
        @RequestHeader String token
    ){
        authService.authenticateEmployee(token);
        return convertServiceReqWithAccountToDto(service.updateIsAssignedById(id, isAssigned));
    }
    
}

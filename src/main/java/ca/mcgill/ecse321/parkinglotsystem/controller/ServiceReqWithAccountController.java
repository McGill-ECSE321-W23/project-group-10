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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.dto.ServiceReqWithAccountDto;
import ca.mcgill.ecse321.parkinglotsystem.service.ServiceReqWithAccountService;

import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.convertServiceReqWithAccountToDto;;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/service-req-with-account")

public class ServiceReqWithAccountController {
    @Autowired
    private ServiceReqWithAccountService service;

    @GetMapping(value = {"","/"})
    public List<ServiceReqWithAccountDto> getAll(){
        return service.getAll().stream().map(s -> convertServiceReqWithAccountToDto(s)).collect(Collectors.toList());
    }

    @GetMapping(value = {"/{id}","/{id}/"})
    public ServiceReqWithAccountDto getServiceReqWithAccountById(@PathVariable("id") int id){
        return convertServiceReqWithAccountToDto(service.getServiceReqWithAccountById(id));
    }

    @GetMapping(value = {"/is-assigned/{isAssigned}","/is-assigned/{isAssigned}/"})
    public List<ServiceReqWithAccountDto> getServiceReqWithAccountByIsAssigned(@PathVariable("isAssigned") boolean isAssigned){
        return service.getServiceReqWithAccountByIsAssigned(isAssigned).stream()
        .map(s -> convertServiceReqWithAccountToDto(s)).collect(Collectors.toList());
    }

    @GetMapping(value = {"/monthly-customer-email/{monthlyCustomerEmail}","/monthly-customer-email/{monthlyCustomerEmail/"})
    public List<ServiceReqWithAccountDto> getServiceReqWithAccountByCustomer(@PathVariable("monthlyCustomerEmail") String monthlyCustomerEmail){
        return service.getServiceReqWithAccountByCustomer(monthlyCustomerEmail).stream()
        .map(s -> convertServiceReqWithAccountToDto(s)).collect(Collectors.toList());
    }

    @PostMapping(value = {"","/"})
    public ServiceReqWithAccountDto createServiceReqWithAccount(
        @RequestParam(value = "monthlyCustomerEmail") String monthlyCustomerEmail,
        @RequestParam(value = "price") int price
    ){
        return convertServiceReqWithAccountToDto(service.createServiceReqWithAccount(monthlyCustomerEmail, price));
    }

    @PutMapping(value = {"/update/{id}/{isAssigned}", "/update/{id}/{isAssigned}/"})
    public ServiceReqWithAccountDto updateIsAssignedById(
        @RequestParam(value = "id") int id,
        @RequestParam(value = "isAssigned") boolean isAssigned
    ){
        return convertServiceReqWithAccountToDto(service.updateIsAssignedById(id, isAssigned));
    }
    
}

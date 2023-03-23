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

import ca.mcgill.ecse321.parkinglotsystem.dto.ServiceReqWithoutAccountDto;
import ca.mcgill.ecse321.parkinglotsystem.service.ServiceReqWithoutAccountService;

import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.convertServiceReqWithoutAccountToDto;;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/service-req-without-account")

public class ServiceReqWithoutAccountController {
    @Autowired
    private ServiceReqWithoutAccountService service;

    @GetMapping(value = {"","/"})
    public List<ServiceReqWithoutAccountDto> getAll(){
        return service.getAll().stream().map(s -> convertServiceReqWithoutAccountToDto(s)).collect(Collectors.toList());
    }

    @GetMapping(value = {"/{id}","/{id}/"})
    public ServiceReqWithoutAccountDto getServiceReqWithAccountById(@PathVariable("id") int id){
        return convertServiceReqWithoutAccountToDto(service.getServiceReqWithoutAccountById(id));
    }

    @GetMapping(value = {"/is-assigned/{isAssigned}","/is-assigned/{isAssigned}/"})
    public List<ServiceReqWithoutAccountDto> getServiceReqWithoutAccountByIsAssigned(@PathVariable("isAssigned") boolean isAssigned){
        return service.getServiceReqWithoutAccountByIsAssigned(isAssigned).stream()
        .map(s -> convertServiceReqWithoutAccountToDto(s)).collect(Collectors.toList());
    }

    @GetMapping(value = {"/license-number/{licenseNumber}","/license-number/{licenseNumber}/"})
    public List<ServiceReqWithoutAccountDto> getServiceReqWithoutAccountByCustomer(@PathVariable("licenseNumber") String licenseNumber){
        return service.findServiceReqWithoutAccountByLicenseNumber(licenseNumber).stream()
        .map(s -> convertServiceReqWithoutAccountToDto(s)).collect(Collectors.toList());
    }

    @PostMapping(value = {"","/"})
    public ServiceReqWithoutAccountDto createServiceReqWithoutAccount(
        @RequestParam(value = "licenseNumber") String licenseNumber,
        @RequestParam(value = "price") int price
    ){
        return convertServiceReqWithoutAccountToDto(service.createServiceReqWithoutAccount(licenseNumber, price));
    }

    @PutMapping(value = {"/update/{id}/{isAssigned}", "/update/{id}/{isAssigned}/"})
    public ServiceReqWithoutAccountDto updateIsAssignedById(
        @RequestParam(value = "id") int id,
        @RequestParam(value = "isAssigned") boolean isAssigned
    ){
        return convertServiceReqWithoutAccountToDto(service.updateIsAssignedById(id, isAssigned));
    }
    
}

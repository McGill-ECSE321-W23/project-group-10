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

import ca.mcgill.ecse321.parkinglotsystem.dto.ServiceReqWithoutAccountDto;
import ca.mcgill.ecse321.parkinglotsystem.service.AuthenticationService;
import ca.mcgill.ecse321.parkinglotsystem.service.ServiceReqWithoutAccountService;

import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.convertServiceReqWithoutAccountToDto;;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/service-req-without-account")

public class ServiceReqWithoutAccountController {
    @Autowired
    private ServiceReqWithoutAccountService service;
    @Autowired
    private AuthenticationService authService;

    /**
     * Gets all ServiceReqWithoutAccount.
     * 
     * @return the list of ServiceReqWithoutAccount as DTOs
     */
    @GetMapping(value = { "", "/" })
    public List<ServiceReqWithoutAccountDto> getAll() {
        return service.getAll().stream().map(s -> convertServiceReqWithoutAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Gets ServiceReqWithoutAccount with such ID.
     * 
     * @return a ServiceReqWithoutAccount as DTOs
     */
    @GetMapping(value = { "/{id}", "/{id}/" })
    public ServiceReqWithoutAccountDto getServiceReqWithAccountById(@PathVariable("id") int id) {
        return convertServiceReqWithoutAccountToDto(service.getServiceReqWithoutAccountById(id));
    }

    /**
     * Gets all ServiceReqWithoutAccount with such isAssigned.
     * 
     * @return the list of ServiceReqWithoutAccount as DTOs
     */
    @GetMapping(value = { "/all-by-is-assigned/{isAssigned}", "/all-by-is-assigned/{isAssigned}/" })
    public List<ServiceReqWithoutAccountDto> getServiceReqWithoutAccountByIsAssigned(
            @PathVariable("isAssigned") boolean isAssigned) {
        return service.getServiceReqWithoutAccountByIsAssigned(isAssigned).stream()
                .map(s -> convertServiceReqWithoutAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Gets ServiceReqWithoutAccount with such licenseNumber.
     * 
     * @return a ServiceReqWithoutAccount as DTOs
     */
    @GetMapping(value = { "/all-by-license-number/{licenseNumber}", "/all-by-license-number/{licenseNumber}/" })
    public List<ServiceReqWithoutAccountDto> getServiceReqWithoutAccountByCustomer(
            @PathVariable("licenseNumber") String licenseNumber) {
        return service.findServiceReqWithoutAccountByLicenseNumber(licenseNumber).stream()
                .map(s -> convertServiceReqWithoutAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Create a ServiceReqWithoutAccount.
     * 
     * @return a ServiceReqWithoutAccount as DTOs
     */
    @PostMapping(value = { "", "/" })
    public ServiceReqWithoutAccountDto createServiceReqWithoutAccount(
            @RequestParam(value = "licenseNumber") String licenseNumber,
            @RequestParam(value = "description") String description,
            @RequestHeader String token) {
        return convertServiceReqWithoutAccountToDto(service.createServiceReqWithoutAccount(licenseNumber, description));
    }

    /**
     * Update a ServiceReqWithoutAccount with such ID.
     * 
     * @return a ServiceReqWithoutAccount as DTOs
     */
    @PutMapping(value = { "/{id}", "/{id}/" })
    public ServiceReqWithoutAccountDto updateIsAssignedById(
            @PathVariable(value = "id") int id,
            @RequestParam(value = "isAssigned") boolean isAssigned,
            @RequestHeader String token) {
        authService.authenticateEmployee(token);
        return convertServiceReqWithoutAccountToDto(service.updateIsAssignedById(id, isAssigned));
    }

}

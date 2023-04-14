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
     * Controller method to get all service requests without account.
     * @author Chenxin
     * @return A List of ServiceReqWithoutAccountDto
     */
    @GetMapping(value = { "", "/" })
    public List<ServiceReqWithoutAccountDto> getAll() {
        return service.getAll().stream().map(s -> convertServiceReqWithoutAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Controller method to get service request without account with ID.
     * @author Chenxin
     * @param id the id of the service request without account
     * @return a ServiceReqWithoutAccountDto
     */
    @GetMapping(value = { "/{id}", "/{id}/" })
    public ServiceReqWithoutAccountDto getServiceReqWithAccountById(@PathVariable("id") int id) {
        return convertServiceReqWithoutAccountToDto(service.getServiceReqWithoutAccountById(id));
    }

    /**
     * Controller method to get service requests without account with isAssigned status.
     * @author Chenxin
     * @param isAssigned whether the service request without account is assigned status
     * @return A List of ServiceReqWithoutAccountDto
     */
    @GetMapping(value = { "/all-by-is-assigned/{isAssigned}", "/all-by-is-assigned/{isAssigned}/" })
    public List<ServiceReqWithoutAccountDto> getServiceReqWithoutAccountByIsAssigned(
            @PathVariable("isAssigned") boolean isAssigned) {
        return service.getServiceReqWithoutAccountByIsAssigned(isAssigned).stream()
                .map(s -> convertServiceReqWithoutAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Controller method to get service requests without account with license number.
     * @author Chenxin
     * @param licenseNumber the license number of the service request without account
     * @return A List of ServiceReqWithoutAccountDto
     */
    @GetMapping(value = { "/all-by-license-number/{licenseNumber}", "/all-by-license-number/{licenseNumber}/" })
    public List<ServiceReqWithoutAccountDto> getServiceReqWithoutAccountByCustomer(
            @PathVariable("licenseNumber") String licenseNumber) {
        return service.findServiceReqWithoutAccountByLicenseNumber(licenseNumber).stream()
                .map(s -> convertServiceReqWithoutAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Controller method to create a service request without account.
     * @author Chenxin
     * @param licenseNumber the license number of the service request without account
     * @param description the description of the service request without account
     * @return A ServiceReqWithoutAccountDto
     */
    @PostMapping(value = { "", "/" })
    public ServiceReqWithoutAccountDto createServiceReqWithoutAccount(
            @RequestParam(value = "licenseNumber") String licenseNumber,
            @RequestParam(value = "description") String description) {
        return convertServiceReqWithoutAccountToDto(service.createServiceReqWithoutAccount(licenseNumber, description));
    }

    /**
     * Controller method to update a service request without account with ID.
     * @author Chenxin
     * @param id the id of the service request without account
     * @param isAssigned whether the service request without account is assigned status
     * @return the updated ServiceReqWithoutAccountDto
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

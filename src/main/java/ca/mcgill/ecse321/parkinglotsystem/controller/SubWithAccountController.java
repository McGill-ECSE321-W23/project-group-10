package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.dto.SubWithAccountDto;
import ca.mcgill.ecse321.parkinglotsystem.service.SubWithAccountService;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.convertSubWithAccountToDto;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/sub-with-account")
public class SubWithAccountController {
    
    @Autowired
    private SubWithAccountService service;

    // TODO: Add javadoc comments

    @GetMapping(value = {"", "/"})
    public List<SubWithAccountDto> getAll() {
        return service.getAll().stream().
            map(s -> convertSubWithAccountToDto(s)).collect(Collectors.toList());
    }

    @GetMapping(value = {"/all-by-customer/{email}", "/all-by-customer/{email}/"})
    public List<SubWithAccountDto> getAll(
        @PathVariable("email") String monthlyCustomerEmail) {
        return service.getAll(monthlyCustomerEmail).stream().
            map(s -> convertSubWithAccountToDto(s)).collect(Collectors.toList());
    }

    @GetMapping(value = {"/{id}", "/{id}/"})
    public SubWithAccountDto getSubWithAccount(@PathVariable("id") int id) {
        return convertSubWithAccountToDto(service.getSubWithAccount(id));
    }

    @GetMapping(value = {"/active-by-customer/{email}", "/active-by-customer/{email}/"})
    public SubWithAccountDto getActiveSubWithAccount(
        @PathVariable("email") String monthlyCustomerEmail) {
        return convertSubWithAccountToDto(service.getActiveSubWithAccount(monthlyCustomerEmail));
    }
    
    @PostMapping(value = {"", "/"})
    public SubWithAccountDto createSubWithAccount(
        @RequestParam(value = "customer-email") String monthlyCustomerEmail,
        @RequestParam(value = "parking-spot-id") int parkingSpotId) {
        return convertSubWithAccountToDto(
            service.createSubWithAccount(monthlyCustomerEmail, parkingSpotId));
    }

}

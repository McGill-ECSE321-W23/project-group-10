package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.dto.SubWithAccountDto;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithAccount;
import ca.mcgill.ecse321.parkinglotsystem.service.SubWithAccountService;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.convertSubWithAccountToDto;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/sub-with-account")
public class SubWithAccountController {
    
    @Autowired
    private SubWithAccountService subWithAccountService;

    @GetMapping(value = {"", "/"})
    public List<SubWithAccountDto> getAll() {
        return convertSubWithAccountToDtoList(subWithAccountService.getAll());
    }

    @GetMapping(value = {"/all-by-customer/{email}", "/all-by-customer/{email}/"})
    public List<SubWithAccountDto> getAll(
        @PathVariable("email") String monthlyCustomerEmail) {
        return convertSubWithAccountToDtoList(subWithAccountService.getAll(monthlyCustomerEmail));
    }

    @GetMapping(value = {"/{id}", "/{id}/"})
    public SubWithAccountDto getSubWithAccount(@PathVariable("id") int id) {
        return convertSubWithAccountToDto(subWithAccountService.getSubWithAccount(id));
    }

    @GetMapping(value = {"/active-by-customer/{email}", "/active-by-customer/{email}/"})
    public SubWithAccountDto getActiveSubWithAccount(
        @PathVariable("email") String monthlyCustomerEmail) {
        return convertSubWithAccountToDto(
            subWithAccountService.getActiveSubWithAccount(monthlyCustomerEmail));
    }
    
    @PostMapping(value = {"", "/"})
    public SubWithAccountDto createSubWithAccount(
        @RequestParam(value = "customer-email") String monthlyCustomerEmail,
        @RequestParam(value = "parking-spot-id") int parkingSpotId) {
        SubWithAccount sub = 
            subWithAccountService.createSubWithAccount(monthlyCustomerEmail, parkingSpotId);
        return convertSubWithAccountToDto(sub);
    }

    @PostMapping(value = {"/{email}", "/{email}/"})
    public SubWithAccountDto updateSubWithAccount(
        @PathVariable("email") String monthlyCustomerEmail) {
        return convertSubWithAccountToDto(
            subWithAccountService.updateSubWithAccount(monthlyCustomerEmail));
    }

    private List<SubWithAccountDto> convertSubWithAccountToDtoList(List<SubWithAccount> subs) {

        // TODO: Move this method to HelperMethods.java
        List<SubWithAccountDto> subsDto = new ArrayList<>();
        for (var sub : subs) {
            subsDto.add(convertSubWithAccountToDto(sub));
        }
        return subsDto;
    }

}

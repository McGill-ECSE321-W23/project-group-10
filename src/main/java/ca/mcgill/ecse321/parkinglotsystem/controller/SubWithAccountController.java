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
@RequestMapping("/api/sub-with-account")
public class SubWithAccountController {
    
    @Autowired
    private SubWithAccountService service;

    /**
     * Gets all subscriptions.
     * 
     * @return the list of subscriptions as DTOs
     */
    @GetMapping(value = {"", "/"})
    public List<SubWithAccountDto> getAll() {
        return service.getAll().stream().
            map(s -> convertSubWithAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Gets all subscription with given monthly customer.
     * 
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return the list of subscriptions as DTOs
     */
    @GetMapping(value = {"/all-by-customer/{email}", "/all-by-customer/{email}/"})
    public List<SubWithAccountDto> getAllByCustomer(@PathVariable("email") String monthlyCustomerEmail) {
        return service.getAllByCustomer(monthlyCustomerEmail).stream().
            map(s -> convertSubWithAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Gets all subscriptions with the given parking spot.
     * 
     * @param parkingSpotId the ID of the parking spot
     * @return the list of subscriptions as DTOs
     */
    @GetMapping(value = {"/all-by-parking-spot/{id}", "/all-by-parking-spot/{id}/"})
    public List<SubWithAccountDto> getAllByParkingSpot(@PathVariable("id") int parkingSpotId) {
        return service.getAllByParkingSpot(parkingSpotId).stream().
            map(s -> convertSubWithAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Gets a subscription with the given ID.
     * @param id the ID of the subscription.
     * @return a subscription as DTO
     */
    @GetMapping(value = {"/{id}", "/{id}/"})
    public SubWithAccountDto getSubWithAccount(@PathVariable("id") int id) {
        return convertSubWithAccountToDto(service.getSubWithAccount(id));
    }

    /**
     * Gets the active subscription of the given monthly customer.
     * 
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return the active subscription as DTO
     */
    @GetMapping(value = {"/active-by-customer/{email}", "/active-by-customer/{email}/"})
    public SubWithAccountDto getActiveByCustomer(@PathVariable("email") String monthlyCustomerEmail) {
        return convertSubWithAccountToDto(service.getActiveByCustomer(monthlyCustomerEmail));
    }

    /**
     * Gets the active subscription of the given parking spot.
     * 
     * @param parkingSpotId the ID of the parking spot
     * @return the active subscription as DTO
     */
    @GetMapping(value = {"/active-by-parking-spot/{id}", "/active-by-parking-spot/{id}/"})
    public SubWithAccountDto getActiveByParkingSpot(@PathVariable("id") int parkingSpotId) {
        return convertSubWithAccountToDto(service.getActiveByParkingSpot(parkingSpotId));
    }
    
    /**
     * Creates a subscription with the given monthly customer and parking spot.
     * 
     * @param monthlyCustomerEmail the email of the monthly customer for whom to create the subscription
     * @param parkingSpotId the id of the parking spot to reserve
     * @return the new subscription as DTO
     */
    @PostMapping(value = {"", "/"})
    public SubWithAccountDto createSubWithAccount(
        @RequestParam(value = "customer-email") String monthlyCustomerEmail,
        @RequestParam(value = "parking-spot-id") int parkingSpotId) {
        return convertSubWithAccountToDto(
            service.createSubWithAccount(monthlyCustomerEmail, parkingSpotId));
    }

}
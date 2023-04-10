package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.dto.SubWithAccountDto;
import ca.mcgill.ecse321.parkinglotsystem.service.AuthenticationService;
import ca.mcgill.ecse321.parkinglotsystem.service.SubWithAccountService;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.convertSubWithAccountToDto;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sub-with-account")
public class SubWithAccountController {
    
    @Autowired
    private SubWithAccountService service;

    @Autowired
    private AuthenticationService authService;

    /**
     * Controller method to get all subscriptions with account.
     * @author Marco
     * @return A List of SubWithAccountDto
     */
    @GetMapping(value = {"", "/"})
    public List<SubWithAccountDto> getAll() {
        return service.getAll().stream().
            map(s -> convertSubWithAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Controller method to get all subscriptions with account with given monthly customer.
     * @author Marco
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return A List of SubWithAccountDto
     */
    @GetMapping(value = {"/all-by-customer/{email}", "/all-by-customer/{email}/"})
    public List<SubWithAccountDto> getAllByCustomer(@PathVariable("email") String monthlyCustomerEmail) {
        return service.getAllByCustomer(monthlyCustomerEmail).stream().
            map(s -> convertSubWithAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Controller method to get all subscriptions with account with the given parking spot.
     * @author Marco
     * @param parkingSpotId the ID of the parking spot
     * @return A List of SubWithAccountDto
     */
    @GetMapping(value = {"/all-by-parking-spot/{id}", "/all-by-parking-spot/{id}/"})
    public List<SubWithAccountDto> getAllByParkingSpot(@PathVariable("id") int parkingSpotId) {
        return service.getAllByParkingSpot(parkingSpotId).stream().
            map(s -> convertSubWithAccountToDto(s)).collect(Collectors.toList());
    }

    /**
     * Controller method to get a subscription with account with the given ID.
     * @author Marco
     * @param id the ID of the subscription.
     * @return a SubWithAccountDto
     */
    @GetMapping(value = {"/{id}", "/{id}/"})
    public SubWithAccountDto getSubWithAccount(@PathVariable("id") int id) {
        return convertSubWithAccountToDto(service.getSubWithAccount(id));
    }

    /**
     * Controller method to get the active subscription with account of the given monthly customer.
     * @author Marco
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return the SubWithAccountDto
     */
    @GetMapping(value = {"/active-by-customer/{email}", "/active-by-customer/{email}/"})
    public SubWithAccountDto getActiveByCustomer(@PathVariable("email") String monthlyCustomerEmail) {
        return convertSubWithAccountToDto(service.getActiveByCustomer(monthlyCustomerEmail));
    }

    /**
     * Controller method to get the active subscription with account of the given parking spot.
     * @author Marco
     * @param parkingSpotId the ID of the parking spot
     * @return the active SubWithAccountDto
     */
    @GetMapping(value = {"/active-by-parking-spot/{id}", "/active-by-parking-spot/{id}/"})
    public SubWithAccountDto getActiveByParkingSpot(@PathVariable("id") int parkingSpotId) {
        return convertSubWithAccountToDto(service.getActiveByParkingSpot(parkingSpotId));
    }

    /**
     * Controller method to get the parking fee of the subscription with account of the given monthly customer.
     * @author Shaun
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return the parking fee
     */
    @GetMapping(value ={"/get-parking-fee/{email}", "/get-parking-fee/{email}/"})
    public double getParkingFee(@PathVariable("email") String monthlyCustomerEmail) {
        return service.getReservationParkingSpotPrice(monthlyCustomerEmail);
    }

    /**
     * Controller method to get the reservation id of the subscription with account of the given monthly customer.
     * @author Shaun
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return the reservation id
     */
    @GetMapping(value ={"/get-id/{email}", "/get-id/{email}/"})
    public int getReservationId(@PathVariable("email") String monthlyCustomerEmail){
        return service.getReservationId(monthlyCustomerEmail);
    }
    
    
    /**
     * Controller method to get a subscription with account with the given monthly customer and parking spot.
     * @author Marco
     * @param monthlyCustomerEmail the email of the monthly customer for whom to create the subscription
     * @param parkingSpotId the id of the parking spot to reserve
     * @return A SubWithAccountDto
     */
    @PostMapping(value = {"", "/"})
    public SubWithAccountDto createSubWithAccount(
        @RequestParam String monthlyCustomerEmail,
        @RequestParam int parkingSpotId) {
        return convertSubWithAccountToDto(
            service.createSubWithAccount(monthlyCustomerEmail, parkingSpotId));
    }

    /**
     * Controller method to delete the subscription with account with the given ID.
     * @author Marco
     * @param id the id of the subscription
     */
    @DeleteMapping(value = {"/{id}","/{id}/"})
    public void deleteSubWithAccount(@PathVariable int id, @RequestHeader String token) {
        authService.authenticateManager(token);
        service.deleteSubWithAccount(id);
    }


    /**
     * Controller method to update the subscription with account with the given 
     * number of month.
     * @param email the email of the subscription
     * @param numberOfMonths the number of months to extend the subscription
     * @return the updated SubWithAccountDto
     */
    @PutMapping(value ={"/{email}", "/{email}/"})
    public SubWithAccountDto updateSubWithAccount(@PathVariable String email, @RequestParam int numberOfMonths) {
        return convertSubWithAccountToDto(service.updateSubWithAccount(email, numberOfMonths));
    }

}

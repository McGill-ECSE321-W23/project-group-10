package ca.mcgill.ecse321.parkinglotsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.dto.LoginDto;
import ca.mcgill.ecse321.parkinglotsystem.service.AuthenticationService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationService service;

    /**
     * Logs in the manager.
     * 
     * @param credentials
     * @return the authentication token
     */
    @PostMapping(value = {"/login-manager","/login-manager/"})
    public String loginManager(@RequestBody LoginDto credentials) {
        return service.loginManager(credentials);
    }

    /**
     * Logs in the employee.
     * 
     * @param credentials
     * @return the authentication token
     */
    @PostMapping(value = {"/login-employee","/login-employee/"})
    public String loginEmployee(@RequestBody LoginDto credentials) {
        return service.loginEmployee(credentials);
    }

    /**
     * Logs in the monthly customer.
     * 
     * @param credentials
     * @return the authentication token
     */
    @PostMapping(value = {"/login-customer","/login-customer/"})
    public String loginMonthlyCustomer(@RequestBody LoginDto credentials) {
        return service.loginMonthlyCustomer(credentials);
    }

    /**
     * Logs out the person.
     * 
     * @param token the authentication token
     */
    @PostMapping(value = {"/logout","/logout/"})
    public void logout(@RequestHeader String token) {
        service.logout(token);;
    }

}

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
     * Validates the credentials to login the person.
     * 
     * @param credentials
     * @return the email of the person and the person type, separated by a comma.
     */
    @PostMapping(value = {"/login","/login/"})
    public String login(@RequestBody LoginDto credentials) {
        return service.login(credentials);
    }

    /**
     * Logout the person.
     * 
     * @param token the email of the person
     * @return the result message
     */
    @PostMapping(value = {"/logout","/logout/"})
    public String logout(@RequestHeader String token) {
        return service.logout(token);
    }

}

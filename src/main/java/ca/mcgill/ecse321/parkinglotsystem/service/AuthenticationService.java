package ca.mcgill.ecse321.parkinglotsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ManagerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.MonthlyCustomerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dto.LoginDto;
import ca.mcgill.ecse321.parkinglotsystem.model.Manager;
import ca.mcgill.ecse321.parkinglotsystem.model.Person;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

@Service
public class AuthenticationService {
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MonthlyCustomerRepository customerRepository;

    /**
     * Validates the credentials to login the person.
     * 
     * @param credentials
     * @return the email of the person and the person type, separated by a comma.
     */
    @Transactional
    public String login(LoginDto credentials) {
        String personType = "";
        Person p  = managerRepository.findManagerByEmail(credentials.getEmail());
        personType = "Manager";
        if(p == null) {
            p = employeeRepository.findEmployeeByEmail(credentials.getEmail());
            personType = "Employee";
        }
        if(p == null) {
            p = customerRepository.findMonthlyCustomerByEmail(credentials.getEmail());
            personType = "MonthlyCustomer";
        }
        if(p == null) {
            throw new CustomException("Invalid email", HttpStatus.BAD_REQUEST);
        }
        if(!credentials.getPassword().equals(p.getPassword())) {
            throw new CustomException("Incorrect password", HttpStatus.BAD_REQUEST);
        }

        return p.getEmail() + "," + personType;
    }

    /**
     * Logout the person.
     * 
     * @param token the email of the person
     * @return the result message
     */
    @Transactional
    public String logout(String token) {
        // TODO: TO BE IMPLEMENTED IN FRONT END (?)
        return "Successful logout";
    }

    /**
     * Authenticates the manager.
     * 
     * @param token the email of the person
     */
    @Transactional
    public void authenticateManager(String token) {
        Manager p  = managerRepository.findManagerByEmail(token);
        if(p == null) {
            throw new CustomException("You must be logged in as a Manager", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Authenticates the employee.
     * 
     * @param token the email of the person
     */
    @Transactional
    public void authenticateEmployee(String token) {
        Person p = managerRepository.findManagerByEmail(token);
        if(p != null) return;
        p = employeeRepository.findEmployeeByEmail(token);
        if(p != null) return;
        throw new CustomException("You must be logged in as an Employee or Manager", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Authenticates the monthly customer.
     * 
     * @param token the email of the person
     */
    @Transactional
    public void authenticateMonthlyCustomer(String token) {
        // TODO: Confirm if Manager has the same permissions as MonthlyCustomer
        Person p = managerRepository.findManagerByEmail(token);
        if(p != null) return;
        p = customerRepository.findMonthlyCustomerByEmail(token);
        if(p != null) return;
        throw new CustomException("You must be logged in as a Monthly Customer or Manager", HttpStatus.UNAUTHORIZED);
    }
    
}

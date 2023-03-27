package ca.mcgill.ecse321.parkinglotsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ManagerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.MonthlyCustomerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dto.LoginDto;
import ca.mcgill.ecse321.parkinglotsystem.model.Employee;
import ca.mcgill.ecse321.parkinglotsystem.model.Manager;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.Person;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.TokenUtil;

@Service
public class AuthenticationService {
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MonthlyCustomerRepository customerRepository;

    private static final String ERR_MSG_MANAGER = "You must be logged in as a Manager";
    private static final String ERR_MSG_EMPLOYEE = "You must be logged in as an Employee or Manager";
    private static final String ERR_MSG_CUSTOMER = "You must be logged in as a Monthly Customer or Manager";

    /**
     * Logs in a manager.
     * 
     * @param credentials
     * @return the authentication token
     */
    @Transactional
    public String loginManager(LoginDto credentials) {
        Manager manager = managerRepository.findManagerByEmail(credentials.getEmail());
        String token = validateCredentialsAndCreateToken(manager, credentials);
        manager.setToken(token);
        managerRepository.save(manager);
        return token;
    }

    /**
     * Logs in an employee.
     * 
     * @param credentials
     * @return the authentication token
     */
    @Transactional
    public String loginEmployee(LoginDto credentials) {
        Employee employee = employeeRepository.findEmployeeByEmail(credentials.getEmail());
        String token = validateCredentialsAndCreateToken(employee, credentials);
        employee.setToken(token);
        employeeRepository.save(employee);
        return token;
    }

    /**
     * Logs in a monthly customer.
     * 
     * @param credentials
     * @return the authentication token
     */
    @Transactional
    public String loginMonthlyCustomer(LoginDto credentials) {
        MonthlyCustomer customer = customerRepository.findMonthlyCustomerByEmail(credentials.getEmail());
        String token = validateCredentialsAndCreateToken(customer, credentials);
        customer.setToken(token);
        customerRepository.save(customer);
        return token;
    }

    /**
     * Logs out the person.
     * 
     * @param token the token of the person
     */
    @Transactional
    public void logout(String token) {
        Person p = findManagerByToken(token);
        if(p != null) {
            p.setToken(null);
            managerRepository.save((Manager) p);
            return;
        }
        p = findEmployeeByToken(token);
        if(p != null) {
            p.setToken(null);
            employeeRepository.save((Employee) p);
            return;
        }
        p = findMonthlyCustomerByToken(token);
        if(p != null) {
            p.setToken(null);
            customerRepository.save((MonthlyCustomer) p);
            return;
        }
    }

    /**
     * Authenticates a manager.
     * 
     * @param token the email of the person
     */
    @Transactional
    public void authenticateManager(String token) {
        validateToken(token, ERR_MSG_MANAGER);
        Manager p  = findManagerByToken(token);
        if(p == null) {
            throw new CustomException(ERR_MSG_MANAGER, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Authenticates an employee.
     * 
     * @param token the email of the person
     */
    @Transactional
    public void authenticateEmployee(String token) {
        validateToken(token, ERR_MSG_EMPLOYEE);
        Person p = findManagerByToken(token);
        if(p != null) return;
        p = findEmployeeByToken(token);
        if(p != null) return;
        throw new CustomException(ERR_MSG_EMPLOYEE, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Authenticates a monthly customer.
     * 
     * @param token the email of the person
     */
    @Transactional
    public void authenticateMonthlyCustomer(String token) {
        // TODO: Confirm if Manager has the same permissions as MonthlyCustomer
        validateToken(token, ERR_MSG_CUSTOMER);
        Person p = findManagerByToken(token);
        if(p != null) return;
        p = findMonthlyCustomerByToken(token);
        if(p != null) return;
        throw new CustomException(ERR_MSG_CUSTOMER, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Validates the credentials and generates a token. This method does not save the token in the database.
     * 
     * @param p
     * @param credentials
     * @return the authentication token
     */
    private String validateCredentialsAndCreateToken(Person p, LoginDto credentials) {
        if(p == null) {
            throw new CustomException("Invalid email", HttpStatus.BAD_REQUEST);
        }
        if(!p.getPassword().equals(credentials.getPassword())) {
            throw new CustomException("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        return TokenUtil.createToken(p.getEmail());
    }

    /**
     * Validates a token. If the token is invalid, it throws a CustomException with the 
     * given error message.
     * 
     * @param token
     * @param errorMessage
     */
    private void validateToken(String token, String errorMessage) {
        long expires = 0;
        try {
            String[] parts = token.split(":");
            expires = Long.parseLong(parts[0]);
            if (expires < System.currentTimeMillis()) {
                throw new RuntimeException();
            }
        } catch(Exception e) {
            throw new CustomException(errorMessage, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Finds a manager by its token.
     * 
     * @param token
     * @return the manager if it exists, null otherwise.
     */
    private Manager findManagerByToken(String token) {
        List<Manager> list = managerRepository.findManagerByToken(token);
        if(list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Finds an employee by its token.
     * 
     * @param token
     * @return the employee if it exists, null otherwise.
     */
    private Employee findEmployeeByToken(String token) {
        List<Employee> list = employeeRepository.findEmployeeByToken(token);
        if(list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Finds a monthly customer by its token.
     * 
     * @param token
     * @return the monthly customer if it exists, null otherwise.
     */
    private MonthlyCustomer findMonthlyCustomerByToken(String token) {
        List<MonthlyCustomer> list = customerRepository.findMonthlyCustomerByToken(token);
        if(list.size() < 1) {
            return null;
        }
        return list.get(0);
    }
    
}

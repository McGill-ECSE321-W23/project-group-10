package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.parkinglotsystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ManagerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.MonthlyCustomerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dto.LoginDto;
import ca.mcgill.ecse321.parkinglotsystem.model.Employee;
import ca.mcgill.ecse321.parkinglotsystem.model.Manager;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {

    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private MonthlyCustomerRepository customerRepository;

    @InjectMocks
    private AuthenticationService service;

    private static final String VALID_MANAGER_EMAIL = "manager@email.com";
    private static final String VALID_EMPLOYEE_EMAIL = "employee@email.com";
    private static final String VALID_CUSTOMER_EMAIL = "customer@email.com";
    private static final String INVALID_EMAIL = "invalid@email.com";
    private static final String VALID_PASSWORD = "valid";
    private static final String INVALID_PASSWORD = "invalid";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(managerRepository.findManagerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID_MANAGER_EMAIL)) {
                Manager p = new Manager();
                p.setEmail(VALID_MANAGER_EMAIL);
                p.setPassword(VALID_PASSWORD);
                return p;
            }
            return null;
        });

        lenient().when(employeeRepository.findEmployeeByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID_EMPLOYEE_EMAIL)) {
                Employee p = new Employee();
                p.setEmail(VALID_EMPLOYEE_EMAIL);
                p.setPassword(VALID_PASSWORD);
                return p;
            }
            return null;
        });

        lenient().when(customerRepository.findMonthlyCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID_CUSTOMER_EMAIL)) {
                MonthlyCustomer p = new MonthlyCustomer();
                p.setEmail(VALID_CUSTOMER_EMAIL);
                p.setPassword(VALID_PASSWORD);
                return p;
            }
            return null;
        });
    }

    @Test
    public void testLoginManager() {
        String result = service.login(new LoginDto(VALID_MANAGER_EMAIL, VALID_PASSWORD));
        assertEquals(VALID_MANAGER_EMAIL+",Manager", result);
    }

    @Test
    public void testLoginManagerInvalidPassword() {
        String message = "";
        try {
            service.login(new LoginDto(VALID_MANAGER_EMAIL, INVALID_PASSWORD));
        } catch(Exception e) {
            message = e.getMessage();
        }

        assertEquals("Incorrect password", message);
    }

    @Test
    public void testLoginEmployee() {
        String result = service.login(new LoginDto(VALID_EMPLOYEE_EMAIL, VALID_PASSWORD));
        assertEquals(VALID_EMPLOYEE_EMAIL+",Employee", result);
    }

    @Test
    public void testLoginCustomer() {
        String result = service.login(new LoginDto(VALID_CUSTOMER_EMAIL, VALID_PASSWORD));
        assertEquals(VALID_CUSTOMER_EMAIL+",MonthlyCustomer", result);
    }

    @Test
    public void testLoginNonExistingPerson() {
        String message = "";
        try {
            service.login(new LoginDto(INVALID_EMAIL, VALID_PASSWORD));
        } catch(Exception e) {
            message = e.getMessage();
        }

        assertEquals("Invalid email", message);
    }

    @Test
    public void testLogout() {
        String result = service.logout(VALID_EMPLOYEE_EMAIL);
        assertEquals("Successful logout", result);
    }

    @Test
    public void testAuthenticateManager() {
        service.authenticateManager(VALID_MANAGER_EMAIL);
    }

    @Test
    public void testAuthenticateEmployee1() {
        service.authenticateEmployee(VALID_EMPLOYEE_EMAIL);
    }

    @Test
    public void testAuthenticateEmployee2() {
        service.authenticateEmployee(VALID_MANAGER_EMAIL);
    }

    @Test
    public void testAuthenticateCustomer1() {
        service.authenticateMonthlyCustomer(VALID_CUSTOMER_EMAIL);
    }

    @Test
    public void testAuthenticateCustomer2() {
        service.authenticateMonthlyCustomer(VALID_MANAGER_EMAIL);
    }

    @Test
    public void testAuthenticateNonExistingManager() {
        String message = "";
        try {
            service.authenticateManager(INVALID_EMAIL);
        } catch(Exception e) {
            message = e.getMessage();
        }
        assertEquals("You must be logged in as a Manager", message);
    }

    @Test
    public void testAuthenticateNonExistingEmployee() {
        String message = "";
        try {
            service.authenticateEmployee(INVALID_EMAIL);
        } catch(Exception e) {
            message = e.getMessage();
        }
        assertEquals("You must be logged in as an Employee or Manager", message);
    }

    @Test
    public void testAuthenticateNonExistingCustomer() {
        String message = "";
        try {
            service.authenticateMonthlyCustomer(INVALID_EMAIL);
        } catch(Exception e) {
            message = e.getMessage();
        }
        assertEquals("You must be logged in as a Monthly Customer or Manager", message);
    }
    
}

package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.parkinglotsystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ManagerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.MonthlyCustomerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dto.LoginDto;
import ca.mcgill.ecse321.parkinglotsystem.model.Employee;
import ca.mcgill.ecse321.parkinglotsystem.model.Manager;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;

@ExtendWith(MockitoExtension.class)
public class TestAuthenticationService {

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
    private static String VALID_MANAGER_TOKEN = "";
    private static String VALID_EMPLOYEE_TOKEN = "";
    private static String VALID_CUSTOMER_TOKEN = "";
    private static String VALID_NONEXISTENT_TOKEN = "";
    private static final String INVALID_TOKEN = "2:234";
    private static final String EXPIRED_TOKEN = "1:234";

    @BeforeEach
    public void setMockOutput() {
        VALID_MANAGER_TOKEN = System.currentTimeMillis() + 3600000 + ":123";
        VALID_EMPLOYEE_TOKEN = System.currentTimeMillis() + 3600000 + ":456";
        VALID_CUSTOMER_TOKEN = System.currentTimeMillis() + 3600000 + ":789";
        VALID_NONEXISTENT_TOKEN = System.currentTimeMillis() + 3600000 + ":012";

        lenient().when(managerRepository.findManagerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID_MANAGER_EMAIL)) {
                return dummyManager();
            }
            return null;
        });

        lenient().when(employeeRepository.findEmployeeByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID_EMPLOYEE_EMAIL)) {
                return dummyEmployee();
            }
            return null;
        });

        lenient().when(customerRepository.findMonthlyCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID_CUSTOMER_EMAIL)) {
                return dummyCustomer();
            }
            return null;
        });

        lenient().when(managerRepository.findManagerByToken(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            List<Manager> list = new ArrayList<>();
            String token = invocation.getArgument(0);
            if(token.equals(VALID_MANAGER_TOKEN) || token.equals(EXPIRED_TOKEN)) {
                list.add(dummyManager());
            }
            return list;
        });

        lenient().when(employeeRepository.findEmployeeByToken(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            List<Employee> list = new ArrayList<>();
            String token = invocation.getArgument(0);
            if(token.equals(VALID_EMPLOYEE_TOKEN) || token.equals(EXPIRED_TOKEN)) {
                list.add(dummyEmployee());
            }
            return list;
        });

        lenient().when(customerRepository.findMonthlyCustomerByToken(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            List<MonthlyCustomer> list = new ArrayList<>();
            String token = invocation.getArgument(0);
            if(token.equals(VALID_CUSTOMER_TOKEN) || token.equals(EXPIRED_TOKEN)) {
                list.add(dummyCustomer());
            }
            return list;
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

        lenient().when(managerRepository.save(any(Manager.class))).thenAnswer(returnParameterAsAnswer);

        lenient().when(employeeRepository.save(any(Employee.class))).thenAnswer(returnParameterAsAnswer);

        lenient().when(customerRepository.save(any(MonthlyCustomer.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testLoginManager() {
        String result = service.loginManager(new LoginDto(VALID_MANAGER_EMAIL, VALID_PASSWORD));
        assertNotNull(result);
        assertTrue(!result.isEmpty());
    }

    @Test
    public void testLoginManagerInvalidPassword() {
        String message = "";
        try {
            service.loginManager(new LoginDto(VALID_MANAGER_EMAIL, INVALID_PASSWORD));
        } catch(Exception e) {
            message = e.getMessage();
        }

        assertEquals("Incorrect password", message);
    }

    @Test
    public void testLoginEmployee() {
        String result = service.loginEmployee(new LoginDto(VALID_EMPLOYEE_EMAIL, VALID_PASSWORD));
        assertNotNull(result);
        assertTrue(!result.isEmpty());
    }

    @Test
    public void testLoginCustomer() {
        String result = service.loginMonthlyCustomer(new LoginDto(VALID_CUSTOMER_EMAIL, VALID_PASSWORD));
        assertNotNull(result);
        assertTrue(!result.isEmpty());
    }

    @Test
    public void testLoginNonExistentPerson() {
        String message = "";
        try {
            service.loginEmployee(new LoginDto(INVALID_EMAIL, VALID_PASSWORD));
        } catch(Exception e) {
            message = e.getMessage();
        }

        assertEquals("Invalid email", message);
    }

    @Test
    public void testLogoutManager() {
        service.logout(VALID_MANAGER_TOKEN);
    }

    @Test
    public void testLogoutEmployee() {
        service.logout(VALID_EMPLOYEE_TOKEN);
    }

    @Test
    public void testLogoutCustomer() {
        service.logout(VALID_CUSTOMER_TOKEN);
    }

    @Test
    public void testLogoutNonExistentPerson() {
        service.logout(VALID_NONEXISTENT_TOKEN);
    }

    @Test
    public void testAuthenticateManager() {
        service.authenticateManager(VALID_MANAGER_TOKEN);
    }

    @Test
    public void testAuthenticateEmployee1() {
        service.authenticateEmployee(VALID_EMPLOYEE_TOKEN);
    }

    @Test
    public void testAuthenticateEmployee2() {
        service.authenticateEmployee(VALID_MANAGER_TOKEN);
    }

    @Test
    public void testAuthenticateCustomer1() {
        service.authenticateMonthlyCustomer(VALID_CUSTOMER_TOKEN);
    }

    @Test
    public void testAuthenticateCustomer2() {
        service.authenticateMonthlyCustomer(VALID_MANAGER_TOKEN);
    }

    @Test
    public void testAuthenticateNonExistentManager1() {
        String message = "";
        try {
            service.authenticateManager(INVALID_TOKEN);
        } catch(Exception e) {
            message = e.getMessage();
        }
        assertEquals("You must be logged in as a Manager", message);
    }

    @Test
    public void testAuthenticateNonExistentManager2() {
        String errMsg = "";
        try {
            service.authenticateManager(VALID_NONEXISTENT_TOKEN);
        } catch(Exception e) {
            errMsg = e.getMessage();
        }
        assertEquals("You must be logged in as a Manager", errMsg);
    }

    @Test
    public void testAuthenticateNonExistentEmployee1() {
        String message = "";
        try {
            service.authenticateEmployee(INVALID_TOKEN);
        } catch(Exception e) {
            message = e.getMessage();
        }
        assertEquals("You must be logged in as an Employee or Manager", message);
    }

    @Test
    public void testAuthenticateNonExistentEmployee2() {
        String message = "";
        try {
            service.authenticateEmployee(VALID_NONEXISTENT_TOKEN);
        } catch(Exception e) {
            message = e.getMessage();
        }
        assertEquals("You must be logged in as an Employee or Manager", message);
    }

    @Test
    public void testAuthenticateNonExistentCustomer1() {
        String message = "";
        try {
            service.authenticateMonthlyCustomer(INVALID_TOKEN);
        } catch(Exception e) {
            message = e.getMessage();
        }
        assertEquals("You must be logged in as a Monthly Customer or Manager", message);
    }

    @Test
    public void testAuthenticateNonExistentCustomer2() {
        String message = "";
        try {
            service.authenticateMonthlyCustomer(VALID_NONEXISTENT_TOKEN);
        } catch(Exception e) {
            message = e.getMessage();
        }
        assertEquals("You must be logged in as a Monthly Customer or Manager", message);
    }

    @Test
    public void testAuthenticateManagerExpiredToken() {
        String message = "";
        try {
            service.authenticateManager(EXPIRED_TOKEN);
        } catch(Exception e) {
            message = e.getMessage();
        }
        assertEquals("You must be logged in as a Manager", message);
    }

    private Manager dummyManager() {
        Manager p = new Manager();
        p.setEmail(VALID_MANAGER_EMAIL);
        p.setPassword(VALID_PASSWORD);
        return p;
    }

    private Employee dummyEmployee() {
        Employee p = new Employee();
        p.setEmail(VALID_EMPLOYEE_EMAIL);
        p.setPassword(VALID_PASSWORD);
        return p;
    }

    private MonthlyCustomer dummyCustomer() {
        MonthlyCustomer p = new MonthlyCustomer();
        p.setEmail(VALID_CUSTOMER_EMAIL);
        p.setPassword(VALID_PASSWORD);
        return p;
    }
    
}

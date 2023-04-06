package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import ca.mcgill.ecse321.parkinglotsystem.dao.MonthlyCustomerRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;


@ExtendWith (MockitoExtension.class)
public class TestMonthlyCustomerService {
    @Mock
    private MonthlyCustomerRepository repo;
    @InjectMocks
    private MonthlyCustomerService service;

    private static final String VALID__EMAIL_INACTIVE = "abc@gmail";
    private static final String VALID__EMAIL_ACTIVE = "active_abc@gmail";
    private static final String INVALID__EMAIL = "invalid-Email";

    private static final String VALID__NAME="Louis";
    private static final String INVALID__NAME = "";
    private static final String NONEXISTING__NAME = "Lewis";

    private static final String VALID__PASSWORD="password1";
    private static final String INVALID__PASSWORD1 = "password";
    private static final String INVALID__PASSWORD2 = "12345678"; 
    private static final String INVALID__PASSWORD3 = "pw1";

    private static final String VALID__PHONE = "4387228120";
    private static final String INVALID__PHONE1 = "438722812a";
    private static final String INVALID__PHONE2 = "438722812";

    private static final String VALID_LICENSE_NUMBER = "ABC123";
    private static final String INVALID_LICENSE_NUMBER = "A1";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(repo.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<MonthlyCustomer> mas = new ArrayList<>();
            mas.add(dummy(VALID__EMAIL_ACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD,VALID_LICENSE_NUMBER));
            return mas;
        });


        lenient().when(repo.save(any(MonthlyCustomer.class))).thenAnswer((InvocationOnMock invocation) -> {
            MonthlyCustomer ma= invocation.getArgument(0);
            ma.setEmail(VALID__EMAIL_INACTIVE);
            ma.setName(VALID__NAME);
            ma.setPhone(VALID__PHONE);
            ma.setPassword(VALID__PASSWORD);
            ma.setLicenseNumber(VALID_LICENSE_NUMBER);
            return ma;
        });


        lenient().when(repo.findMonthlyCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID__EMAIL_ACTIVE)) {
                return dummy(VALID__EMAIL_ACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD,VALID_LICENSE_NUMBER);
            }
            return null;
        });

        lenient().when(repo.findMonthlyCustomerByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID__NAME)) {
                List<MonthlyCustomer> mas=new ArrayList<>();
                mas.add(dummy(VALID__EMAIL_ACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD,VALID_LICENSE_NUMBER));
                return mas;
            }
            return null;
        });

        lenient().when(repo.findMonthlyCustomerByPhone(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID__PHONE)) {
                List<MonthlyCustomer> mas=new ArrayList<>();
                mas.add(dummy(VALID__EMAIL_ACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD,VALID_LICENSE_NUMBER));
                return mas;
            }
            return null;
        });

        lenient().when(repo.findMonthlyCustomerByLicenseNumber(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID_LICENSE_NUMBER)) {
                List<MonthlyCustomer> mas=new ArrayList<>();
                mas.add(dummy(VALID__EMAIL_ACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD,VALID_LICENSE_NUMBER));
                return mas;
            }
            return null;
        });

    }




    @Test
    public void testCreateMonthlyCustomer() {
        MonthlyCustomer ma = service.createMonthlyCustomer(VALID__EMAIL_INACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD,VALID_LICENSE_NUMBER);
        assertNotNull(ma);
        var name = ma.getName();
        assertNotNull(name);
        assertEquals(VALID__NAME, ma.getName());
        var phone = ma.getPhone();
        assertNotNull(phone);
        assertEquals(VALID__PHONE, ma.getPhone());
        var password = ma.getPassword();
        assertNotNull(password);
        assertEquals(VALID__PASSWORD, ma.getPassword());
        var licenseNumber = ma.getLicenseNumber();
        assertNotNull(licenseNumber);
        assertEquals(VALID_LICENSE_NUMBER, ma.getLicenseNumber());
    }

    @Test
    public void testCreateMonthlyCustomerInvalidEmail1() {
        testCreateMonthlyCustomerFailure(
            INVALID__EMAIL, 
            VALID__NAME, 
            VALID__PHONE,
            VALID__PASSWORD,
            VALID_LICENSE_NUMBER,
            "Email must contain @ ! ");
    }

    @Test
    public void testCreateMonthlyCustomerInvalidEmail2() {
        testCreateMonthlyCustomerFailure(
            VALID__EMAIL_ACTIVE, 
            VALID__NAME, 
            VALID__PHONE,
            VALID__PASSWORD,
            VALID_LICENSE_NUMBER,
            "Cannot have the same email as an existing account! ");
    }

    @Test
    public void testCreateMonthlyCustomerInvalidName() {
        testCreateMonthlyCustomerFailure(
            VALID__EMAIL_INACTIVE, 
            INVALID__NAME, 
            VALID__PHONE,
            VALID__PASSWORD,
            VALID_LICENSE_NUMBER,
            "Name cannot be empty! ");
    }

    @Test
    public void testCreateMonthlyCustomerInvalidPassword1() {
        testCreateMonthlyCustomerFailure(
            VALID__EMAIL_INACTIVE, 
            VALID__NAME, 
            VALID__PHONE,
            INVALID__PASSWORD1,
            VALID_LICENSE_NUMBER,
            "Password must contain number! ");
    }

    @Test
    public void testCreateMonthlyCustomerInvalidPassword2() {
        testCreateMonthlyCustomerFailure(
            VALID__EMAIL_INACTIVE, 
            VALID__NAME, 
            VALID__PHONE,
            INVALID__PASSWORD2,
            VALID_LICENSE_NUMBER,
            "Password must contain letter! ");
    }

    @Test
    public void testCreateMonthlyCustomerInvalidPassword3() {
        testCreateMonthlyCustomerFailure(
            VALID__EMAIL_INACTIVE, 
            VALID__NAME, 
            VALID__PHONE,
            INVALID__PASSWORD3,
            VALID_LICENSE_NUMBER,
            "Password cannot be shorter than 8 digits! ");
    }

    @Test
    public void testCreateMonthlyCustomerInvalidPhone1() {
        testCreateMonthlyCustomerFailure(
            VALID__EMAIL_INACTIVE, 
            VALID__NAME, 
            INVALID__PHONE1,
            VALID__PASSWORD,
            VALID_LICENSE_NUMBER,
            "Phone cannot have non-number digits! ");
    }

    @Test
    public void testCreateMonthlyCustomerInvalidPhone2() {
        testCreateMonthlyCustomerFailure(
            VALID__EMAIL_INACTIVE, 
            VALID__NAME, 
            INVALID__PHONE2,
            VALID__PASSWORD,
            VALID_LICENSE_NUMBER,
            "Phone must have exactlty 10 digits! ");
    }

    @Test
    public void testCreateMonthlyCustomerInvalidLicenseNumber() {
        testCreateMonthlyCustomerFailure(
            VALID__EMAIL_INACTIVE, 
            VALID__NAME, 
            VALID__PHONE,
            VALID__PASSWORD,
            INVALID_LICENSE_NUMBER,
            "MonthlyCustomer license number cannot be shorter than 4 digits! ");
    }

    @Test
    public void testGetMonthlyCustomerValidEmail() {
        MonthlyCustomer ma = service.getMonthlyCustomerByEmail(VALID__EMAIL_ACTIVE);
        assertNotNull(ma);
        assertEquals(VALID__EMAIL_ACTIVE, ma.getEmail());
    }

    @Test
    public void testGetMonthlyCustomerInvalidEmail() {
        String errMsg="";
        MonthlyCustomer ma=null;
        try{
           ma = service.getMonthlyCustomerByEmail(INVALID__EMAIL);
        }catch(Exception e){
            errMsg=e.getMessage();
        }
        assertNull(ma);
        assertEquals("Invalid monthly customer email! ", errMsg);
    } 

    @Test
    public void testGetMonthlyCustomerValidName() {
        List<MonthlyCustomer> mas = service.getMonthlyCustomerByName(VALID__NAME);
        assertEquals(mas.size(),1);
        assertEquals(VALID__NAME, mas.get(0).getName());
    }

    @Test
    public void testGetMonthlyCustomerInvalidName() {
        List<MonthlyCustomer> mas = service.getMonthlyCustomerByName(NONEXISTING__NAME);
        assertEquals(mas,null);
    }

    @Test
    public void testGetMonthlyCustomerValidPhone() {
        List<MonthlyCustomer> mas = service.getMonthlyCustomerByPhone(VALID__PHONE);
        assertEquals(mas.size(),1);
        assertEquals(VALID__PHONE, mas.get(0).getPhone());
    }

    @Test
    public void testGetMonthlyCustomerInvalidPhone() {
        List<MonthlyCustomer> mas = service.getMonthlyCustomerByPhone(INVALID__PHONE1);
        assertEquals(mas,null);
    }

    @Test
    public void testGetMonthlyCustomerValidLicenseNumber() {
        List<MonthlyCustomer> mas = service.getMonthlyCustomerByLicenseNumber(VALID_LICENSE_NUMBER);
        assertEquals(mas.size(),1);
        assertEquals(VALID__PHONE, mas.get(0).getPhone());
    }

    @Test
    public void testGetMonthlyCustomerInvalidLicenseNumber() {
        List<MonthlyCustomer> mas = service.getMonthlyCustomerByLicenseNumber(INVALID_LICENSE_NUMBER);
        assertEquals(mas,null);
    }

    @Test
    public void testDeleteMonthlyCustomerInvalidEmail() {
        String errMsg="";
        MonthlyCustomer ma=null;
        try{
           ma = service.deleteMonthlyCustomerByEmail(INVALID__EMAIL);
        }catch(Exception e){
            errMsg=e.getMessage();
        }
        assertNull(ma);
        assertEquals("No monthly customer with that email was found!", errMsg);
    }

    @Test
    public void testUpdateMonthlyCustomerValid() {
        MonthlyCustomer ma = service.updateMonthlyCustomer(VALID__EMAIL_ACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD,VALID_LICENSE_NUMBER);
        assertNotNull(ma);
        var name = ma.getName();
        assertNotNull(name);
        assertEquals(VALID__NAME, ma.getName());
        var phone = ma.getPhone();
        assertNotNull(phone);
        assertEquals(VALID__PHONE, ma.getPhone());
        var password = ma.getPassword();
        assertNotNull(password);
        assertEquals(VALID__PASSWORD, ma.getPassword());
        var licenseNumber = ma.getLicenseNumber();
        assertNotNull(licenseNumber);
        assertEquals(VALID_LICENSE_NUMBER, ma.getLicenseNumber());
    }

    @Test
    public void testCreateMonthlyCustomerInvalidEmail() {
        String errMsg="";
        MonthlyCustomer ma=null;
        try{
           ma = service.updateMonthlyCustomer(VALID__EMAIL_INACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD,VALID_LICENSE_NUMBER);
        }catch(Exception e){
            errMsg=e.getMessage();
        }
        assertNull(ma);
        assertEquals("No monthly customer with that email exists!", errMsg);
    } 



    private void testCreateMonthlyCustomerFailure(String email, String name, String phone,String password,String licenseNumber,String message) {
        MonthlyCustomer ma = null;
        String errMsg = "";
        try {
            ma = service.createMonthlyCustomer(email, name,phone,password,licenseNumber);
        } catch(Exception e) {
            errMsg = e.getMessage();
        }
        assertEquals(message, errMsg);
        assertNull(ma);
    }

    private MonthlyCustomer dummy(String email, String name, String phone,String password,String licenseNumber) {
        MonthlyCustomer ma = new MonthlyCustomer();
        ma.setEmail(email);
        ma.setName(name);
        ma.setPhone(phone);
        ma.setPassword(password);
        ma.setLicenseNumber(licenseNumber);
        return ma;
    }

}
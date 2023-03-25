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

import ca.mcgill.ecse321.parkinglotsystem.dao.ManagerRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Manager;


@ExtendWith (MockitoExtension.class)
public class TestManagerService {
    @Mock
    private ManagerRepository repo;
    @InjectMocks
    private ManagerService service;

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

    @BeforeEach
    public void setMockOutput() {
        lenient().when(repo.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<Manager> mas = new ArrayList<>();
            mas.add(dummy(VALID__EMAIL_ACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD));
            return mas;
        });


        lenient().when(repo.save(any(Manager.class))).thenAnswer((InvocationOnMock invocation) -> {
            Manager ma= invocation.getArgument(0);
            ma.setEmail(VALID__EMAIL_INACTIVE);
            ma.setName(VALID__NAME);
            ma.setPhone(VALID__PHONE);
            ma.setPassword(VALID__PASSWORD);
            return ma;
        });


        lenient().when(repo.findManagerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID__EMAIL_ACTIVE)) {
                return dummy(VALID__EMAIL_ACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD);
            }
            return null;
        });

        lenient().when(repo.findManagerByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID__NAME)) {
                List<Manager> mas=new ArrayList<>();
                mas.add(dummy(VALID__EMAIL_ACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD));
                return mas;
            }
            return null;
        });

        lenient().when(repo.findManagerByPhone(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID__PHONE)) {
                List<Manager> mas=new ArrayList<>();
                mas.add(dummy(VALID__EMAIL_ACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD));
                return mas;
            }
            return null;
        });

    }




    @Test
    public void testCreateManager() {
        Manager ma = service.createManager(VALID__EMAIL_INACTIVE, VALID__NAME, VALID__PHONE, VALID__PASSWORD);
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
    }

    @Test
    public void testCreateManagerInvalidEmail1() {
        testCreateManagerFailure(
            INVALID__EMAIL, 
            VALID__NAME, 
            VALID__PHONE,
            VALID__PASSWORD,
            "Email must contain @ ! ");
    }

    @Test
    public void testCreateManagerInvalidEmail2() {
        testCreateManagerFailure(
            VALID__EMAIL_ACTIVE, 
            VALID__NAME, 
            VALID__PHONE,
            VALID__PASSWORD,
            "Cannot have the same email as an existing account! ");
    }

    @Test
    public void testCreateManagerInvalidName() {
        testCreateManagerFailure(
            VALID__EMAIL_INACTIVE, 
            INVALID__NAME, 
            VALID__PHONE,
            VALID__PASSWORD,
            "Name cannot be empty! ");
    }

    @Test
    public void testCreateManagerInvalidPassword1() {
        testCreateManagerFailure(
            VALID__EMAIL_INACTIVE, 
            VALID__NAME, 
            VALID__PHONE,
            INVALID__PASSWORD1,
            "Password must contain number! ");
    }

    @Test
    public void testCreateManagerInvalidPassword2() {
        testCreateManagerFailure(
            VALID__EMAIL_INACTIVE, 
            VALID__NAME, 
            VALID__PHONE,
            INVALID__PASSWORD2,
            "Password must contain letter! ");
    }

    @Test
    public void testCreateManagerInvalidPassword3() {
        testCreateManagerFailure(
            VALID__EMAIL_INACTIVE, 
            VALID__NAME, 
            VALID__PHONE,
            INVALID__PASSWORD3,
            "Password cannot be shorter than 8 digits! ");
    }

    @Test
    public void testCreateManagerInvalidPhone1() {
        testCreateManagerFailure(
            VALID__EMAIL_INACTIVE, 
            VALID__NAME, 
            INVALID__PHONE1,
            VALID__PASSWORD,
            "Phone cannot have non-number digits! ");
    }

    @Test
    public void testCreateManagerInvalidPhone2() {
        testCreateManagerFailure(
            VALID__EMAIL_INACTIVE, 
            VALID__NAME, 
            INVALID__PHONE2,
            VALID__PASSWORD,
            "Phone must have exactlty 10 digits! ");
    }

    @Test
    public void testGetManagerValidEmail() {
        Manager ma = service.getManagerByEmail(VALID__EMAIL_ACTIVE);
        assertNotNull(ma);
        assertEquals(VALID__EMAIL_ACTIVE, ma.getEmail());
    }

    @Test
    public void testGetManagerInvalidEmail() {
        String errMsg="";
        Manager ma=null;
        try{
           ma = service.getManagerByEmail(INVALID__EMAIL);
        }catch(Exception e){
            errMsg=e.getMessage();
        }
        assertNull(ma);
        assertEquals("Invalid manager email! ", errMsg);
    } 

    @Test
    public void testGetManagerValidName() {
        List<Manager> mas = service.getManagerByName(VALID__NAME);
        assertEquals(mas.size(),1);
        assertEquals(VALID__NAME, mas.get(0).getName());
    }

    @Test
    public void testGetManagerInvalidName() {
        List<Manager> mas = service.getManagerByName(NONEXISTING__NAME);
        assertEquals(mas,null);
    }

    @Test
    public void testGetManagerValidPhone() {
        List<Manager> mas = service.getManagerByPhone(VALID__PHONE);
        assertEquals(mas.size(),1);
        assertEquals(VALID__PHONE, mas.get(0).getPhone());
    }

    @Test
    public void testGetManagerInvalidPhone() {
        List<Manager> mas = service.getManagerByPhone(INVALID__PHONE1);
        assertEquals(mas,null);
    }




    private void testCreateManagerFailure(String email, String name, String phone,String password,String message) {
        Manager ma = null;
        String errMsg = "";
        try {
            ma = service.createManager(email, name,phone,password);
        } catch(Exception e) {
            errMsg = e.getMessage();
        }
        assertEquals(message, errMsg);
        assertNull(ma);
    }

    private Manager dummy(String email, String name, String phone,String password) {
        Manager ma = new Manager();
        ma.setEmail(email);
        ma.setName(name);
        ma.setPhone(phone);
        ma.setPassword(password);
        return ma;
    }

}
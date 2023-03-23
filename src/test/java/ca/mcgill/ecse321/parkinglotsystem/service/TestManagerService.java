package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.time.LocalDate;
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
    private static final String VALID__EMAIL_ACTIVE = "abc@gmail";
    private static final String INVALID__EMAIL = "invalid-Email";

    private static final String VALID__NAME="Louis";
    private static final String INVALID__NAME = "";

    private static final String VALID__PASSWORD="password1";
    private static final String INVALID__PASSWORD1 = "password";
    private static final String INVALID__PASSWORD2 = "12345678"; 
    private static final String INVALID__PASSWORD3 = "pw1";

    private static final String VALID__PHONE = "4387228120";
    private static final String VALID__PHONE1 = "438722812a";
    private static final String VALID__PHONE2 = "438722812";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(repo.save(any(Manager.class))).thenAnswer((InvocationOnMock invocation) -> {
            Manager ma= invocation.getArgument(0);
            ma.setEmail(VALID__EMAIL_INACTIVE);
            ma.setName(VALID__NAME);
            ma.setPhone(VALID__PHONE);
            ma.setPassword(VALID__PASSWORD);
            return ma;
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
            "Email must contain @ !");
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


}

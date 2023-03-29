package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
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

import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceReqWithAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

@ExtendWith(MockitoExtension.class)
public class ServiceReqWithAccountServiceTests {

    @Mock
    private ServiceReqWithAccountRepository repository;

    @Mock
    private MonthlyCustomerService monthlyCustomerService;

    @Mock
    private ServicesService serviceService;

    @InjectMocks
    private ServiceReqWithAccountService service;

    private static final int VALID_ID = 1;
    private static final int INVALID_ID = 2;
    private static final String VALID_CUSTOMER_EMAIL_ASSIGNED = "validEmailAssigned";
    private static final String VALID_CUSTOMER_EMAIL_NOTASSIGNED = "validEmailNotAssigned";
    private static final String VALID_SERVICE_DESC = "descValid";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(repository.findServiceReqWithAccountById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(VALID_ID)) {
                return dummy(VALID_ID, true, dummyService(VALID_SERVICE_DESC),
                        dummyCustomer(VALID_CUSTOMER_EMAIL_ASSIGNED));
            }
            return null;
        });

        lenient().when(repository.findServiceReqWithAccountByIsAssigned(anyBoolean()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<ServiceReqWithAccount> srwas = new ArrayList<>();
                    boolean arg = invocation.getArgument(0);
                    if(!arg) {
                        return srwas;
                    }
                    srwas.add(dummy(VALID_ID, invocation.getArgument(0), dummyService(VALID_SERVICE_DESC),
                            dummyCustomer(VALID_CUSTOMER_EMAIL_ASSIGNED)));
                    return srwas;
                });

        lenient().when(repository.findServiceReqWithAccountByCustomer(any(MonthlyCustomer.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<ServiceReqWithAccount> srwas = new ArrayList<>();
                    MonthlyCustomer customer = invocation.getArgument(0);
                    if (customer.getEmail().equals(VALID_CUSTOMER_EMAIL_ASSIGNED)) {
                        srwas.add(dummy(VALID_ID, true, dummyService(VALID_SERVICE_DESC), customer));
                    } else if (customer.getEmail().equals(VALID_CUSTOMER_EMAIL_NOTASSIGNED)) {
                        srwas.add(dummy(VALID_ID, false, dummyService(VALID_SERVICE_DESC), customer));
                    }
                    return srwas;
                });

        lenient().when(repository.findServiceReqWithAccountByService(any(Service.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<ServiceReqWithAccount> srwas = new ArrayList<>();
                    Service service = invocation.getArgument(0);
                    srwas.add(dummy(VALID_ID, true, service, dummyCustomer(VALID_CUSTOMER_EMAIL_ASSIGNED)));
                    return srwas;
                });

        lenient().when(repository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<ServiceReqWithAccount> srwas = new ArrayList<>();
            srwas.add(dummy(VALID_ID, true, null, null));
            srwas.add(dummy(VALID_ID + 1, true, null, null));
            return srwas;
        });

        lenient().when(repository.save(any(ServiceReqWithAccount.class))).thenAnswer((InvocationOnMock invocation) -> {
            ServiceReqWithAccount srwa = invocation.getArgument(0);
            srwa.setId(VALID_ID);
            return srwa;
        });

        lenient().when(serviceService.getServiceByDescription(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String arg = invocation.getArgument(0);
            if(arg.equals(VALID_SERVICE_DESC)) {
                return dummyService(VALID_SERVICE_DESC);
            }
            return null;
        });

        lenient().when(monthlyCustomerService.getMonthlyCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            return dummyCustomer(invocation.getArgument(0));
        });
    }

    @Test
    public void testCreateServiceReqWithAccount() {
        ServiceReqWithAccount srwa = service.createServiceReqWithAccount(VALID_CUSTOMER_EMAIL_ASSIGNED,
                VALID_SERVICE_DESC);
        assertNotNull(srwa);
        assertEquals(VALID_ID, srwa.getId());
        assertEquals(true, srwa.getIsAssigned());
        assertEquals(VALID_CUSTOMER_EMAIL_ASSIGNED,
                srwa.getCustomer().getEmail());
        assertEquals(VALID_SERVICE_DESC, srwa.getService().getDescription());
    }

    @Test
    public void testGetById() {
        ServiceReqWithAccount srwa = service.getServiceReqWithAccountById(VALID_ID);
        assertNotNull(srwa);
        assertEquals(VALID_ID, srwa.getId());
    }

    @Test
    public void testGetByInvalidId() {
        ServiceReqWithAccount srwa = null;
        String errMsg = "";
        try {
            srwa = service.getServiceReqWithAccountById(INVALID_ID);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(srwa);
        assertEquals("No serviceRequest Id", errMsg);
    }

    @Test
    public void testGetServiceReqWithAccountByIsAssigned() {
        List<ServiceReqWithAccount> srwas = service.getServiceReqWithAccountByIsAssigned(true);
        assertNotNull(srwas);
        for (ServiceReqWithAccount srwa : srwas) {
            assertNotNull(srwa);
            assertEquals(VALID_ID, srwa.getId());
            assertEquals(true, srwa.getIsAssigned());
            assertEquals(VALID_SERVICE_DESC, srwa.getService().getDescription());
        }
    }

    @Test
    public void testGetServiceReqWithAccountByInvalidIsAssigned() {
        List<ServiceReqWithAccount> srwas = null;
        String errMsg = "";
        try {
            srwas = service.getServiceReqWithAccountByIsAssigned(false);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(srwas);
        assertEquals("No serviceRequest with such IsAssigned", errMsg);
    }

    @Test
    public void testGetServiceReqWithAccountByCustomer() {
        List<ServiceReqWithAccount> srwas = service.getServiceReqWithAccountByCustomer(VALID_CUSTOMER_EMAIL_ASSIGNED);
        assertNotNull(srwas);
        for (ServiceReqWithAccount srwa : srwas) {
            assertNotNull(srwa);
            assertEquals(VALID_ID, srwa.getId());
            assertEquals(true, srwa.getIsAssigned());
            assertEquals(VALID_CUSTOMER_EMAIL_ASSIGNED,
                    srwa.getCustomer().getEmail());
            assertEquals(VALID_SERVICE_DESC, srwa.getService().getDescription());
        }
    }

    @Test
    public void testGetServiceReqWithAccountByNotAssignedCustomer() {
        List<ServiceReqWithAccount> srwas = service
                .getServiceReqWithAccountByCustomer(VALID_CUSTOMER_EMAIL_NOTASSIGNED);
        assertNotNull(srwas);
        for (ServiceReqWithAccount srwa : srwas) {
            assertNotNull(srwa);
            assertEquals(VALID_ID, srwa.getId());
            assertEquals(false, srwa.getIsAssigned());
            assertEquals(VALID_CUSTOMER_EMAIL_NOTASSIGNED,
                    srwa.getCustomer().getEmail());
            assertEquals(VALID_SERVICE_DESC, srwa.getService().getDescription());
        }
    }

    @Test
    public void testGetAll() {
        List<ServiceReqWithAccount> srwas = service.getAll();
        assertNotNull(srwas);
        assertTrue(srwas.size() > 0);
    }

    @Test
    public void testUpdateIsAssignedById() {
        ServiceReqWithAccount srwa = service.updateIsAssignedById(VALID_ID, false);
        assertNotNull(srwa);
        assertEquals(VALID_ID, srwa.getId());
        assertEquals(false, srwa.getIsAssigned());
    }

    @Test
    public void testUpdateIsAssignedByNonExistingId() {
        ServiceReqWithAccount srwa = null;
        String errMsg = "";
        try {
            srwa = service.updateIsAssignedById(INVALID_ID, false);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(srwa);
        assertEquals("The ServiceReqWithAccount Id does not exist", errMsg);
    }

    private MonthlyCustomer dummyCustomer(String email) {
        MonthlyCustomer customer = new MonthlyCustomer();
        customer.setEmail(email);
        customer.setLicenseNumber("someLicense");
        customer.setName("Chenxin");
        customer.setPassword("123");
        customer.setPhone("1234567890");
        return customer;
    }

    private Service dummyService(String desc) {
        Service service = new Service();
        service.setPrice(100);
        service.setDescription(desc);
        return service;
    }

    private ServiceReqWithAccount dummy(int id, boolean isAssigned, Service service, MonthlyCustomer monthlyCustomer) {
        ServiceReqWithAccount srwa = new ServiceReqWithAccount();
        srwa.setId(id);
        srwa.setIsAssigned(isAssigned);
        srwa.setCustomer(monthlyCustomer);
        srwa.setService(service);
        return srwa;
    }

}

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

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceReqWithoutAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceRequestRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithoutAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

@ExtendWith(MockitoExtension.class)
public class ServiceReqWithoutAccountServiceTests {

    @Mock
    private ServiceReqWithoutAccountRepository repository;

    @Mock
    private ServiceRequestRepository serviceRequestRepository;

    @Mock
    private ServicesService serviceService;



    @InjectMocks
    private ServiceReqWithoutAccountService service;
    //private ServiceRequestService serviceRequestService;

    private static final int VALID_ID = 1;
    private static final int INVALID_ID = 2;
    private static final String VALID_LICENSE_ASSIGNED = "validEmailAssigned";
    private static final String VALID_LICENSE_NOTASSIGNED = "validEmailNotAssigned";

    private static final String VALID_SERVICE_DESC = "Test description";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(repository.findServiceReqWithoutAccountById(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(VALID_ID)) {
                        return dummy(VALID_ID, true, dummyService(VALID_SERVICE_DESC),
                                VALID_LICENSE_ASSIGNED);
                    }
                    return null;
                });

        lenient().when(repository.findServiceReqWithoutAccountByIsAssigned(anyBoolean()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<ServiceReqWithoutAccount> srwas = new ArrayList<>();
                    boolean arg = invocation.getArgument(0);
                    if(!arg) {
                        return srwas;
                    }
                    srwas.add(dummy(VALID_ID, invocation.getArgument(0), dummyService(VALID_SERVICE_DESC),
                            VALID_LICENSE_ASSIGNED));
                    return srwas;
                });

        lenient().when(repository.findServiceReqWithoutAccountByLicenseNumber(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<ServiceReqWithoutAccount> srwas = new ArrayList<>();
                    String licnese = invocation.getArgument(0);
                    if (licnese.equals(VALID_LICENSE_ASSIGNED)) {
                        srwas.add(dummy(VALID_ID, true, dummyService(VALID_SERVICE_DESC), licnese));
                    } else if (licnese.equals(VALID_LICENSE_NOTASSIGNED)) {
                        srwas.add(dummy(VALID_ID, false, dummyService(VALID_SERVICE_DESC), licnese));
                    }
                    return srwas;
                });

        lenient().when(repository.findServiceReqWithoutAccountByService(any(Service.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<ServiceReqWithoutAccount> srwas = new ArrayList<>();
                    Service service = invocation.getArgument(0);
                    srwas.add(dummy(VALID_ID, true, service, VALID_LICENSE_ASSIGNED));
                    return srwas;
                });

        lenient().when(repository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<ServiceReqWithoutAccount> srwas = new ArrayList<>();
            srwas.add(dummy(VALID_ID, true, null, null));
            srwas.add(dummy(VALID_ID + 1, true, null, null));
            return srwas;
        });

        lenient().when(serviceRequestRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<ServiceRequest> serviceRequestList = new ArrayList<>();
            serviceRequestList.add(dummy(VALID_ID, true, null, null));
            serviceRequestList.add(dummy(VALID_ID + 1, true, null, null));
            return serviceRequestList;
        });

        lenient().when(repository.save(any(ServiceReqWithoutAccount.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    ServiceReqWithoutAccount srwa = invocation.getArgument(0);
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

        lenient().when(serviceRequestRepository.save(any(ServiceReqWithoutAccount.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    ServiceRequest ser = invocation.getArgument(0);
                    ser.setId(VALID_ID);
                    return ser;
                });
    }

    @Test
    public void testCreateServiceReqWithAccount() {
        ServiceReqWithoutAccount srwa = service.createServiceReqWithoutAccount(VALID_LICENSE_ASSIGNED,
                VALID_SERVICE_DESC);
        assertNotNull(srwa);
        assertEquals(VALID_ID, srwa.getId());
        assertEquals(true, srwa.getIsAssigned());
        assertEquals(VALID_LICENSE_ASSIGNED, srwa.getLicenseNumber());
        assertEquals(VALID_SERVICE_DESC, srwa.getService().getDescription());
    }


    @Test
    public void testCreateServiceReqWithAccountFail() {
        String error = "";
        ServiceReqWithoutAccount srwa = null;
        try {
            srwa = service.createServiceReqWithoutAccount("",
                VALID_SERVICE_DESC);
        } catch (CustomException e) {
			error = e.getMessage();
		}
        assertNull(srwa);
        assertEquals("License Number empty! ", error);
    }

    @Test
    public void testGetById() {
        ServiceReqWithoutAccount srwa = service.getServiceReqWithoutAccountById(VALID_ID);
        assertNotNull(srwa);
        assertEquals(VALID_ID, srwa.getId());
    }

    @Test
    public void testGetByInvalidId() {
        ServiceReqWithoutAccount srwa = null;
        String errMsg = "";
        try {
            srwa = service.getServiceReqWithoutAccountById(INVALID_ID);
        } catch (CustomException e) {
            errMsg = e.getMessage();
        }
        assertNull(srwa);
        assertEquals("No serviceRequest Id", errMsg);
    }

    @Test
    public void testGetServiceReqWithAccountByIsAssigned() {
        List<ServiceReqWithoutAccount> srwas = service.getServiceReqWithoutAccountByIsAssigned(true);
        assertNotNull(srwas);
        for (ServiceReqWithoutAccount srwa : srwas) {
            assertNotNull(srwa);
            assertEquals(VALID_ID, srwa.getId());
            assertEquals(true, srwa.getIsAssigned());
            assertEquals(VALID_LICENSE_ASSIGNED, srwa.getLicenseNumber());
            assertEquals(VALID_SERVICE_DESC, srwa.getService().getDescription());
        }
    }

    @Test
    public void testGetServiceReqWithAccountByInvalidIsAssigned() {
        List<ServiceReqWithoutAccount> srwas = null;
        String errMsg = "";
        try {
            srwas = service.getServiceReqWithoutAccountByIsAssigned(false);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(srwas);
        assertEquals("No serviceRequest with such IsAssigned", errMsg);
    }

    @Test
    public void testGetServiceReqWithAccountByLicense() {
        List<ServiceReqWithoutAccount> srwas = service
                .findServiceReqWithoutAccountByLicenseNumber(VALID_LICENSE_ASSIGNED);
        assertNotNull(srwas);
        for (ServiceReqWithoutAccount srwa : srwas) {
            assertNotNull(srwa);
            assertEquals(VALID_ID, srwa.getId());
            assertEquals(true, srwa.getIsAssigned());
            assertEquals(VALID_LICENSE_ASSIGNED, srwa.getLicenseNumber());
            assertEquals(VALID_SERVICE_DESC, srwa.getService().getDescription());
        }
    }

    @Test
    public void testGetServiceReqWithAccountByNotAssignedCustomer() {
        List<ServiceReqWithoutAccount> srwas = service
                .findServiceReqWithoutAccountByLicenseNumber(VALID_LICENSE_NOTASSIGNED);
        assertNotNull(srwas);
        for (ServiceReqWithoutAccount srwa : srwas) {
            assertNotNull(srwa);
            assertEquals(VALID_ID, srwa.getId());
            assertEquals(false, srwa.getIsAssigned());
            assertEquals(VALID_LICENSE_NOTASSIGNED, srwa.getLicenseNumber());
            assertEquals(VALID_SERVICE_DESC, srwa.getService().getDescription());
        }
    }

    @Test
    public void testGetAll() {
        List<ServiceReqWithoutAccount> srwas = service.getAll();
        assertNotNull(srwas);
        assertTrue(srwas.size() > 0);
    }

    // @Test
    // public void testGetServiceRequestById() {
    //     ServiceRequest ser = serviceRequestService.getServiceRequestById(VALID_ID);
    //     assertNotNull(ser);
    //     assertEquals(VALID_ID, ser.getId());
    // }

    // @Test
    // public void testGetServiceRequestByIsAssigned() {
    //     List<ServiceRequest> serviceRequestList = serviceRequestService.getServiceRequestByIsAssigned(true);
    //     assertNotNull(serviceRequestList);
    //     for (ServiceRequest ser : serviceRequestList) {
    //         assertNotNull(ser);
    //         assertEquals(VALID_ID, ser.getId());
    //         assertEquals(true, ser.getIsAssigned());
    //         assertEquals(serviceService.getServiceByPrice(VALID_SERVICE_PRICE).get(0), ser.getService());
    //     }
    // }

    // @Test
    // public void testGetServiceRequestByService() {
    //     List<ServiceRequest> serviceRequestList = serviceRequestService.getServiceRequestByServices(dummyService(VALID_SERVICE_PRICE));
    //     assertNotNull(serviceRequestList);
    //     for (ServiceRequest ser : serviceRequestList) {
    //         assertNotNull(ser);
    //         assertEquals(VALID_ID, ser.getId());
    //         assertEquals(true, ser.getIsAssigned());
    //         assertEquals(serviceService.getServiceByPrice(VALID_SERVICE_PRICE).get(0), ser.getService());
    //     }
    // }

    // @Test
    // public void testGetAllServiceRequest() {
    //     List<ServiceRequest> serviceRequestList = serviceRequestService.getAllServiceRequest();
    //     assertNotNull(serviceRequestList);
    //     assertTrue(serviceRequestList.size() > 0);
    // }

    @Test
    public void testUpdateIsAssignedById() {
        ServiceReqWithoutAccount srwa = service.updateIsAssignedById(VALID_ID, false);
        assertNotNull(srwa);
        assertEquals(VALID_ID, srwa.getId());
        assertEquals(false, srwa.getIsAssigned());
    }

    @Test
    public void testUpdateIsAssignedByNonExistingId() {
        ServiceReqWithoutAccount srwa = null;
        String errMsg = "";
        try {
            srwa = service.updateIsAssignedById(INVALID_ID, false);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(srwa);
        assertEquals("The ServiceReqWithoutAccount Id does not exist", errMsg);
    }

    private Service dummyService(String desc) {
        Service service = new Service();
        service.setPrice(100);
        service.setDescription(desc);
        return service;
    }

    private ServiceReqWithoutAccount dummy(int id, boolean isAssigned, Service service, String license) {
        ServiceReqWithoutAccount srwa = new ServiceReqWithoutAccount();
        srwa.setId(id);
        srwa.setIsAssigned(isAssigned);
        srwa.setLicenseNumber(license);
        srwa.setService(service);
        return srwa;
    }

}

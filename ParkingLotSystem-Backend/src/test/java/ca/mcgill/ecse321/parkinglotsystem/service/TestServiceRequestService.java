package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceRequestRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithoutAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;

@ExtendWith(MockitoExtension.class)
public class TestServiceRequestService {

    @Mock
    private ServiceRequestRepository repository;

    @Mock
    private ServicesService serviceService;

    @InjectMocks
    private ServiceRequestService serviceRequestService;

    private static final int VALID_ID = 1;
    private static final String VALID_LICENSE_ASSIGNED = "validEmailAssigned";
    private static final String VALID_SERVICE_DESC = "Test description";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(repository.findServiceRequestById(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(VALID_ID)) {
                        return dummy(VALID_ID, true, dummyService(VALID_SERVICE_DESC),
                                VALID_LICENSE_ASSIGNED);
                    }
                    return null;
                });

        lenient().when(repository.findServiceRequestByIsAssigned(anyBoolean()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<ServiceRequest> srwas = new ArrayList<>();
                    boolean arg = invocation.getArgument(0);
                    if(!arg) {
                        return srwas;
                    }
                    srwas.add(dummy(VALID_ID, invocation.getArgument(0), dummyService(VALID_SERVICE_DESC),
                            VALID_LICENSE_ASSIGNED));
                    return srwas;
                });

        lenient().when(repository.findServiceRequestByService(any(Service.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<ServiceRequest> srwas = new ArrayList<>();
                    Service service = invocation.getArgument(0);
                    srwas.add(dummy(VALID_ID, true, service, VALID_LICENSE_ASSIGNED));
                    return srwas;
                });

        lenient().when(repository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<ServiceRequest> srwas = new ArrayList<>();
            srwas.add(dummy(VALID_ID, true, null, null));
            srwas.add(dummy(VALID_ID + 1, true, null, null));
            return srwas;
        });

        lenient().when(repository.save(any(ServiceRequest.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    ServiceRequest srwa = invocation.getArgument(0);
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
    }
    
    @Test
    public void testGetServiceRequestById() {
        ServiceRequest ser = serviceRequestService.getServiceRequestById(VALID_ID);
        assertNotNull(ser);
        assertEquals(VALID_ID, ser.getId());
    }

    @Test
    public void testGetServiceRequestByIsAssigned() {
        List<ServiceRequest> serviceRequestList = serviceRequestService.getServiceRequestByIsAssigned(true);
        assertNotNull(serviceRequestList);
        for (ServiceRequest ser : serviceRequestList) {
            assertNotNull(ser);
            assertEquals(VALID_ID, ser.getId());
            assertEquals(true, ser.getIsAssigned());
        }
    }

    @Test
    public void testGetServiceRequestByService() {
        List<ServiceRequest> serviceRequestList = serviceRequestService.getServiceRequestByServices("test");
        assertNotNull(serviceRequestList);
        for (ServiceRequest ser : serviceRequestList) {
            assertNotNull(ser);
            assertEquals(VALID_ID, ser.getId());
            assertEquals(true, ser.getIsAssigned());
        }
    }

    @Test
    public void testGetAllServiceRequest() {
        List<ServiceRequest> serviceRequestList = serviceRequestService.getAllServiceRequest();
        assertNotNull(serviceRequestList);
        assertTrue(serviceRequestList.size() > 0);
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

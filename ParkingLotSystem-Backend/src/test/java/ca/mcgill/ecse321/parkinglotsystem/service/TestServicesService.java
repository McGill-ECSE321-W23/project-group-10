package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestServicesService {
    @Mock
    private ServiceRepository servicesRepository;
    @InjectMocks
    private ServicesService service;

    private static final String VALID__DESCRIPTION = "This is an valid description.";
    private static final String VALID__DESCRIPTION_ACTIVE = "This is an valid description activation.";
    private static final String INVALID__DESCRIPTION = "";
    private static final String NON_EXIST__DESCRIPTION = "feeling lucky";

    private static final int VALID__PRICE = 100;
    private static final int VALID__PRICE_UPDATE = 80;
    private static final int INVALID__PRICE_UPDATE = -80;
    private static final int INVALID__PRICE = -50;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(servicesRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<Service> serviceList = new ArrayList<>();
            serviceList.add(dummy(VALID__DESCRIPTION, VALID__PRICE));
            return serviceList;
        });


        lenient().when(servicesRepository.save(any(Service.class))).thenAnswer((InvocationOnMock invocation) -> {
            Service se = invocation.getArgument(0);
            //se.setDescription(VALID__DESCRIPTION);
            //se.setPrice(VALID__PRICE);
            return se;
        });


        lenient().when(servicesRepository.findServiceByDescription(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(VALID__DESCRIPTION)) {
                return dummy(VALID__DESCRIPTION, VALID__PRICE);
            }
            return null;
        });

        lenient().when(servicesRepository.findServiceByPrice(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(VALID__PRICE)) {
                List<Service> serviceList = new ArrayList<>();
                serviceList.add(dummy(VALID__DESCRIPTION, VALID__PRICE));
                return serviceList;
            }
            return null;
        });
    }

    @Test
    public void testCreateService() {
        Service ser = service.createService(VALID__DESCRIPTION_ACTIVE, VALID__PRICE);
        assertNotNull(ser);
        var description = ser.getDescription();
        assertNotNull(description);
        assertEquals(VALID__DESCRIPTION_ACTIVE, ser.getDescription());
        assertEquals(VALID__PRICE, ser.getPrice());
    }

    @Test
    public void testCreateServiceInvalidPrice() {
        testCreateServiceFailure(
                VALID__DESCRIPTION_ACTIVE,
                INVALID__PRICE,
                "Price input cannot be empty or less than zero!");
    }

    @Test
    public void testCreateServiceInvalidDescription() {
        testCreateServiceFailure(
                INVALID__DESCRIPTION,
                VALID__PRICE,
                "Service description cannot be empty!");
    }

    @Test
    public void testCreateExistingService() {
        testCreateServiceFailure(VALID__DESCRIPTION, VALID__PRICE, "Service already exists!");
    }

    @Test
    public void testGetServiceValidDescription() {
        Service ser = service.getServiceByDescription(VALID__DESCRIPTION);
        assertNotNull(ser);
        assertEquals(VALID__DESCRIPTION, ser.getDescription());
    }

    @Test
    public void testGetServiceInValidDescription1() {
        String errMsg = "";
        try {
            service.getServiceByDescription(INVALID__DESCRIPTION);
        } catch(Exception e) {
            errMsg = e.getMessage();
        }
        assertEquals("No service found.", errMsg);
    }

    @Test
    public void testGetServiceInValidDescription2() {
        String errMsg = "";
        try {
            service.getServiceByDescription(NON_EXIST__DESCRIPTION);
        } catch(Exception e) {
            errMsg = e.getMessage();
        }
        assertEquals("No service found.", errMsg);
    }

    @Test
    public void testGetServiceValidPrice() {
        List<Service> serviceList = service.getServiceByPrice(VALID__PRICE);
        assertEquals(serviceList.size(), 1);
        assertEquals(VALID__PRICE, serviceList.get(0).getPrice());
    }

    @Test
    public void testGetServiceInValidPrice() {
        List<Service> serviceList = service.getServiceByPrice(INVALID__PRICE);
        assertNull(serviceList);
    }

    @Test
    public void testGetAll() {
        List<Service> serviceList = service.getAllServices();
        assertEquals(serviceList.size(), 1);
    }

    @Test
    public void testDeleteService() {
        String error = "";
        try {
            service.deleteServiceByDescription(VALID__DESCRIPTION);
        }catch (Exception e) {
            // Check that no error occurred
            error = e.getMessage();
        }
        assertEquals("", error);
        //assertNull(servicesRepository.findServiceByDescription(VALID__DESCRIPTION));

    }

    @Test
    public void testDeleteNonExistentService() {
        String error = "";
        try {
            service.deleteServiceByDescription(VALID__DESCRIPTION_ACTIVE);
        }catch (Exception e) {
            // Check that no error occurred
            error = e.getMessage();
        }
        assertEquals("No service with that description was found!", error);
        //assertNull(servicesRepository.findServiceByDescription(VALID__DESCRIPTION));

    }

    @Test
    public void testUpdateServiceValid() {
        Service ser = service.updateService(VALID__DESCRIPTION, VALID__PRICE_UPDATE);
        assertEquals(ser.getPrice(), VALID__PRICE_UPDATE);
    }

    @Test
    public void testUpdateServiceInvalid() {
        String errMsg = "";
        try {
            Service ser = service.updateService(VALID__DESCRIPTION, INVALID__PRICE_UPDATE);
            assertEquals(ser.getPrice(), VALID__PRICE);
        } catch(Exception e) {
            errMsg = e.getMessage();
        }
        assertEquals(errMsg, "Price input cannot be empty or less than zero!");
    }

    @Test
    public void testUpdateNonExistentService() {
        String errMsg = "";
        try {
            Service ser = service.updateService(VALID__DESCRIPTION_ACTIVE, VALID__PRICE_UPDATE);
            assertEquals(ser.getPrice(), VALID__PRICE);
        } catch(Exception e) {
            errMsg = e.getMessage();
        }
        assertEquals(errMsg, "No service with that description exists!");
    }

    private void testCreateServiceFailure(String description, int price, String message) {
        Service ser = null;
        String errMsg = "";
        try {
            ser = service.createService(description, price);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertEquals(message, errMsg);
        assertNull(ser);
    }

    private Service dummy(String description, int price) {
        Service ser = new Service();
        ser.setDescription(description);
        ser.setPrice(price);
        return ser;
    }
}


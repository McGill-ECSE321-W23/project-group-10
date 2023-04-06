package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dao.PaymentServiceRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceRequestRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith (MockitoExtension.class)
public class TestPaymentServiceService {

    @Mock
    private PaymentServiceRepository paymentServiceRepository;
    @Mock
    private ServiceRequestRepository serviceRequestRepository;
    @InjectMocks
    private PaymentServiceService service;

    private static final int VALID__ID = 100;
    private static final int INVALID__ID = -100;
    private static final int NON_EXIST__ID = 75;

    private static final double VALID__AMOUNT = 100.5;
    private static final double VALID__AMOUNT_UPDATE = 200.6;
    private static final double INVALID__AMOUNT = -100.5;
    //creation 1648094400
    private static final Timestamp VALID__DATETIME = new Timestamp(1650000000);
    private static final Timestamp VALID__DATETIME__UPDATE = new Timestamp(1651000000);
    private static final Timestamp INVALID_PAST__DATETIME = new Timestamp(1600000000);
    private static final Timestamp INVALID_FUTURE__DATETIME = new Timestamp(System.currentTimeMillis()+10000);

    private static final int SERVICE_REQUEST__ID = 10;
    private static final boolean SERVICE__IS_ASSIGNED = true;
    private static final String SERVICE__LICENSE_NUMBER = "123456";
    private static final int SERVICE__PRICE = 100;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(paymentServiceRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<PaymentService> paymentServiceList = new ArrayList<>();
            paymentServiceList.add(dummyPaymentService(VALID__ID, VALID__AMOUNT, VALID__DATETIME, dummyServiceReq(
                SERVICE_REQUEST__ID, SERVICE__IS_ASSIGNED, SERVICE__LICENSE_NUMBER, dummyService(SERVICE__PRICE))));
            return paymentServiceList;
        });


        lenient().when(paymentServiceRepository.save(any(PaymentService.class))).thenAnswer((InvocationOnMock invocation) -> {
            PaymentService pa= invocation.getArgument(0);
            //pa.setId(VALID__ID);
            //pa.setAmount(VALID__AMOUNT);
            //pa.setDateTime(VALID__DATETIME);
            //pa.setServiceReq(dummyServiceReq(SERVICE_REQUEST__ID, SERVICE__IS_ASSIGNED, SERVICE__LICENSE_NUMBER, dummyService(SERVICE__PRICE)));
            return pa;
        });


        lenient().when(paymentServiceRepository.findPaymentServiceById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            int arg = invocation.getArgument(0);
            if(arg == VALID__ID) {
                return dummyPaymentService(VALID__ID, VALID__AMOUNT, VALID__DATETIME, dummyServiceReq(
                    SERVICE_REQUEST__ID, SERVICE__IS_ASSIGNED, SERVICE__LICENSE_NUMBER, dummyService(SERVICE__PRICE)));
            }
            return null;
        });

        lenient().when(paymentServiceRepository.findPaymentServiceByAmount(anyDouble())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID__AMOUNT)) {
                List<PaymentService> paymentServiceList=new ArrayList<>();
                paymentServiceList.add(dummyPaymentService(VALID__ID, VALID__AMOUNT, VALID__DATETIME, dummyServiceReq(
                    SERVICE_REQUEST__ID, SERVICE__IS_ASSIGNED, SERVICE__LICENSE_NUMBER, dummyService(SERVICE__PRICE))));
                return paymentServiceList;
            }
            return null;
        });

        lenient().when(paymentServiceRepository.findPaymentServiceByDateTime(any(java.sql.Timestamp.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID__DATETIME)) {
                List<PaymentService> paymentServiceList=new ArrayList<>();
                paymentServiceList.add(dummyPaymentService(VALID__ID, VALID__AMOUNT, VALID__DATETIME, dummyServiceReq(SERVICE_REQUEST__ID, SERVICE__IS_ASSIGNED, SERVICE__LICENSE_NUMBER, dummyService(SERVICE__PRICE))));
                return paymentServiceList;
            }
            return null;
        });

        lenient().when(serviceRequestRepository.findServiceRequestById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(SERVICE_REQUEST__ID)) {
                ServiceRequest serviceRequest_dummy = dummyServiceReq(SERVICE_REQUEST__ID, SERVICE__IS_ASSIGNED, SERVICE__LICENSE_NUMBER, dummyService(SERVICE__PRICE));
                return serviceRequest_dummy;
            }
            return null;
        });

        lenient().when(paymentServiceRepository.findPaymentServiceByServiceReq(any(ServiceRequest.class))).thenAnswer((InvocationOnMock invocation) -> {
            
            List<PaymentService> paymentServices = new ArrayList<>();
            paymentServices.add(dummyPaymentService(VALID__ID, VALID__AMOUNT, VALID__DATETIME, dummyServiceReq(
                SERVICE_REQUEST__ID, SERVICE__IS_ASSIGNED, SERVICE__LICENSE_NUMBER, dummyService(SERVICE__PRICE))));

            return paymentServices;
            // ServiceRequest incomingRequest = invocation.getArgument(0);
            // if(incomingRequest.getId() == SERVICE_REQUEST__ID) {
            //     return dummyPaymentService(VALID__ID, VALID__AMOUNT, VALID__DATETIME, incomingRequest);
            // }
            // return null;
        });

    }

    @Test
    public void testCreatePaymentService() {
        //ServiceRequest serviceRequest = dummyServiceReq(SERVICE_REQUEST__ID, SERVICE__IS_ASSIGNED, SERVICE__LICENSE_NUMBER, dummyService(SERVICE__PRICE));
        // serviceRequestRepository.save(serviceRequest);
        PaymentService pa = service.createPaymentService(SERVICE_REQUEST__ID);
        assertNotNull(pa);
        var amount = pa.getAmount();
        assertNotNull(amount);
        var dateTime = pa.getDateTime();
        assertNotNull(dateTime);
        ServiceRequest serviceReq = pa.getServiceReq();
        assertNotNull(serviceReq.getId());
        assertNotNull(serviceReq.getIsAssigned());
        assertNotNull(serviceReq.getService().getDescription());
        assertNotNull(serviceReq.getService().getPrice());
        assertEquals(serviceReq.getId(), SERVICE_REQUEST__ID);
        assertEquals(serviceReq.getIsAssigned(), SERVICE__IS_ASSIGNED);
        assertEquals(serviceReq.getService().getPrice(), SERVICE__PRICE);
    }

    @Test
    public void testGetPaymentServiceValidId() {
        PaymentService pa = service.getPaymentServiceById(VALID__ID);
        assertNotNull(pa);
        assertEquals(VALID__ID, pa.getId());
    }

    @Test
    public void testGetPaymentServiceInvalidId1() {
        PaymentService pa = service.getPaymentServiceById(INVALID__ID);
        assertNull(pa);
    }

    @Test
    public void testGetPaymentServiceInvalidId2() {
        PaymentService pa = service.getPaymentServiceById(NON_EXIST__ID);
        assertNull(pa);
    }

    @Test
    public void testGetPaymentServiceValidAmount() {
        List<PaymentService> paymentServiceList = service.getPaymentServiceByAmount(VALID__AMOUNT);
        assertEquals(paymentServiceList.size(),1);
        assertEquals(VALID__AMOUNT, paymentServiceList.get(0).getAmount());
    }

    @Test
    public void testGetPaymentServiceInvalidAmount() {
        List<PaymentService> paymentServiceList = service.getPaymentServiceByAmount(INVALID__AMOUNT);
        assertEquals(paymentServiceList,null);
    }

    @Test
    public void testGetPaymentServiceValidTimestamp() {
        List<PaymentService> paymentServiceList = service.getPaymentServiceByDateTime(VALID__DATETIME);
        assertEquals(paymentServiceList.size(),1);
        assertEquals(VALID__DATETIME, paymentServiceList.get(0).getDateTime());
    }

    @Test
    public void testGetPaymentServiceInValidTimestamp1() {
        List<PaymentService> paymentServiceList = service.getPaymentServiceByDateTime(INVALID_PAST__DATETIME);
        assertEquals(paymentServiceList,null);
    }

    @Test
    public void testGetPaymentServiceInValidTimestamp2() {
        List<PaymentService> paymentServiceList = service.getPaymentServiceByDateTime(INVALID_FUTURE__DATETIME);
        assertEquals(paymentServiceList,null);
    }

    @Test
    public void testGetPaymentServiceValidServiceRequest() {
        List<PaymentService> pa = service.getPaymentServiceByServiceRequest(SERVICE_REQUEST__ID);
        assertEquals(SERVICE_REQUEST__ID, pa.get(0).getServiceReq().getId());
    }

    @Test
    public void testGetAll() {
        List<PaymentService> paymentServiceList = service.getAllPaymentService();
        assertEquals(paymentServiceList.size(),1);
    }

    @Test
    public void testUpdatePaymentServiceValid() {
        PaymentService pa = service.updatePaymentService(VALID__ID, VALID__DATETIME__UPDATE, VALID__AMOUNT_UPDATE, 
        SERVICE_REQUEST__ID);
        assertNotNull(pa);
        assertEquals(VALID__AMOUNT_UPDATE, pa.getAmount());
        assertEquals(VALID__DATETIME__UPDATE, pa.getDateTime());
        assertEquals(SERVICE_REQUEST__ID, pa.getServiceReq().getId());
    }

    @Test
    public void testUpdatePaymentServiceInvalid1() {
        String error = "";
        try {
            service.updatePaymentService(VALID__ID, INVALID_PAST__DATETIME, VALID__AMOUNT_UPDATE, 
            SERVICE_REQUEST__ID);
        }catch (Exception e){
            error = e.getMessage();
        }
        assertEquals("payment service date time is wrong!", error);
    }

    @Test
    public void testUpdatePaymentServiceInvalid2() {
        String error = "";
        try {
            service.updatePaymentService(VALID__ID, INVALID_FUTURE__DATETIME, VALID__AMOUNT_UPDATE, 
            SERVICE_REQUEST__ID);
        }catch (Exception e){
            error = e.getMessage();
        }
        assertEquals("payment service date time is wrong!", error);
    }

    @Test
    public void testUpdatePaymentServiceInvalid3() {
        String error = "";
        try {
            service.updatePaymentService(VALID__ID, VALID__DATETIME, VALID__AMOUNT_UPDATE, 
            INVALID__ID);
        }catch (Exception e){
            error = e.getMessage();
        }
        assertEquals("payment service does not exist in service request repository!", error);
    }

    @Test
    public void testDeletePaymentService() {
        String error = "";
        PaymentService paymentService = null;
        try {
            paymentService = service.deletePaymentService(VALID__ID);
        }catch (Exception e){
            error = e.getMessage();
        }
        // return dummyPaymentService(VALID__ID, VALID__AMOUNT, VALID__DATETIME, 
        // dummyServiceReq(SERVICE_REQUEST__ID, SERVICE__IS_ASSIGNED, SERVICE__LICENSE_NUMBER, dummyService(SERVICE__PRICE)));

        assertEquals("", error);
        assertEquals(VALID__ID, paymentService.getId());
        assertEquals(VALID__AMOUNT, paymentService.getAmount());
        assertEquals(VALID__DATETIME, paymentService.getDateTime());
        assertEquals(SERVICE_REQUEST__ID, paymentService.getServiceReq().getId());
    }

    @Test
    public void testDeletePaymentServiceFail1() {
        testDeletePaymentReservationFailure(0, 
        "a id must be mention to delete a payment service! ");
    }

    @Test
    public void testDeletePaymentServiceFail2() {
        testDeletePaymentReservationFailure(INVALID__ID, 
        "no such payment service exist! ");
    }

    private void testDeletePaymentReservationFailure(int id, String message){
        PaymentService pa = null;
        String errMsg = "";
        try {
            pa = service.deletePaymentService(id);
        } catch(Exception e) {
            errMsg = e.getMessage();
        }
        assertEquals(message, errMsg);
        assertNull(pa);
    }

    private Service dummyService(int price) {
        Service service = new Service();
        service.setPrice(price);
        service.setDescription("This is a test service");
        return service;
    }

    private ServiceReqWithoutAccount dummyServiceReq(int id, boolean isAssigned, String LicenseNumber, Service service) {
        ServiceReqWithoutAccount ser = new ServiceReqWithoutAccount();
        ser.setId(id);
        ser.setService(service);
        ser.setLicenseNumber(LicenseNumber);
        ser.setIsAssigned(isAssigned);
        return ser;
    }

    private PaymentService dummyPaymentService(int id, double amount, Timestamp dateTime, ServiceRequest serviceRequest) {
        PaymentService pa = new PaymentService();
        pa.setId(id);
        pa.setAmount(amount);
        pa.setDateTime(dateTime);
        pa.setServiceReq(serviceRequest);
        return pa;
    }

}

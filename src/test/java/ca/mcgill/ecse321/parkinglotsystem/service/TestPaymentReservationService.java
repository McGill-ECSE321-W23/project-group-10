package ca.mcgill.ecse321.parkinglotsystem.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Timestamp;
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

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.PaymentReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.PaymentReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

@ExtendWith(MockitoExtension.class)
public class TestPaymentReservationService {

    @Mock
    private ParkingSpotTypeRepository parkingSpotTypeRepository;

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @Mock
    private PaymentReservationRepository paymentReservationRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private PaymentReservationService paymentReservationService;

    // for parking spot type
    private static final String PARKING_SPOT_TYPE_NAME_STRING = "TestSpotType";
    private static final double PARKING__SPOT_TYPE_FEE = 1;

    // for parking spot
    private static final int PARKING_SPOT_ID = 1; //MIGHT REMOVE

    // for reservation
    private static final int RESERVATION_ID1 = 1;
    private static final int RESERVATION_ID2 = 2;
    private static final Date RESERVATION_DATE = Date.valueOf("2023-02-27");
    private static final String LICENSE_NUMBER = "1A1";
    private static final int PARKING_TIME_SINGLE_RESERVATION = 10;

    // for Payment reservation
    private static final int PAYMENT_ID1 =1;
    private static final int PAYMENT_ID2 =2;
    private static final Timestamp PAYMENT_TIME = Timestamp.valueOf("2018-09-01 09:01:15");
    private static final double PAYMENT_AMOUNT1 = 1.0;
    private static final double PAYMENT_AMOUNT2 = 2.0;


    @BeforeEach
	public void setMockOutput() {
        // remove this?
        lenient().when(reservationRepository.findReservationById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            // create a parking spot type

            if(invocation.getArgument(0).equals(RESERVATION_ID1)) {
                return dummyReservation(RESERVATION_ID1, RESERVATION_DATE, LICENSE_NUMBER, PARKING_TIME_SINGLE_RESERVATION);
            }
            else if(invocation.getArgument(0).equals(RESERVATION_ID2)) {
                return dummyReservation(RESERVATION_ID2, RESERVATION_DATE, LICENSE_NUMBER, PARKING_TIME_SINGLE_RESERVATION);
            }        
            else {
                return null;
            }
        });

        lenient().when(paymentReservationRepository.findPaymentReservationById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(PAYMENT_ID1)) {
                return dummy(PAYMENT_ID1, PAYMENT_TIME, PAYMENT_AMOUNT1);
            }
            else{
                return null;
            }
        });

        lenient().when(paymentReservationRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
                List<PaymentReservation> paymentReservations = new ArrayList<>();
                paymentReservations.add(dummy(PAYMENT_ID1, PAYMENT_TIME, PAYMENT_AMOUNT1));
                paymentReservations.add(dummy(PAYMENT_ID2, PAYMENT_TIME, PAYMENT_AMOUNT2));
                return paymentReservations;
        });

        lenient().when(paymentReservationRepository.findPaymentReservationByReservation(any(Reservation.class))).thenAnswer((InvocationOnMock invocation) -> {

                List<PaymentReservation> paymentReservations = new ArrayList<>();
                paymentReservations.add(dummy(PAYMENT_ID1, PAYMENT_TIME, PAYMENT_AMOUNT1));
                paymentReservations.add(dummy(PAYMENT_ID2, PAYMENT_TIME, PAYMENT_AMOUNT2));
                return paymentReservations;
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(paymentReservationRepository.save(any(PaymentReservation.class))).thenAnswer(returnParameterAsAnswer);
    }
    
    @Test
    public void testCreatePaymentReservation() {

        // Create a PaymentReservation object
        PaymentReservation paymentReservation = new PaymentReservation();

        String error = "";
        try {
			paymentReservation = paymentReservationService.createPaymentReservation(PAYMENT_TIME, PAYMENT_AMOUNT1, RESERVATION_ID1);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

        assertEquals(PAYMENT_TIME, paymentReservation.getDateTime());
        assertEquals(PAYMENT_AMOUNT1, paymentReservation.getAmount());
    }

    @Test
    public void testCreatePaymentReservationWithInvalidInput() {
        Timestamp timestamp = null;

        // Create a PaymentReservation object
        PaymentReservation paymentReservation = new PaymentReservation();

        String error = "";
        try {
			paymentReservation = paymentReservationService.createPaymentReservation(timestamp, -1, RESERVATION_ID1);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

        assertEquals("no date and time entered! amount should be greater than 0! ", error);
        
    }

    @Test
    public void testCreatePaymentReservationWithInvalidReservation() {

        // Create a PaymentReservation object
        PaymentReservation paymentReservation = new PaymentReservation();
        int invalidReservationId = 1000;
        String error = "";
        try {
			paymentReservation = paymentReservationService.createPaymentReservation(PAYMENT_TIME, PAYMENT_AMOUNT1, invalidReservationId);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

        assertEquals("Reservation is not found. ", error);
    }


    @Test
    public void testGetAllPaymentReservations(){
        String error = "";
        List<PaymentReservation> paymentReservations = null;
        try {
            paymentReservations = paymentReservationService.getAllPaymentReservation();

        }catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(PAYMENT_ID1, paymentReservations.get(0).getId());
        assertEquals(PAYMENT_ID2, paymentReservations.get(1).getId());
        assertEquals(PAYMENT_AMOUNT1, paymentReservations.get(0).getAmount());
        assertEquals(PAYMENT_AMOUNT2, paymentReservations.get(1).getAmount());
    }

    @Test
    public void testDeletePaymentReservation() {
        String error = "";
        PaymentReservation paymentReservation = null;
        try {
            paymentReservation = paymentReservationService.deletePaymentReservation(PAYMENT_ID1);

        }catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(PAYMENT_TIME, paymentReservation.getDateTime());
        assertEquals(PAYMENT_AMOUNT1, paymentReservation.getAmount());

    }

    @Test
    public void testDeletePaymentReservationWithInvalidId() {
        String error = "";
        PaymentReservation paymentReservation = null;
        try {
            paymentReservation = paymentReservationService.deletePaymentReservation(-1);

        }catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("Invalid payment id! No payment with that id exists! ", error);

    }

    @Test
    public void testUpdatePaymentReservation() {
        // Create a PaymentReservation object
        PaymentReservation paymentReservation = new PaymentReservation();

        String error = "";
        Timestamp newPaymentTime = Timestamp.valueOf("2023-09-01 09:01:15");
        double newPaymentFee = 80;

        try {
			paymentReservation = paymentReservationService.updatePaymentReservation(PAYMENT_ID1, newPaymentTime, newPaymentFee, RESERVATION_ID2);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error); 
        assertEquals(newPaymentTime, paymentReservation.getDateTime());
        assertEquals(newPaymentFee, paymentReservation.getAmount());
        assertEquals(RESERVATION_ID2, paymentReservation.getReservation().getId());
    }


    @Test
    public void testUpdatePaymentReservationWithInvalidInput() {
        // Create a PaymentReservation object
        PaymentReservation paymentReservation = new PaymentReservation();

        String error = "";
        Timestamp newPaymentTime = null;
        double newPaymentFee = -1;

        try {
			paymentReservation = paymentReservationService.updatePaymentReservation(PAYMENT_ID1, newPaymentTime, newPaymentFee, RESERVATION_ID2);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("No date and time entered! Amount should be greater than 0! ", error); 

    }

    @Test
    public void testUpdatePaymentReservationWithInvalidReservation() {
        // Create a PaymentReservation object
        PaymentReservation paymentReservation = new PaymentReservation();

        String error = "";
        Timestamp newPaymentTime = Timestamp.valueOf("2023-09-01 09:01:15");
        double newPaymentFee = 80;

        try {
			paymentReservation = paymentReservationService.updatePaymentReservation(PAYMENT_ID1, newPaymentTime, newPaymentFee, 100000);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("Reservation not found. ", error); 

    }

    @Test
    public void testUpdatePaymentReservationWithInvalidId() {
        // Create a PaymentReservation object
        PaymentReservation paymentReservation = new PaymentReservation();

        String error = "";
        Timestamp newPaymentTime = Timestamp.valueOf("2023-09-01 09:01:15");
        double newPaymentFee = 80;

        try {
			paymentReservation = paymentReservationService.updatePaymentReservation(1022434332, newPaymentTime, newPaymentFee, RESERVATION_ID2);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("Payment reservation not found. ", error); 
    }


    @Test
    public void testGetPaymentReservationByReservation() {

        String error = "";
        List<PaymentReservation> paymentReservations = null;
        try {
            paymentReservations = paymentReservationService.getPaymentReservationByReservation(RESERVATION_ID1);

        }catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(PAYMENT_ID1, paymentReservations.get(0).getId());

        assertEquals(PAYMENT_AMOUNT1, paymentReservations.get(0).getAmount());

    }

    @Test
    public void testGetPaymentReservationByReservationWithWrongId() {

        String error = "";
        List<PaymentReservation> paymentReservations = null;
        try {
            paymentReservations = paymentReservationService.getPaymentReservationByReservation(122323);

        }catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("Reservation not found. ", error);

    }

    @Test
    public void testGetPaymentReservationByReservationWithInvalidId() {

        String error = "";
        List<PaymentReservation> paymentReservations = null;
        try {
            paymentReservations = paymentReservationService.getPaymentReservationByReservation(0);

        }catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("reservation id should be greater than 0! ", error);

    }

    
    //  dummy classes //
    private PaymentReservation dummy(int id, Timestamp timestamp, double amount) {
        
        PaymentReservation paymentReservation = new PaymentReservation();
        paymentReservation.setId(id);
        paymentReservation.setAmount(amount);
        paymentReservation.setDateTime(timestamp);
        paymentReservation.setReservation(dummyReservation(RESERVATION_ID1, 
        RESERVATION_DATE, LICENSE_NUMBER, PARKING_TIME_SINGLE_RESERVATION));
        return paymentReservation;
    }

    private ParkingSpotType dummyParkingSpotType(String name, double fee) {
        ParkingSpotType parkingSpotType = new ParkingSpotType();
        parkingSpotType.setFee(fee);
        parkingSpotType.setName(name);
        return parkingSpotType;
    }

    private ParkingSpot dummyParkingSpot(int id){
        ParkingSpotType parkingSpotType = dummyParkingSpotType(PARKING_SPOT_TYPE_NAME_STRING, PARKING__SPOT_TYPE_FEE);
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setId(id);
        parkingSpot.setType(parkingSpotType);
        return parkingSpot;
    }
    private SingleReservation dummyReservation(int id, Date date, String licenceNumber, int parkingTime) {
        ParkingSpot parkingSpot = dummyParkingSpot(PARKING_SPOT_ID);
        SingleReservation reservation = new SingleReservation();
        reservation.setId(id);
        reservation.setDate(date);
        reservation.setLicenseNumber(licenceNumber);
        reservation.setParkingSpot(parkingSpot);
        reservation.setParkingTime(parkingTime);
        return reservation;
    }



}

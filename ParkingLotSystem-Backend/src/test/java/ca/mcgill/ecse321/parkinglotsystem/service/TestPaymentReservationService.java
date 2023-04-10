package ca.mcgill.ecse321.parkinglotsystem.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyDouble;
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
    private static final int INVALID_RESERVATION_ID = 200;
    private static final Date RESERVATION_DATE = Date.valueOf("2023-02-27");
    private static final String LICENSE_NUMBER = "1A1";
    private static final int PARKING_TIME_SINGLE_RESERVATION = 10;

    // for Payment reservation
    private static final int PAYMENT_ID1 = 1;
    private static final int PAYMENT_ID2 = 2;
    private static final int INVALID_PAYMENT_ID = -1;
    private static final Timestamp PAYMENT_TIME = Timestamp.valueOf("2018-09-01 09:01:15");
    private static final double PAYMENT_AMOUNT1 = 1.0;
    private static final double PAYMENT_AMOUNT2 = 2.0;
    private static final double INVALID_PAYMENT_AMOUNT = -1.0;


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

        lenient().when(paymentReservationRepository.findPaymentReservationByDateTime(any(Timestamp.class))).thenAnswer((InvocationOnMock invocation) -> {
            List<PaymentReservation> paymentReservations = new ArrayList<>();
            paymentReservations.add(dummy(PAYMENT_ID1, PAYMENT_TIME, PAYMENT_AMOUNT1));
            return paymentReservations;
        });

        lenient().when(paymentReservationRepository.findPaymentReservationByAmount(anyDouble())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(PAYMENT_AMOUNT1)) {
                List<PaymentReservation> paymentReservations = new ArrayList<>();
                paymentReservations.add(dummy(PAYMENT_ID1, PAYMENT_TIME, PAYMENT_AMOUNT1));
                return paymentReservations;
            }
            else {
                return null;
            }
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
			paymentReservation = paymentReservationService.createPaymentReservation(PAYMENT_AMOUNT1, RESERVATION_ID1);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertNotNull(paymentReservation);
        assertEquals(PAYMENT_AMOUNT1, paymentReservation.getAmount());
    }


    @Test
    public void testCreatePaymentReservationWithInvalidFee() {
        testCreatePaymentReservationFailure(INVALID_PAYMENT_AMOUNT, RESERVATION_ID1, 
        "amount should be greater than 0! ");
    }

    @Test
    public void testCreatePaymentReservationWithInvalidReservation() {
        testCreatePaymentReservationFailure(PAYMENT_AMOUNT1, INVALID_RESERVATION_ID, 
        "Reservation is not found. ");
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
        testDeletePaymentReservationFailure(INVALID_PAYMENT_ID, "Invalid payment id! ");
    }

    @Test
    public void testDeletePaymentReservationWithFalseId() {
        testDeletePaymentReservationFailure(4, "No payment with that id exists! ");
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
    public void testUpdatePaymentReservationWithInvalidDate() {
        testUpdatePaymentReservationFailure(PAYMENT_ID1, null, PAYMENT_AMOUNT1, RESERVATION_ID1, 
        "No date and time entered! ");

    }

    @Test
    public void testUpdatePaymentReservationWithInvalidInput() {
        testUpdatePaymentReservationFailure(PAYMENT_ID1, PAYMENT_TIME, INVALID_PAYMENT_AMOUNT, RESERVATION_ID1, 
        "Amount should be greater than 0! ");
    }

    @Test
    public void testUpdatePaymentReservationWithInvalidReservation() {
        testUpdatePaymentReservationFailure(PAYMENT_ID1, PAYMENT_TIME, PAYMENT_AMOUNT1, 40, 
        "Reservation not found. ");
    }

    @Test
    public void testUpdatePaymentReservationWithInvalidId() {
        testUpdatePaymentReservationFailure(INVALID_PAYMENT_ID, PAYMENT_TIME, PAYMENT_AMOUNT1, RESERVATION_ID1, 
        "Payment reservation not found. ");
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
        testGetPaymentReservationByReservationFailure(4343, 
        "Reservation not found. ");

    }

    @Test
    public void testGetPaymentReservationByReservationWithInvalidId() {
        testGetPaymentReservationByReservationFailure(-1, 
        "reservation id should be greater than 0! ");
    }

    @Test
    public void testGetPaymentReservationByDate() {

        String error = "";
        List<PaymentReservation> paymentReservations = null;
        try {
            paymentReservations = paymentReservationService.getPaymentReservationByDateTime(PAYMENT_TIME);

        }catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(PAYMENT_ID1, paymentReservations.get(0).getId());
        assertEquals(PAYMENT_AMOUNT1, paymentReservations.get(0).getAmount());
        assertEquals(PAYMENT_TIME, paymentReservations.get(0).getDateTime());
    }

    @Test
    public void testGetPaymentReservationByDateWithInvalidDate() {

        String error = "";
        try {
            paymentReservationService.getPaymentReservationByDateTime(null);

        }catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("Invalid Date and time! ", error);
    }

    @Test
    public void testGetPaymentReservationByAmount() {
        String error = "";
        List<PaymentReservation> paymentReservations = null;
        try {
            paymentReservations = paymentReservationService.getPaymentReservationByAmout(PAYMENT_AMOUNT1);

        }catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(PAYMENT_ID1, paymentReservations.get(0).getId());
        assertEquals(PAYMENT_AMOUNT1, paymentReservations.get(0).getAmount());
        assertEquals(PAYMENT_TIME, paymentReservations.get(0).getDateTime());
    }

    @Test
    public void testGetPaymentReservationByAmountWithInvalidInput() {
        String error = "";
        try {
            paymentReservationService.getPaymentReservationByAmout(-1);

        }catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("Invalid amount! ", error);

    }


    private void testCreatePaymentReservationFailure(double amount, int resId, String message) {
        PaymentReservation paymentReservation = null;
        String error = "";
        try {
			paymentReservation = paymentReservationService.createPaymentReservation(amount, resId);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertNull(paymentReservation);
        assertEquals(message, error);
    }

    private void testDeletePaymentReservationFailure(int id, String message) {
        PaymentReservation paymentReservation = null;
        String error = "";
        try {
			paymentReservation = paymentReservationService.deletePaymentReservation(id);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertNull(paymentReservation);
        assertEquals(message, error);
    }

    private void testUpdatePaymentReservationFailure(int payId, Timestamp newTime, double newFee, int resId, String message) {
        PaymentReservation paymentReservation = null;
        String error = "";
        try {
			paymentReservation = paymentReservationService.updatePaymentReservation(payId, newTime, newFee, resId);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertNull(paymentReservation);
        assertEquals(message, error);
    }

    private void testGetPaymentReservationByReservationFailure(int id, String message) {
        String error = "";
        List<PaymentReservation> paymentReservations = null;
        try {
            paymentReservations = paymentReservationService.getPaymentReservationByReservation(id);

        }catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertNull(paymentReservations);
        assertEquals(message, error);
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

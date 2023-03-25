package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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

import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.*;


@ExtendWith(MockitoExtension.class)
public class TestParkingSpotService {

    // parking spot type variables
    private static final String PARKING_SPOT_TYPE_NAME = "TestType";
    private static final int PARKING_SPOT_TYPE_FEE = 1;

    // parking spot variables
    private static final int PARKING_SPOT_ID = 1;
    private static final int PARKING_SPOT_ID2 = 2;

    @Mock
    private ParkingSpotTypeRepository parkingSpotTypeRepository;
    @Mock
    private ParkingSpotRepository parkingSpotRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ParkingSpotService parkingSpotService;

    @BeforeEach
	public void setMockOutput() {
        lenient().when(parkingSpotTypeRepository.findParkingSpotTypeByName(anyString())).thenAnswer((InvocationOnMock invocation) -> { 
            if(invocation.getArgument(0).equals(PARKING_SPOT_TYPE_NAME)) {
                return dummyParkingSpotType(PARKING_SPOT_TYPE_NAME, PARKING_SPOT_TYPE_FEE);
            }
            else{
                return null;
            }
        });

        lenient().when(parkingSpotRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> { 
            List<ParkingSpot> parkingSpots = new ArrayList<>();
            parkingSpots.add(dummyParkingSpot(PARKING_SPOT_ID));
            return parkingSpots; 
        });

        lenient().when(parkingSpotRepository.findParkingSpotById(anyInt())).thenAnswer((InvocationOnMock invocation) -> { 
            if(invocation.getArgument(0).equals(PARKING_SPOT_ID)) {
                return dummyParkingSpot(PARKING_SPOT_ID);
            }
            else if(invocation.getArgument(0).equals(PARKING_SPOT_ID2)) {
                return dummyParkingSpot(PARKING_SPOT_ID2);
            }
            else{
                return null;
            }
        });
        lenient().when(reservationRepository.findReservationsByParkingSpot(any(ParkingSpot.class))).thenAnswer((InvocationOnMock invocation) -> { 
            List<Reservation> reservations = new ArrayList<>();
            if(invocation.getArgument(0).equals(dummyParkingSpot(PARKING_SPOT_ID))) {
                return reservations;
            }
            else {
                reservations.add(dummyReservation(1, Date.valueOf("2023-02-27"), PARKING_SPOT_TYPE_NAME, 10, PARKING_SPOT_ID2));
                return reservations;
            }
            
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(parkingSpotRepository.save(any(ParkingSpot.class))).thenAnswer(returnParameterAsAnswer);
    }   

    @Test
    public void testCreateParkingSpot() {

        ParkingSpot parkingSpot = new ParkingSpot();
        String error = "";
        try {
			parkingSpot = parkingSpotService.createParkingSpot(PARKING_SPOT_ID, PARKING_SPOT_TYPE_NAME);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(PARKING_SPOT_ID, parkingSpot.getId());
        assertEquals(PARKING_SPOT_TYPE_NAME, parkingSpot.getType().getName());
        assertEquals(PARKING_SPOT_TYPE_FEE, parkingSpot.getType().getFee());
    }

    @Test
    public void testCreateParkingSpotWithInvalidInput() {

        ParkingSpot parkingSpot = new ParkingSpot();
        String error = "";
        try {
			parkingSpot = parkingSpotService.createParkingSpot(0, PARKING_SPOT_TYPE_NAME);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("Id must be greater than zero! ", error);
    }

    @Test
    public void testCreateParkingSpotWithInvalidType() {
        ParkingSpot parkingSpot = new ParkingSpot();
        String error = "";
        try {
			parkingSpot = parkingSpotService.createParkingSpot(PARKING_SPOT_ID, "fail");
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("No parking spot type with name fail exists! ", error);
    }

    @Test
    public void testGetAllParkingSpot() {
        List<ParkingSpot> parkingSpots = null;
        String error = "";
        try {
            parkingSpots = parkingSpotService.getAllParkingSpots();
        } catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(PARKING_SPOT_ID, parkingSpots.get(0).getId());
        assertEquals(PARKING_SPOT_TYPE_FEE, parkingSpots.get(0).getType().getFee());
        assertEquals(PARKING_SPOT_TYPE_NAME, parkingSpots.get(0).getType().getName());
    }


    // @Test
    // public void testDeleteParkingSpot() {
    //     ParkingSpot parkingSpot = new ParkingSpot();
    //     String error = "";
    //     int dummyId = 30;
    //     try {
    //         parkingSpot = parkingSpotService.deleteParkingSpotBy(PARKING_SPOT_ID);
    //     } catch (CustomException e) {
	// 		// Check that no error occurred
	// 		error = e.getMessage();
	// 	}
    //     assertEquals("", error);
    //     assertEquals(PARKING_SPOT_ID, parkingSpot.getId());
    //     assertEquals(PARKING_SPOT_TYPE_NAME, parkingSpot.getType().getName());
    //     assertEquals(PARKING_SPOT_TYPE_FEE, parkingSpot.getType().getFee());
    // }
    
    // dummy object creation //

    private ParkingSpotType dummyParkingSpotType(String name, double fee) {
        ParkingSpotType parkingSpotType = new ParkingSpotType();
        parkingSpotType.setFee(fee);
        parkingSpotType.setName(name);
        return parkingSpotType;
    }

    private ParkingSpot dummyParkingSpot(int id){
        ParkingSpotType parkingSpotType = dummyParkingSpotType(PARKING_SPOT_TYPE_NAME, PARKING_SPOT_TYPE_FEE);
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setId(id);
        parkingSpot.setType(parkingSpotType);
        return parkingSpot;
    }

    private SingleReservation dummyReservation(int id, Date date, String licenceNumber, int parkingTime, int parkingSpotId) {
        ParkingSpot parkingSpot = dummyParkingSpot(parkingSpotId);
        SingleReservation reservation = new SingleReservation();
        reservation.setId(id);
        reservation.setDate(date);
        reservation.setLicenseNumber(licenceNumber);
        reservation.setParkingSpot(parkingSpot);
        reservation.setParkingTime(parkingTime);
        return reservation;
    }
    
}

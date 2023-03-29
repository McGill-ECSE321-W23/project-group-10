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
    private static final String VALID_PARKING_SPOT_TYPE_NAME_1 = "TestType";
    private static final int VALID_PARKING_SPOT_TYPE_FEE_1 = 1;

    private static final String VALID_PARKING_SPOT_TYPE_NAME_2 = "TestType2";
    private static final int VALID_PARKING_SPOT_TYPE_FEE_2 = 50;

    private static final String INVALID_PARKING_SPOT_TYPE_NAME = "fail";

    // parking spot variables
    private static final int VALID_PARKING_SPOT_ID_1 = 1;
    private static final int VALID_PARKING_SPOT_ID_2 = 2;
    private static final int INVALID_PARKING_SPOT_ID_1 = -11;

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
            if(invocation.getArgument(0).equals(VALID_PARKING_SPOT_TYPE_NAME_1)) {
                return dummyParkingSpotType(VALID_PARKING_SPOT_TYPE_NAME_1, VALID_PARKING_SPOT_TYPE_FEE_1);
            }
            else if(invocation.getArgument(0).equals(VALID_PARKING_SPOT_TYPE_NAME_2)) {
                return dummyParkingSpotType(VALID_PARKING_SPOT_TYPE_NAME_2, VALID_PARKING_SPOT_TYPE_FEE_2);
            }
            else{
                return null;
            }
        });

        lenient().when(parkingSpotRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> { 
            List<ParkingSpot> parkingSpots = new ArrayList<>();
            parkingSpots.add(dummyParkingSpot(VALID_PARKING_SPOT_ID_1));
            return parkingSpots; 
        });

        lenient().when(parkingSpotRepository.findParkingSpotByType(any(ParkingSpotType.class))).thenAnswer((InvocationOnMock invocation) -> {
            List<ParkingSpot> parkingSpots = new ArrayList<>();
            parkingSpots.add(dummyParkingSpot(VALID_PARKING_SPOT_ID_1));
            return parkingSpots; 
        });

        lenient().when(parkingSpotRepository.findParkingSpotById(anyInt())).thenAnswer((InvocationOnMock invocation) -> { 
            if(invocation.getArgument(0).equals(VALID_PARKING_SPOT_ID_1)) {
                return dummyParkingSpot(VALID_PARKING_SPOT_ID_1);
            }
            else if(invocation.getArgument(0).equals(VALID_PARKING_SPOT_ID_2)) {
                return dummyParkingSpot(VALID_PARKING_SPOT_ID_2);
            }
            else{
                return null;
            }
        });
        lenient().when(reservationRepository.findReservationsByParkingSpot(any(ParkingSpot.class))).thenAnswer((InvocationOnMock invocation) -> { 
            List<Reservation> reservations = new ArrayList<>();
            reservations.add(dummyReservation(1, Date.valueOf("2023-02-27"), VALID_PARKING_SPOT_TYPE_NAME_1, 10, VALID_PARKING_SPOT_ID_2));
            return reservations;
            
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(parkingSpotRepository.save(any(ParkingSpot.class))).thenAnswer(returnParameterAsAnswer);
    }   

    // test create //
    @Test
    public void testCreateParkingSpot() {

        ParkingSpot parkingSpot = new ParkingSpot();
        String error = "";
        try {
			parkingSpot = parkingSpotService.createParkingSpot(VALID_PARKING_SPOT_ID_1, VALID_PARKING_SPOT_TYPE_NAME_1);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(VALID_PARKING_SPOT_ID_1, parkingSpot.getId());
        assertEquals(VALID_PARKING_SPOT_TYPE_NAME_1, parkingSpot.getType().getName());
        assertEquals(VALID_PARKING_SPOT_TYPE_FEE_1, parkingSpot.getType().getFee());
    }

    @Test
    public void testCreateParkingSpotWithInvalidInput() {
        testCreateParkingSpotFailure(INVALID_PARKING_SPOT_ID_1, VALID_PARKING_SPOT_TYPE_NAME_2, 
        "Id must be greater than zero! ");
    }

    @Test
    public void testCreateParkingSpotWithInvalidType() {
        testCreateParkingSpotFailure(VALID_PARKING_SPOT_ID_1, INVALID_PARKING_SPOT_TYPE_NAME, 
        "No parking spot type with name fail exists! ");
    }

    // test get all //
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
        assertEquals(VALID_PARKING_SPOT_ID_1, parkingSpots.get(0).getId());
        assertEquals(VALID_PARKING_SPOT_TYPE_FEE_1, parkingSpots.get(0).getType().getFee());
        assertEquals(VALID_PARKING_SPOT_TYPE_NAME_1, parkingSpots.get(0).getType().getName());
    }

    // test update //
    @Test
    public void testUpdateParkingSpot() {
        ParkingSpot parkingSpot = null;
        String error = "" ;
        try {
            parkingSpot = parkingSpotService.updateParkingSpot(VALID_PARKING_SPOT_ID_1, VALID_PARKING_SPOT_TYPE_NAME_2);
        } catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(VALID_PARKING_SPOT_ID_1, parkingSpot.getId());
        assertEquals(VALID_PARKING_SPOT_TYPE_NAME_2, parkingSpot.getType().getName());
        assertEquals(VALID_PARKING_SPOT_TYPE_FEE_2, parkingSpot.getType().getFee());
    }

    @Test
    public void testUpdateParkingSpotWithWrongId() {
        testUpdateParkingSpotFailure(INVALID_PARKING_SPOT_ID_1, VALID_PARKING_SPOT_TYPE_NAME_1, 
        "no parking spot with that id exists! ");
    }

    @Test
    public void testUpdateParkingSpotWithWrongType() {
        testUpdateParkingSpotFailure(VALID_PARKING_SPOT_ID_1, INVALID_PARKING_SPOT_TYPE_NAME, 
        "Cannot find parking spot type by that name! ");
    }

    // test get by id //

    @Test
    public void testGetById() {
        ParkingSpot parkingSpot = null;
        String error = "" ;
        try {
            parkingSpot = parkingSpotService.getParkingSpotById(VALID_PARKING_SPOT_ID_1);
        } catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(VALID_PARKING_SPOT_ID_1, parkingSpot.getId());
        assertEquals(VALID_PARKING_SPOT_TYPE_NAME_1, parkingSpot.getType().getName());
        assertEquals(VALID_PARKING_SPOT_TYPE_FEE_1, parkingSpot.getType().getFee());
    }

    @Test
    public void testGetByIdWithWrongId() {
        ParkingSpot parkingSpot = null;
        String error = "" ;
        try {
            parkingSpot = parkingSpotService.getParkingSpotById(44);
        } catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("No parking spot with that id was found! ", error);
    }

    // test get by Type //

    @Test
    public void testGetByType() {
        List<ParkingSpot> parkingSpots = null;
        String error = "";
        try {
            parkingSpots = parkingSpotService.getParkingSpotByType(VALID_PARKING_SPOT_TYPE_NAME_1);
        } catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(VALID_PARKING_SPOT_ID_1, parkingSpots.get(0).getId());
        assertEquals(VALID_PARKING_SPOT_TYPE_FEE_1, parkingSpots.get(0).getType().getFee());
        assertEquals(VALID_PARKING_SPOT_TYPE_NAME_1, parkingSpots.get(0).getType().getName());
    }

    @Test
    public void testGetByTypeWithWrongName() {
        List<ParkingSpot> parkingSpots = null;
        String error = "";
        try {
            parkingSpots = parkingSpotService.getParkingSpotByType("fail");
        } catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("Cannot find parking spot type by that name! ", error);
    }

    // test delete //

    @Test
    public void testDeleteParkingSpot() {
        ParkingSpot parkingSpot = new ParkingSpot();
        String error = "";
        try {
            parkingSpot = parkingSpotService.deleteParkingSpotById(VALID_PARKING_SPOT_ID_1);
        } catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertEquals(VALID_PARKING_SPOT_ID_1, parkingSpot.getId());
        assertEquals(VALID_PARKING_SPOT_TYPE_NAME_1, parkingSpot.getType().getName());
        assertEquals(VALID_PARKING_SPOT_TYPE_FEE_1, parkingSpot.getType().getFee());
    }

    @Test
    public void testDeleteParkingSpotWithValidReservation() {
        testDeleteParkingSpotFailure(VALID_PARKING_SPOT_ID_2, 
        "Cannot delete as parking spot has 1 or more reservation! ");
    }

    @Test
    public void testDeleteParkingSpotWithInvalidId() {
        testDeleteParkingSpotFailure(INVALID_PARKING_SPOT_ID_1, 
        "No parking spot with that id was found! ");
    }


    private void testCreateParkingSpotFailure(int id, String typeName, String message) {
        ParkingSpot parkingSpot = new ParkingSpot();
        String error = "";
        try {
			parkingSpot = parkingSpotService.createParkingSpot(id, typeName);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals(message, error);
    }

    private void testDeleteParkingSpotFailure(int id, String message) {
        ParkingSpot parkingSpot = new ParkingSpot();
        String error = "";
        try {
            parkingSpot = parkingSpotService.deleteParkingSpotById(id);
        } catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals(message, error);
    }

    private void testUpdateParkingSpotFailure(int id, String typeName, String message) {
        ParkingSpot parkingSpot = new ParkingSpot();
        String error = "";
        try {
			parkingSpot = parkingSpotService.updateParkingSpot(id, typeName);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals(message, error);
    }
    


    // dummy object creation //

    private ParkingSpotType dummyParkingSpotType(String name, double fee) {
        ParkingSpotType parkingSpotType = new ParkingSpotType();
        parkingSpotType.setFee(fee);
        parkingSpotType.setName(name);
        return parkingSpotType;
    }

    private ParkingSpot dummyParkingSpot(int id){
        ParkingSpotType parkingSpotType = dummyParkingSpotType(VALID_PARKING_SPOT_TYPE_NAME_1, VALID_PARKING_SPOT_TYPE_FEE_1);
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

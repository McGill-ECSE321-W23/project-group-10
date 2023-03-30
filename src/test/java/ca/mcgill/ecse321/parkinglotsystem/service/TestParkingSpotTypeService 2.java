package ca.mcgill.ecse321.parkinglotsystem.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

@ExtendWith(MockitoExtension.class)
public class TestParkingSpotTypeService {
    
    @Mock
    private ParkingSpotTypeRepository parkingSpotTypeRepository;

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @InjectMocks
    private ParkingSpotTypeService parkingSpotTypeService;

    // parking spot type instance variables
    private static final String VALID_PARKING_SPOT_TYPE_1 = "TestSpotType";
    private static final double VALID_PARKING_SPOT_TYPE_FEE_1 = 10;

    private static final String VALID_PARKING_SPOT_TYPE_2 = "TestSpotType2";
    private static final double VALID_PARKING_SPOT_TYPE_FEE_2 = 10;

    private static final String INVALID_PARKING_SPOT_TYPE_NAME = "fail";

    // parking spot type instance variable
    private static final int PARKING_SPOT_ID = 3;

    @BeforeEach
	public void setMockOutput() {
        lenient().when(parkingSpotTypeRepository.findParkingSpotTypeByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(VALID_PARKING_SPOT_TYPE_1)) {
				return dummyParkingSpotType(VALID_PARKING_SPOT_TYPE_1, VALID_PARKING_SPOT_TYPE_FEE_1);
			} else if (invocation.getArgument(0).equals(VALID_PARKING_SPOT_TYPE_2)) {
				return dummyParkingSpotType(VALID_PARKING_SPOT_TYPE_2, VALID_PARKING_SPOT_TYPE_FEE_2);
			}else {
				return null;
			}
        });

        lenient().when(parkingSpotTypeRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<ParkingSpotType> parkingSpotTypes = new ArrayList<ParkingSpotType>();
            parkingSpotTypes.add(dummyParkingSpotType(VALID_PARKING_SPOT_TYPE_1, VALID_PARKING_SPOT_TYPE_FEE_1));
            return parkingSpotTypes;
        });

        lenient().when(parkingSpotRepository.findParkingSpotByType(any(ParkingSpotType.class))).thenAnswer((InvocationOnMock invocation) -> {
            List<ParkingSpot> parkingSpots = new ArrayList<>();
            parkingSpots.add(dummyParkingSpot(PARKING_SPOT_ID, dummyParkingSpotType(VALID_PARKING_SPOT_TYPE_1, VALID_PARKING_SPOT_TYPE_FEE_1)));
            return parkingSpots;
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

        lenient().when(parkingSpotTypeRepository.save(any(ParkingSpotType.class))).thenAnswer(returnParameterAsAnswer);

    }


    @Test
    public void testCreateParkingSpotType() {
        ParkingSpotType parkingSpotType = null;
        String error = "";
        try {
			parkingSpotType = parkingSpotTypeService.createParkingSpotType(VALID_PARKING_SPOT_TYPE_1, VALID_PARKING_SPOT_TYPE_FEE_1);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertNotNull(parkingSpotType);
        assertEquals(VALID_PARKING_SPOT_TYPE_1, parkingSpotType.getName());
        assertEquals(VALID_PARKING_SPOT_TYPE_FEE_1, parkingSpotType.getFee());
    }

    @Test
    public void testCreateParkingSpotTypeWithInvalidFee() {
        testCreateParkingSpotTypeFailure(VALID_PARKING_SPOT_TYPE_1, -1, 
        "Parking spot type fee cannot be less than zero! ");
    }

    @Test
    public void testCreateParkingSpotTypeWithInvalidName() {
        testCreateParkingSpotTypeFailure("", VALID_PARKING_SPOT_TYPE_FEE_1, 
        "Parking spot type name cannot be empty! ");
    }
    @Test
    public void testUpdateParkingTypeFee() {
        ParkingSpotType parkingSpotType = null;
        String error = "";
        try {
			parkingSpotType = parkingSpotTypeService.updateParkingSpotTypeFee(VALID_PARKING_SPOT_TYPE_1, VALID_PARKING_SPOT_TYPE_FEE_2);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

        assertEquals("", error);
        assertNotNull(parkingSpotType);
        assertEquals(VALID_PARKING_SPOT_TYPE_FEE_2, parkingSpotType.getFee());
        assertEquals(VALID_PARKING_SPOT_TYPE_1, parkingSpotType.getName());    
    }

    @Test
    public void testUpdateParkingTypeFeeWithEmptyName() {
        testUpdateParkingSpotTypeFailure("", VALID_PARKING_SPOT_TYPE_FEE_1, 
        "Parking spot type name cannot be empty! ");
    }

    @Test
    public void testUpdateParkingTypeFeeWithInvalidFee() {
        testUpdateParkingSpotTypeFailure(VALID_PARKING_SPOT_TYPE_1, -1, 
        "Parking spot type fee cannot be less than zero! ");

    }
    @Test
    public void testUpdateParkingTypeFeeWithInvalidName() {
        testUpdateParkingSpotTypeFailure(INVALID_PARKING_SPOT_TYPE_NAME, VALID_PARKING_SPOT_TYPE_FEE_1, 
        "Could not find a parking spot type by this name to update! ");

    }

    @Test
    public void testGetAllParkingSpotType() {
        List<ParkingSpotType> parkingSpotTypes = new ArrayList<ParkingSpotType>();
        String error = "";
        try {
			parkingSpotTypes = parkingSpotTypeService.getAllParkingSpotTypes();
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals(VALID_PARKING_SPOT_TYPE_1, parkingSpotTypes.get(0).getName()); 
    }

    @Test
    public void testGetParkingSpotTypeByName(){
        String error = "";
        ParkingSpotType parkingSpotType = null;
        try {
		    parkingSpotType = parkingSpotTypeService.getParkingSpotTypeByName(VALID_PARKING_SPOT_TYPE_1);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertNotNull(parkingSpotType);
        assertEquals(VALID_PARKING_SPOT_TYPE_1,parkingSpotType.getName() );
        assertEquals(VALID_PARKING_SPOT_TYPE_FEE_1, parkingSpotType.getFee());
    }

    @Test
    public void testGetParkingSpotTypeByNameWithInvalidName(){
        String error = "";
        ParkingSpotType parkingSpotType = null;
        try {
		    parkingSpotType = parkingSpotTypeService.getParkingSpotTypeByName("");
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertNull(parkingSpotType);
        assertEquals(
            "Parking spot type name cannot be empty! ",
            error);
    }


    @Test
    public void testDeleteParkingSpotType() {
        String error = "";
        ParkingSpotType parkingSpotType = null;
        try {
		    parkingSpotType = parkingSpotTypeService.deleteParkingSpotType(VALID_PARKING_SPOT_TYPE_2);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("", error);
        assertNotNull(parkingSpotType);
        assertEquals(VALID_PARKING_SPOT_TYPE_2,parkingSpotType.getName() );
        assertEquals(VALID_PARKING_SPOT_TYPE_FEE_2, parkingSpotType.getFee());
    }


    @Test
    public void testDeleteParkingSpotTypeWithInvalidDelete() {
        testDeleteParkingSpotTypeFailure(VALID_PARKING_SPOT_TYPE_1, 
        "This type is assigned to a parking spot ");

    }

    @Test
    public void testDeleteParkingSpotWithEmptyName() {
        testDeleteParkingSpotTypeFailure("", 
        "a name must be mention to delete parking spot type! ");
    
    }

    @Test
    public void testDeleteParkingSpotWithInvalidName() {
        testDeleteParkingSpotTypeFailure(INVALID_PARKING_SPOT_TYPE_NAME, 
        "no such parking spot type exist! ");
    }


    private void testCreateParkingSpotTypeFailure(String name, double fee, String message) {
        ParkingSpotType parkingSpotType = null;
        String error = "";
        try {
			parkingSpotType = parkingSpotTypeService.createParkingSpotType(name, fee);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}  
        assertNull(parkingSpotType);
        assertEquals(message, error);
    }

    private void testUpdateParkingSpotTypeFailure(String name, double fee, String message) {
        ParkingSpotType parkingSpotType = null;
        String error = "";
        try {
			parkingSpotType = parkingSpotTypeService.updateParkingSpotTypeFee(name, fee);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertNull(parkingSpotType);
        assertEquals(message, error);

    }

    private void testDeleteParkingSpotTypeFailure(String name, String message) {
        String error = "";
        ParkingSpotType parkingSpotType = null;
        try {
		    parkingSpotType = parkingSpotTypeService.deleteParkingSpotType(name);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertNull(parkingSpotType);
        assertEquals(message, error);
    
    }

    // dummy objects //

    private ParkingSpotType dummyParkingSpotType(String Name, double fee){
        ParkingSpotType parkingSpotType = new ParkingSpotType();
        parkingSpotType.setFee(fee);
        parkingSpotType.setName(Name);
        return parkingSpotType;
    }

    private ParkingSpot dummyParkingSpot(int id, ParkingSpotType parkingSpotType) {
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setId(id);
        parkingSpot.setType(parkingSpotType);
        return parkingSpot;
    }


}

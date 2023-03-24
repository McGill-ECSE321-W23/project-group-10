package ca.mcgill.ecse321.parkinglotsystem.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
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

    private static final String PARKING_SPOT_NAME_STRING = "TestSpotType";
    private static final double PARKING_FEE = 1;

    @BeforeEach
	public void setMockOutput() {
        lenient().when(parkingSpotTypeRepository.findParkingSpotTypeByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(PARKING_SPOT_NAME_STRING)) {
				ParkingSpotType parkingSpotType = new ParkingSpotType();
                parkingSpotType.setName(PARKING_SPOT_NAME_STRING);
                parkingSpotType.setFee(1);
				return parkingSpotType;
			} else {
				return null;
			}
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

        lenient().when(parkingSpotTypeRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<ParkingSpotType> parkingSpotTypes = new ArrayList<ParkingSpotType>();
            ParkingSpotType parkingSpotType = new ParkingSpotType();
            parkingSpotType.setName(PARKING_SPOT_NAME_STRING);
            parkingSpotType.setFee(1);
            parkingSpotTypes.add(parkingSpotType);
            return parkingSpotTypes;
        });

        lenient().when(parkingSpotRepository.findParkingSpotByType(any(ParkingSpotType.class))).thenAnswer((InvocationOnMock invocation) -> {

            // create parking spot type
            ParkingSpotType parkingSpotType = new ParkingSpotType();
            parkingSpotType.setName(PARKING_SPOT_NAME_STRING);
            parkingSpotType.setFee(1);

            // create parking spot
            List<ParkingSpot> parkingSpots = new ArrayList<>();
            ParkingSpot parkingSpot = new ParkingSpot();
            parkingSpot.setId(1);
            parkingSpot.setType(parkingSpotType);
            parkingSpots.add(parkingSpot);
            return parkingSpots;
        });
        lenient().when(parkingSpotTypeRepository.save(any(ParkingSpotType.class))).thenAnswer(returnParameterAsAnswer);
    }


    @Test
    public void testCreateParkingSpotType() {
        String name = "TestSpotType";
        double fee = 1.0;
        ParkingSpotType parkingSpotType = null;
        try {
			parkingSpotType = parkingSpotTypeService.createParkingSpotType(name, fee);
		} catch (CustomException e) {
			// Check that no error occurred
			fail(e.getMessage());
		}
        assertNotNull(parkingSpotType);
        assertEquals(name, parkingSpotType.getName());
        assertEquals(fee, parkingSpotType.getFee());
    }

    @Test
    public void testCreateParkingSpotTypeWithInvalidFee() {
        String name = "TestSpotType";
        double fee = -2;
        String error = "";
        ParkingSpotType parkingSpotType = null;
        try {
			parkingSpotType = parkingSpotTypeService.createParkingSpotType(name, fee);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        
        assertEquals(error, "Parking spot type fee cannot be less than or equal to zero! ");
    }

    @Test
    public void testCreateParkingSpotTypeWithZeroFee() {
        String name = "TestSpotType";
        double fee = 0.0;
        String error = "";
        ParkingSpotType parkingSpotType = null;
        try {
			parkingSpotType = parkingSpotTypeService.createParkingSpotType(name, fee);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        
        assertEquals(error, "Parking spot type fee cannot be less than or equal to zero! ");
    }

    @Test
    public void testCreateParkingSpotTypeWithInvalidName() {
        String name = "";
        double fee = 2;
        String error = "";
        ParkingSpotType parkingSpotType = null;
        try {
			parkingSpotType = parkingSpotTypeService.createParkingSpotType(name, fee);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        
        assertEquals("Parking spot type name cannot be empty! ", error);
    }
    @Test
    public void testUpdateParkingTypeFee() {
        ParkingSpotType parkingSpotType = null;
        String error = "";
        try {
			parkingSpotType = parkingSpotTypeService.updateParkingSpotTypeFee(PARKING_SPOT_NAME_STRING, 2);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals(2, parkingSpotType.getFee());
        assertEquals(PARKING_SPOT_NAME_STRING, parkingSpotType.getName());
        
    }

    @Test
    public void testUpdateParkingTypeFeeWithInvalidName() {
        String error = "";
        try {
			ParkingSpotType parkingSpotType = parkingSpotTypeService.updateParkingSpotTypeFee("", 2);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals(
            "Parking spot type name cannot be empty! Could not find a parking spot type by this name to update! ",
            error );
    }

    @Test
    public void testUpdateParkingTypeFeeWithInvalidFee() {
        String error = "";
        try {
			ParkingSpotType parkingSpotType = parkingSpotTypeService.updateParkingSpotTypeFee("l", -2);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals(
            "Parking spot type fee cannot be less than zero! Could not find a parking spot type by this name to update! ",
            error );
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
        assertEquals(PARKING_SPOT_NAME_STRING, parkingSpotTypes.get(0).getName()); 
    }

    @Test
    public void testGetParkingSpotTypeByName(){
        String error = "";
        ParkingSpotType parkingSpotType = null;
        try {
		    parkingSpotType = parkingSpotTypeService.getParkingSpotTypeByName(PARKING_SPOT_NAME_STRING);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals(
            PARKING_SPOT_NAME_STRING,
            parkingSpotType.getName() );
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
        assertEquals(
            "Parking spot type name cannot be empty! ",
            error);
    }

    @Test
    public void testDeleteParkingSpot() {
        String error = "";
        ParkingSpotType parkingSpotType = null;
        try {
		    parkingSpotType = parkingSpotTypeService.deleteParkingSpotType(PARKING_SPOT_NAME_STRING);
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("This type is assigned to a parking spot ", error);
        // assertEquals(PARKING_FEE, parkingSpotType.getFee());
    }

    @Test
    public void testDeleteParkingSpotWithInvalidName() {
        String error = "";
        ParkingSpotType parkingSpotType = null;
        try {
		    parkingSpotType = parkingSpotTypeService.deleteParkingSpotType("");
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("a name must be mention to delete parking spot type! no such parking spot type exist! ", error);
    
    }

    @Test
    public void testDeleteParkingSpotWith() {
        String error = "";
        ParkingSpotType parkingSpotType = null;
        try {
		    parkingSpotType = parkingSpotTypeService.deleteParkingSpotType("");
		} catch (CustomException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
        assertEquals("a name must be mention to delete parking spot type! no such parking spot type exist! ", error);
    
    }
}

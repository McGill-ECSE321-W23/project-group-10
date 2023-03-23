package ca.mcgill.ecse321.parkinglotsystem.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;

@ExtendWith(MockitoExtension.class)
public class TestParkingSpotTypeService {
    
    @Mock
    private ParkingSpotTypeRepository parkingSpotTypeRepository;

    @InjectMocks
    private ParkingSpotTypeService parkingSpotTypeService;

    private static final String PARKING_SPOT_NAME_STRING = "TestSpotType";

    @BeforeEach
	public void setMockOutput() {
        lenient().when(parkingSpotTypeRepository.findById(anyString())).thenAnswer((InvocationOnMock invocation) -> {
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
        lenient().when(parkingSpotTypeRepository.save(any(ParkingSpotType.class))).thenAnswer(returnParameterAsAnswer);
    }


    @Test
    public void testcreateParkingSpotType() {
        String name = "TestSpotType";
        double fee = 1.0;
        ParkingSpotType parkingSpotType = null;
        try {
			parkingSpotType = parkingSpotTypeService.createParkingSpotType(name, fee);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail(e.getMessage());
		}
        assertNotNull(parkingSpotType);
        assertEquals(name, parkingSpotType.getName());
        assertEquals(fee, parkingSpotType.getFee());
    }

    @Test
    public void testDeleteParkingSpotTypes() {
        String name = "TestSpotType";
        double fee = 1.0;
        ParkingSpotType parkingSpotType = null;
        try {
			parkingSpotType = parkingSpotTypeService.createParkingSpotType(name, fee);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail(e.getMessage());
		}
        assertEquals(1, parkingSpotTypeRepository.count());
        assertNotNull(parkingSpotType);
        assertEquals(name, parkingSpotType.getName());
        assertEquals(fee, parkingSpotType.getFee());
        assertEquals(name, parkingSpotTypeRepository.findParkingSpotTypeByName(name).getName());
        ParkingSpotType parkingSpotType1 = null;
        try {
			parkingSpotType1 = parkingSpotTypeService.deleteParkingSpotType(name);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail(e.getMessage());
		}
        assertNotNull(parkingSpotType1);
        assertNull(parkingSpotTypeService.getParkingSpotTypeByName(name));
    }
}

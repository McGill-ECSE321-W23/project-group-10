package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.*;


@ExtendWith(MockitoExtension.class)
public class TestParkingSpotService {

    // parking spot type variables
    private static final String PARKING_SPOT_TYPE_NAME = "TestType";
    private static final int PARKING_SPOT_TYPE_FEE = 1;

    // parking spot variables
    private static final int PARKING_SPOT_ID = 1;

    @Mock
    private ParkingSpotTypeRepository parkingSpotTypeRepository;
    @Mock
    private ParkingSpotRepository parkingSpotRepository;
    @InjectMocks
    private ParkingSpotService parkingSpotService;

    @BeforeEach
	public void setMockOutput() {

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
    }
    
}

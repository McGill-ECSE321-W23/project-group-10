package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
// import org.mockito.junit.jupiter;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingLotSystemRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingLotSystem;

@ExtendWith(MockitoExtension.class)
public class ParkingLotSystemServiceTest {

    @Mock
    private ParkingLotSystemRepository repository;
    
    @InjectMocks
    private ParkingLotSystemService service;

    private static final int VALID_ID = 1;
    private static final int INVALID_ID = 2;
    private static final String VALID_CUSTOMER_EMAIL_INACTIVE = "inactiveEmail";
    private static final String VALID_CUSTOMER_EMAIL_ACTIVE = "activeEmail";
    private static final String INVALID_CUSTOMER_EMAIL = "invalidEmail";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(repository.findParkingLotSystemById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).euqals(VALID_ID)){
                return dummy(VALID_ID, 1, dummyC)
            }
        })
    }

}

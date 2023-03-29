package ca.mcgill.ecse321.parkinglotsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingLotSystemRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingLotSystem;

@ExtendWith(MockitoExtension.class)
public class ParkingLotSystemServiceTests {

    @Mock
    private ParkingLotSystemRepository repository;

    @InjectMocks
    private ParkingLotSystemService service;

    private static final int VALID_ID = 1;
    private static final int REPEATED_ID = 3;
    private static final int NOTEXIST_ID = 4;
    private static final Time VALID_OP_TIME = Time.valueOf("11:30:00");
    private static final Time INVALID_OP_TIME = Time.valueOf("11:30:30");
    private static final Time VALID_CL_TIME = Time.valueOf("15:30:00");
    private static final Time INVALID_CL_TIME = Time.valueOf("15:30:30");

    @BeforeEach
    public void setMockOutput() {
        lenient().when(repository.findParkingLotSystemById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(VALID_ID)) {
                return dummy(VALID_ID, VALID_OP_TIME, VALID_CL_TIME);
            }
            return null;
        });

        lenient().when(repository.findParkingLotSystemByOpenTime(any(Time.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<ParkingLotSystem> plses = new ArrayList<>();
                    if (invocation.getArgument(0).equals(VALID_OP_TIME)) {
                        plses.add(dummy(VALID_ID, VALID_OP_TIME, VALID_CL_TIME));
                        return plses;
                    }
                    return null;
                });

        lenient().when(repository.findParkingLotSystemByCloseTime(any(Time.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<ParkingLotSystem> plses = new ArrayList<>();
                    if (invocation.getArgument(0).equals(VALID_CL_TIME)) {
                        plses.add(dummy(VALID_ID, VALID_OP_TIME, VALID_CL_TIME));
                        return plses;
                    }
                    return null;
                });

        lenient().when(repository.countParkingLotSystemById(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(NOTEXIST_ID)) {
                        return -1;
                    }
                    return 1;
                });

        lenient().when(repository.countParkingLotSystemByOpenTime(any(Time.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(INVALID_OP_TIME) || 
                        invocation.getArgument(0).equals(INVALID_CL_TIME)) {
                        return -1;
                    }
                    return 1;
                });

        lenient().when(repository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<ParkingLotSystem> subs = new ArrayList<>();
            subs.add(dummy(VALID_ID, VALID_OP_TIME, VALID_CL_TIME));
            subs.add(dummy(VALID_ID + 1, VALID_OP_TIME, VALID_CL_TIME));
            return subs;
        });

        lenient().when(repository.save(any(ParkingLotSystem.class))).thenAnswer((InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        });
    }

    @Test
    public void testCreateParking() {
        ParkingLotSystem pls = service.createParkingLotSystem(NOTEXIST_ID, VALID_OP_TIME, VALID_CL_TIME);
        assertNotNull(pls);
        assertEquals(NOTEXIST_ID, pls.getId());
        assertEquals(VALID_OP_TIME, pls.getOpenTime());
        assertEquals(VALID_CL_TIME, pls.getCloseTime());
    }

    @Test
    public void testCreateRepeatedParking() {
        ParkingLotSystem pls = null;
        String errMsg = "";
        try {
            pls = service.createParkingLotSystem(REPEATED_ID, VALID_OP_TIME, VALID_CL_TIME);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(pls);
        assertEquals("The ParkingLostSystem Id already exists", errMsg);
    }

    @Test
    public void testGetById() {
        ParkingLotSystem pls = service.getById(VALID_ID);
        assertNotNull(pls);
        assertEquals(VALID_ID, pls.getId());
    }

    @Test
    public void testGetByInvalidId() {
        ParkingLotSystem pls = null;
        String errMsg = "";
        try {
            pls = service.getById(NOTEXIST_ID);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(pls);
        assertEquals("The ParkingLostSystem Id does not exist", errMsg);
    }

    @Test
    public void testGetAllByOpenTime() {
        List<ParkingLotSystem> plses = service.getAllByOpenTime(VALID_OP_TIME);
        assertNotNull(plses);
        for (ParkingLotSystem pls : plses) {
            assertNotNull(pls);
            assertEquals(VALID_ID, pls.getId());
            assertEquals(VALID_OP_TIME, pls.getOpenTime());
            assertEquals(VALID_CL_TIME, pls.getCloseTime());
        }
    }

    @Test
    public void testGetAllByInvalidOpenTime() {
        List<ParkingLotSystem> plses = null;
        String errMsg = "";
        try {
            plses = service.getAllByOpenTime(INVALID_OP_TIME);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(plses);
        assertEquals("The ParkingLostSystem openTime does not exist", errMsg);
    }

    @Test
    public void testGetAllByCloseTime() {
        List<ParkingLotSystem> plses = service.getAllByCloseTime(VALID_CL_TIME);
        assertNotNull(plses);
        for (ParkingLotSystem pls : plses) {
            assertNotNull(pls);
            assertEquals(VALID_ID, pls.getId());
            assertEquals(VALID_OP_TIME, pls.getOpenTime());
            assertEquals(VALID_CL_TIME, pls.getCloseTime());
        }
    }

    @Test
    public void testGetAllByInvalidCloseTime() {
        List<ParkingLotSystem> plses = null;
        String errMsg = "";
        try {
            plses = service.getAllByCloseTime(INVALID_CL_TIME);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(plses);
        assertEquals("The ParkingLostSystem closeTime does not exist", errMsg);
    }

    @Test
    public void testGetAll() {
        List<ParkingLotSystem> plses = service.getAll();
        assertNotNull(plses);
        assertTrue(plses.size() > 0);
    }

    @Test
    public void testUpdatePark() {
        ParkingLotSystem pls = service.updateParkingLotSystem(VALID_ID, INVALID_OP_TIME, INVALID_CL_TIME);
        assertNotNull(pls);
        assertEquals(VALID_ID, pls.getId());
        assertEquals(INVALID_OP_TIME, pls.getOpenTime());
        assertEquals(INVALID_CL_TIME, pls.getCloseTime());
    }

    @Test
    public void testUpdateNonExistPark() {
        ParkingLotSystem pls = null;
        String errMsg = "";
        try {
            pls = service.updateParkingLotSystem(NOTEXIST_ID, INVALID_OP_TIME, INVALID_CL_TIME);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(pls);
        assertEquals("The ParkingLostSystem Id does not exist", errMsg);
    }

    private ParkingLotSystem dummy(int id, Time openTime, Time closeTime) {
        ParkingLotSystem pls = new ParkingLotSystem();
        pls.setId(id);
        pls.setOpenTime(openTime);
        pls.setCloseTime(closeTime);
        return pls;
    }

}

package ca.mcgill.ecse321.parkinglotsystem.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import java.sql.Date;
import java.sql.Time;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.parkinglotsystem.dao.SingleReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;



@ExtendWith(MockitoExtension.class)
public class TestSingleReservationService {
    
    @Mock
    private SingleReservationRepository singleReservationRepository;
    @Mock
    private ParkingSpotService parkingSpotService;
   
    
    
    @InjectMocks
    private SingleReservationService singleReservationService;
    
    
    private static final int RESERVATION_ID = 100;
    private static Calendar calendar = Calendar.getInstance();
    static {
    calendar.setTime(new Date(System.currentTimeMillis()));
    calendar.add(Calendar.MINUTE, -60);
    }
    // Get the updated date and time
    private static final Date date1 = new Date(calendar.getTimeInMillis());
    private static final int RESERVATION_ID2 = 999;
    private static final String license_number1 = "CA1234";
    private static final String license_number_unused = "CA1232";
    private static final String license_number2 = "QC5555";
    private static final int parking_time = 100;
    private static final int parking_time2 = 30;
    
    private static final int ParkingSpot_ID = 1000;
    private static final int ParkingSpot_ID_UNUSED = 1001;
    private static final int ParkingSpot_ID2 = 2500;
    
    //private static final String TYPE_NAME = "regular";
    //private static final String TYPE_NAME2 = "large";
    
    
    @BeforeEach
    public void setMockOutput() {
        lenient().when(singleReservationRepository.findSingleReservationById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(RESERVATION_ID)) {
                SingleReservation singleReservation =  new SingleReservation();
                singleReservation.setId(RESERVATION_ID);
                singleReservation.setDate(date1);
                singleReservation.setLicenseNumber(license_number1);
                singleReservation.setParkingTime(parking_time);
                singleReservation.setParkingSpot(parkingSpotService.getParkingSpotById(ParkingSpot_ID));
                return singleReservation;
            } 
            else if (invocation.getArgument(0).equals(RESERVATION_ID2)) {
                SingleReservation singleReservation =  new SingleReservation();
                singleReservation.setId(RESERVATION_ID2);
                singleReservation.setDate(date1);
                singleReservation.setLicenseNumber(license_number2);
                singleReservation.setParkingTime(parking_time2);
                return singleReservation;
            }
            else {
                return null;
            }
        });
    
        lenient().when(singleReservationService.getAllSingleReservations()).thenAnswer( (InvocationOnMock invocation) -> {
            SingleReservation singleReservation1 =  new SingleReservation();
            singleReservation1.setId(RESERVATION_ID);
            singleReservation1.setDate(date1);
            singleReservation1.setLicenseNumber(license_number1);
            singleReservation1.setParkingTime(parking_time);
            SingleReservation singleReservation2 =  new SingleReservation();
            singleReservation2.setId(RESERVATION_ID2);
            singleReservation2.setDate(date1);
            singleReservation2.setLicenseNumber(license_number2);
            singleReservation2.setParkingTime(parking_time2);
            List<SingleReservation> singleReservations = new ArrayList<SingleReservation>();
            singleReservations.add(singleReservation1);
            singleReservations.add(singleReservation2);
            
            return singleReservations;
        });
    
        lenient().when(singleReservationService.getSingleReservationsByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(date1)) {
                SingleReservation singleReservation =  new SingleReservation();
                singleReservation.setId(RESERVATION_ID);
                singleReservation.setDate(date1);
                singleReservation.setLicenseNumber(license_number1);
                singleReservation.setParkingTime(parking_time);
                List<SingleReservation> singleReservations = new ArrayList<SingleReservation>();
                singleReservations.add(singleReservation);
                return singleReservations;
            } 
            else if (invocation.getArgument(0).equals(date1)) {
                SingleReservation singleReservation =  new SingleReservation();
                singleReservation.setId(RESERVATION_ID2);
                singleReservation.setDate(date1);
                singleReservation.setLicenseNumber(license_number2);
                singleReservation.setParkingTime(parking_time2);
                List<SingleReservation> singleReservations = new ArrayList<SingleReservation>();
                singleReservations.add(singleReservation);
                return singleReservations;
            }
            else {
                return null;
            }
        });

        lenient().when(singleReservationRepository.findSingleReservationsByParkingSpot(any(ParkingSpot.class))).
        thenAnswer((InvocationOnMock invocation) -> {
            ParkingSpot spot = invocation.getArgument(0);
            List<SingleReservation> singleReservations = new ArrayList<SingleReservation>();
            if (spot.getId() == ParkingSpot_ID) {
                SingleReservation singleReservation =  new SingleReservation();
                singleReservation.setId(RESERVATION_ID);
                singleReservation.setDate(date1);
                singleReservation.setLicenseNumber(license_number1);
                singleReservation.setParkingTime(parking_time2);
                singleReservation.setParkingSpot(spot);
                singleReservations.add(singleReservation);
            } 
            else if (spot.getId() == ParkingSpot_ID2) {
                SingleReservation singleReservation =  new SingleReservation();
                singleReservation.setId(RESERVATION_ID2);
                singleReservation.setDate(date1);
                singleReservation.setLicenseNumber(license_number2);
                singleReservation.setParkingTime(parking_time2);
                singleReservation.setParkingSpot(spot);
                singleReservations.add(singleReservation);
            }
            return singleReservations;
        });
    
        lenient().when(parkingSpotService.getParkingSpotById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            ParkingSpotType type = new ParkingSpotType();
            type.setFee(0.50);
            type.setName("regular");
            ParkingSpot parkingSpot = new ParkingSpot();
            parkingSpot.setId(invocation.getArgument(0));
            parkingSpot.setType(type);
            return parkingSpot;
        
    });

    
        lenient().when(singleReservationService.getSingleReservationsByLicenseNumber(anyString())).thenAnswer( (InvocationOnMock invocation) -> { 
            List<SingleReservation> singleReservations = new ArrayList<SingleReservation>();
    
            if (invocation.getArgument(0).equals(license_number1)) {
                SingleReservation singleReservation1 =  new SingleReservation();
                singleReservation1.setId(RESERVATION_ID);
                singleReservation1.setDate(date1);
                singleReservation1.setLicenseNumber(license_number1);
                singleReservation1.setParkingTime(parking_time);
                singleReservations.add(singleReservation1);
            }
    
            else if (invocation.getArgument(0).equals(ParkingSpot_ID)){
                SingleReservation singleReservation2 =  new SingleReservation();
                singleReservation2.setId(RESERVATION_ID2);
                singleReservation2.setDate(date1);
                singleReservation2.setLicenseNumber(license_number2);
                singleReservation2.setParkingTime(parking_time2);
                singleReservations.add(singleReservation2);
            }
            return singleReservations;
    
            
    
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
                return invocation.getArgument(0);
            };
            lenient().when(singleReservationRepository.save(any(SingleReservation.class))).thenAnswer(returnParameterAsAnswer);
    }
    
    @Test
    public void testCreateSingleReservationSuccessfully() {
        assertEquals(2, singleReservationService.getAllSingleReservations().size());
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(license_number_unused, parking_time, ParkingSpot_ID_UNUSED);
        } catch (CustomException e) {
            fail(e.getMessage());
        }
        assertNotNull(singleReservation);
        assertEquals(license_number_unused, singleReservation.getLicenseNumber());
        assertEquals(parking_time, singleReservation.getParkingTime());
        assertEquals(ParkingSpot_ID_UNUSED, singleReservation.getParkingSpot().getId());
    
    }
    
    @Test
    public void testCreateSingleReservationWithInvalidParkingId() {
        
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(license_number1, parking_time, ParkingSpot_ID2);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("The parking spot is only available for monthly customers.", error);
    
    }

    @Test
    public void testCreateSingleReservationWithEmptyLicenseNumber() {
        String licenseNum = null;
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(licenseNum, parking_time, ParkingSpot_ID);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("license number cannot be empty", error);
    
    }

    @Test
    public void testCreateSingleReservationWithIncorrectLicenseNumberFormat() {
        String licenseNum = "!@!#$$";
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(licenseNum, parking_time, ParkingSpot_ID);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("Incorrect format for licenseNumber", error);
    
    }

    @Test
    public void testCreateSingleReservationWithNegativeParkingTime() {
        int parkingTime = -10;
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(license_number1, parkingTime, ParkingSpot_ID);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("ParkingTime cannot be negative", error);
    
    }

    @Test
    public void testCreateSingleReservationWithReservedParking() {
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(license_number1, parking_time, ParkingSpot_ID);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("The parking spot is currently reserved by another customer.", error);
    
    }

    @Test
    public void testCreateSingleReservationWithExistingReservation() {
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(license_number1, parking_time, ParkingSpot_ID_UNUSED);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("The license number already has an active subscription.", error);
    
    }
    
    @Test
    public void testUpdateSingleReservationSuccessfully() {
        assertEquals(2, singleReservationService.getAllSingleReservations().size());
    
        int newParkingTime = 75;
        int parkingTime = singleReservationService.getActiveByLicenseNumber(license_number1).getParkingTime();
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.updateSingleReservation(license_number1, newParkingTime);
        } catch (CustomException e) {
            fail(e.getMessage());
        }
        assertNotNull(singleReservation);
        assertEquals(license_number1, singleReservation.getLicenseNumber());
        assertEquals(parkingTime + newParkingTime, singleReservation.getParkingTime());
    
    }

    @Test
    public void testUpdateSingleReservationWithNoExistingLicense() {
        assertEquals(2 , singleReservationService.getAllSingleReservations().size());
        String error = null;
        int newParkingTime = 75;
        String licenseNumber = "wwww";
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.updateSingleReservation(licenseNumber, newParkingTime);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("There is no active subscription with this License number", error);
    
    }

    @Test
    public void testUpdateSingleReservationWithEmptyLicense() {
        assertEquals(2 , singleReservationService.getAllSingleReservations().size());
        String error = null;
        int newParkingTime = 75;
        String licenseNumber = "";
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.updateSingleReservation(licenseNumber, newParkingTime);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("licenseNumber cannot be empty", error);
    
    }

    @Test
    public void testUpdateSingleReservationWithIncorrectLicenseFormat() {
        assertEquals(2 , singleReservationService.getAllSingleReservations().size());
        String error = null;
        int newParkingTime = 75;
        String licenseNumber = "812!()";
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.updateSingleReservation(licenseNumber, newParkingTime);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("Incorrect licenseNumber format", error);
    
    }

    @Test
    public void testUpdateSingleReservationWithNegativeParkingTime() {
        assertEquals(2 , singleReservationService.getAllSingleReservations().size());
        String error = null;
        int newParkingTime = -10;
        
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.updateSingleReservation(license_number1, newParkingTime);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("ParkingTime cannot be negative", error);
    
    }

    @Test
    public void testUpdateSingleReservationWithNoActiveSub() {
        assertEquals(2 , singleReservationService.getAllSingleReservations().size());
        String error = null;
        String licenseNumber = "ABC123";
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.updateSingleReservation(licenseNumber, parking_time);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("There is no active subscription with this License number", error);
    
    }
    
    @Test
    public void testDeleteSingleReservationSuccessfully() {
        assertEquals(2, singleReservationService.getAllSingleReservations().size());
    
        SingleReservation singleReservation = null; 
        try {
            singleReservation = singleReservationService.deleteSingleReservation(RESERVATION_ID);
        } catch (CustomException e) {
            fail();
        }
        SingleReservation savedSingleReservation = singleReservationService.getSingleReservationById(RESERVATION_ID);
        assertNotNull(savedSingleReservation);
        assertEquals(savedSingleReservation.getId(), singleReservation.getId());
        assertEquals(savedSingleReservation.getDate(), singleReservation.getDate());
        
    }
    @Test
    public void testDeleteSingleReservationWithNoExistingId() {
        assertEquals(2, singleReservationService.getAllSingleReservations().size());
    
        int id = 462;
        String error = null;
        SingleReservation singleReservation = null; 
        try {
            singleReservation = singleReservationService.deleteSingleReservation(id);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("Reservation does not exist.", error);
        
    }
    
    @Test
    public void testDeleteSingleReservationWithInvalidId() {
        assertEquals(2, singleReservationService.getAllSingleReservations().size());
    
        int id = -12;
        String error = null;
        SingleReservation singleReservation = null; 
        try {
            singleReservation = singleReservationService.deleteSingleReservation(id);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("ReservationId cannot be negative.", error);
        
    }
    
    @Test
    public void testGetSingleReservationById() {
        SingleReservation singleReservation = singleReservationService.getSingleReservationById(RESERVATION_ID);
        assertNotNull(singleReservation);
        assertEquals(RESERVATION_ID, singleReservation.getId());
    }

    @Test
    public void testGetSingleReservationByNonExistingId() {
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.getSingleReservationById(1234455);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        
        assertNull(singleReservation);
        assertEquals("SingleReservation not found", error);
    }
    
    @Test
    public void testGetSingleReservationsByDate() {
        List<SingleReservation> singleReservations = singleReservationService.getSingleReservationsByDate(date1);
        assertNotNull(singleReservations);
        assertEquals(singleReservations.get(0).getId(), RESERVATION_ID);
    }
    
    @Test
    public void testGetSingleReservationsByLicenseNumber() {
        List<SingleReservation> singleReservations = singleReservationService.getSingleReservationsByLicenseNumber(license_number1);
        assertNotNull(singleReservations);
        assertEquals(singleReservations.get(0).getId(), RESERVATION_ID);
    }
    @Test
    public void testGetSubWithoutAccountsByParkingSpot() {
        List<SingleReservation> singleReservations = singleReservationService.getSingleReservationsByParkingSpot(ParkingSpot_ID);
        assertNotNull(singleReservations);
        assertEquals(singleReservations.get(0).getId(), RESERVATION_ID);
    }
    @Test
    public void testGetAllSingleReservations() {
        List<SingleReservation> singleReservations = singleReservationService.getAllSingleReservations();
        assertNotNull(singleReservations);
        assertEquals(2, singleReservations.size());
        assertEquals(singleReservations.get(0).getId(), RESERVATION_ID);
        assertEquals(singleReservations.get(1).getId(), RESERVATION_ID2);
    }
    
    @Test
    public void testDeleteAllSingleReservations() {
        List<SingleReservation> singleReservations = singleReservationService.deleteAllSingleReservations();
        assertNotNull(singleReservations);
        assertEquals(2, singleReservations.size());
    }

    @Test 
    public void testGetActiveByLicenseNumber() {
        SingleReservation singleReservation = singleReservationService.getActiveByLicenseNumber(license_number1);
        assertNotNull(singleReservation);
        assertEquals(license_number1, singleReservation.getLicenseNumber());
        assertEquals(RESERVATION_ID, singleReservation.getId());
        assertEquals(date1, singleReservation.getDate());
    }

    

    @Test
    public void testCalculateFee() {
        SingleReservation singleReservation = singleReservationService.getSingleReservationById(RESERVATION_ID);
        ParkingSpotType type = new ParkingSpotType();
        type.setFee(0.50);
        type.setName("regular");;
        ParkingSpot spot = new ParkingSpot();
        spot.setId(ParkingSpot_ID2);
        spot.setType(type);
        singleReservation.setParkingSpot(spot);
        Double fee = singleReservationService.calculateFee(Time.valueOf("09:30:00"), RESERVATION_ID);
        assertEquals((Time.valueOf(LocalTime.now()).getTime() - Time.valueOf("09:30:00").getTime()) * type.getFee(), fee);

    }

    @Test
    public void testCalculateFeeWithNoReservationFound() {
        String error = null;
        try {
            singleReservationService.calculateFee(Time.valueOf("09:30:00"), 05050);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertEquals("singleReservation not found", error);

    }
    }
    

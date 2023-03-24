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
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
import ca.mcgill.ecse321.parkinglotsystem.dao.ReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.SingleReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.service.ParkingSpotService;
import ca.mcgill.ecse321.parkinglotsystem.service.SingleReservationService;


@ExtendWith(MockitoExtension.class)
public class TestSingleReservationService {
    
    @Mock
    private SingleReservationRepository singleReservationRepository;
    @Mock
    private ParkingSpotService parkingSpotService;
   
    
    
    @InjectMocks
    private SingleReservationService singleReservationService;
    
    
    private static final int RESERVATION_ID = 100;
    private static final Date date1 = Date.valueOf("2023-03-22");
    private static final int RESERVATION_ID2 = 999;
    private static final Date date2 = Date.valueOf("2023-03-23");
    private static final String license_number1 = "CA1234";
    private static final String license_number2 = "QC5555";
    private static final int parking_time = 30;
    private static final int parking_time2 = 60;
    
    private static final int ParkingSpot_ID = 1;
    private static final int ParkingSpot_ID2 = 2;
    
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
                singleReservation.setDate(date2);
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
            singleReservation2.setDate(date2);
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
            else if (invocation.getArgument(0).equals(date2)) {
                SingleReservation singleReservation =  new SingleReservation();
                singleReservation.setId(RESERVATION_ID2);
                singleReservation.setDate(date2);
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
    
        lenient().when(parkingSpotService.getParkingSpotById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(ParkingSpot_ID)) {
                ParkingSpotType type = new ParkingSpotType();
                type.setFee(0.50);
                type.setName("regular");
                ParkingSpot parkingSpot = new ParkingSpot();
                parkingSpot.setId(ParkingSpot_ID);
                parkingSpot.setType(type);
                return parkingSpot;
            } 
            else if (invocation.getArgument(0).equals(ParkingSpot_ID2)) {
                ParkingSpot parkingSpot = new ParkingSpot();
                parkingSpot.setId(ParkingSpot_ID2);
                return parkingSpot;
            }
            else {
                return null;
            }
        });

    
        lenient().when(singleReservationService.getSingleReservationsByLicenseNumber(anyString())).thenAnswer( (InvocationOnMock invocation) -> { 
            
    
            if (invocation.getArgument(0).equals(license_number1)) {
                SingleReservation singleReservation1 =  new SingleReservation();
                singleReservation1.setId(RESERVATION_ID);
                singleReservation1.setDate(date1);
                singleReservation1.setLicenseNumber(license_number1);
                singleReservation1.setParkingTime(parking_time);
                List<SingleReservation> singleReservations = new ArrayList<SingleReservation>();
                singleReservations.add(singleReservation1);
                return singleReservations;
            }
    
            else if (invocation.getArgument(0).equals(ParkingSpot_ID)){
                SingleReservation singleReservation2 =  new SingleReservation();
                singleReservation2.setId(RESERVATION_ID2);
                singleReservation2.setDate(date2);
                singleReservation2.setLicenseNumber(license_number2);
                singleReservation2.setParkingTime(parking_time2);
                List<SingleReservation> singleReservations = new ArrayList<SingleReservation>();
                singleReservations.add(singleReservation2);
                return singleReservations;
    
            }
            else {
                return null;
            }
    
            
    
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
                return invocation.getArgument(0);
            };
            lenient().when(singleReservationRepository.save(any(SingleReservation.class))).thenAnswer(returnParameterAsAnswer);
    }
    
    @Test
    public void testCreateSingleReservationSuccessfully() {
        assertEquals(2, singleReservationService.getAllSingleReservations().size());
        int newId = 1190;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(newId, date1, license_number1, parking_time, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
        assertNotNull(singleReservation);
        assertEquals(newId, singleReservation.getId());
        assertEquals(date1, singleReservation.getDate());
        assertEquals(license_number1, singleReservation.getLicenseNumber());
        assertEquals(parking_time, singleReservation.getParkingTime());
        assertEquals(ParkingSpot_ID, singleReservation.getParkingSpot().getId());
    
    }
    
    @Test
    public void testCreateSingleReservationWithNegativeId() {
        int id = -1;
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(id, date1, license_number1, parking_time, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("ReservationId cannot be negative.", error);
    
    }
    
    @Test
    public void testCreateSingleReservationWithExistingId() {
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(RESERVATION_ID, date1, license_number1, parking_time, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("ReservationId is in use.", error);
    
    }
    
    @Test
    public void testCreateSingleReservationWithEmptyDate() {
        int newId = 1190;
        Date date = null;
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(newId, date, license_number1, parking_time, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("date cannot be empty.", error);
    
    }
    
    @Test
    public void testCreateSingleReservationWithEmptyLicenseNumber() {
        int newId = 1190;
        String licenseNum = null;
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(newId, date1, licenseNum, parking_time, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("license number cannot be empty", error);
    
    }

    @Test
    public void testCreateSingleReservationWithIncorrectLicenseNumberFormat() {
        int newId = 1190;
        String licenseNum = "!@!#$$";
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(newId, date1, licenseNum, parking_time, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("Incorrect format for licenseNumber", error);
    
    }

    @Test
    public void testCreateSingleReservationWithNegativeParkingTime() {
        int newId = 1190;
        int parkingTime = -10;
        String error = null;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.createSingleReservation(newId, date1, license_number1, parkingTime, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("ParkingTime cannot be negative", error);
    
    }
    
    @Test
    public void testUpdateSingleReservationSuccessfully() {
        assertEquals(2, singleReservationService.getAllSingleReservations().size());
    
        
        Date newDate = Date.valueOf("2023-04-01");
        String newLicense = "QC0000";
        int newParkingTime = 75;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.updateSingleReservation(RESERVATION_ID, newDate, newLicense, newParkingTime, ParkingSpot_ID2);
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
        assertNotNull(singleReservation);
        assertEquals(RESERVATION_ID, singleReservation.getId());
        assertEquals(newDate, singleReservation.getDate());
        assertEquals(newLicense, singleReservation.getLicenseNumber());
        assertEquals(newParkingTime, singleReservation.getParkingTime());
        assertEquals(ParkingSpot_ID2, singleReservation.getParkingSpot().getId());
    
    }

    @Test
    public void testUpdateSingleReservationWithNoExistingID() {
        assertEquals(2 , singleReservationService.getAllSingleReservations().size());
        String error = null;
        int id = 99999;
        Date newDate = Date.valueOf("2023-04-01");
        String newLicense = "QC0000";
        int newParkingTime = 75;
        SingleReservation singleReservation = null;
        try {
            singleReservation = singleReservationService.updateSingleReservation(id, newDate, newLicense, newParkingTime, ParkingSpot_ID2);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(singleReservation);
        assertEquals("Single reservation not found", error);
    
    }
    
    @Test
    public void testDeleteSingleReservationSuccessfully() {
        assertEquals(2, singleReservationService.getAllSingleReservations().size());
    
        SingleReservation singleReservation = null; 
        try {
            singleReservation = singleReservationService.deleteSingleReservation(RESERVATION_ID);
        } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
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
    }
    

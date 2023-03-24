package ca.mcgill.ecse321.parkinglotsystem.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.SubWithoutAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.service.ParkingSpotService;
import ca.mcgill.ecse321.parkinglotsystem.service.SubWithoutAccountService;


@ExtendWith(MockitoExtension.class)
public class TestSubWithoutAccountService {
    
    @Mock
    private SubWithoutAccountRepository subWithoutAccountRepository;
    @Mock
    private ParkingSpotRepository parkingSpotRepository;
    @Mock
    private ParkingSpotTypeRepository parkingSpotTypeRepository;
    
    
    @InjectMocks
    private SubWithoutAccountService subWithoutAccountService;
    @InjectMocks
    private ParkingSpotService parkingSpotService;
    
    private static final int RESERVATION_ID = 100;
    private static final Date date1 = Date.valueOf("2023-03-22");
    private static final int RESERVATION_ID2 = 999;
    private static final Date date2 = Date.valueOf("2023-03-23");
    private static final String license_number1 = "CA1234";
    private static final String license_number2 = "QC5555";
    private static final int nbrMonths = 1;
    private static final int nbrMonths2 = 2;
    
    private static final int ParkingSpot_ID = 1;
    private static final int ParkingSpot_ID2 = 2;
    
    //private static final String TYPE_NAME = "regular";
    //private static final String TYPE_NAME2 = "large";
    
    
    @BeforeEach
    public void setMockOutput() {
        lenient().when(subWithoutAccountRepository.findSubWithoutAccountById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(RESERVATION_ID)) {
                SubWithoutAccount subWithoutAccount =  new SubWithoutAccount();
                subWithoutAccount.setId(RESERVATION_ID);
                subWithoutAccount.setDate(date1);
                subWithoutAccount.setLicenseNumber(license_number1);
                subWithoutAccount.setNbrMonths(nbrMonths);
                return subWithoutAccount;
            } 
            else if (invocation.getArgument(0).equals(RESERVATION_ID2)) {
                SubWithoutAccount subWithoutAccount =  new SubWithoutAccount();
                subWithoutAccount.setId(RESERVATION_ID2);
                subWithoutAccount.setDate(date2);
                subWithoutAccount.setLicenseNumber(license_number2);
                subWithoutAccount.setNbrMonths(nbrMonths2);
                return subWithoutAccount;
            }
            else {
                return null;
            }
        });
    
        lenient().when(subWithoutAccountRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
            SubWithoutAccount subWithoutAccount1 =  new SubWithoutAccount();
            subWithoutAccount1.setId(RESERVATION_ID);
            subWithoutAccount1.setDate(date1);
            subWithoutAccount1.setLicenseNumber(license_number1);
            subWithoutAccount1.setNbrMonths(nbrMonths);
            SubWithoutAccount subWithoutAccount2 =  new SubWithoutAccount();
            subWithoutAccount2.setId(RESERVATION_ID2);
            subWithoutAccount2.setDate(date2);
            List<SubWithoutAccount> subWithoutAccounts = new ArrayList<SubWithoutAccount>();
            subWithoutAccounts.add(subWithoutAccount1);
            subWithoutAccounts.add(subWithoutAccount2);
            subWithoutAccount2.setLicenseNumber(license_number2);
            subWithoutAccount2.setNbrMonths(nbrMonths2);
            return subWithoutAccounts;
        });
    
        lenient().when(subWithoutAccountRepository.findSubWithoutAccountsByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(date1)) {
                SubWithoutAccount subWithoutAccount =  new SubWithoutAccount();
                subWithoutAccount.setId(RESERVATION_ID);
                subWithoutAccount.setDate(date1);
                subWithoutAccount.setLicenseNumber(license_number1);
                subWithoutAccount.setNbrMonths(nbrMonths);
                List<SubWithoutAccount> subWithoutAccounts = new ArrayList<SubWithoutAccount>();
                subWithoutAccounts.add(subWithoutAccount);
                return subWithoutAccount;
            } 
            else if (invocation.getArgument(0).equals(date2)) {
                SubWithoutAccount subWithoutAccount =  new SubWithoutAccount();
                subWithoutAccount.setId(RESERVATION_ID2);
                subWithoutAccount.setDate(date2);
                subWithoutAccount.setLicenseNumber(license_number2);
                subWithoutAccount.setNbrMonths(nbrMonths2);
                List<SubWithoutAccount> subWithoutAccounts = new ArrayList<SubWithoutAccount>();
                subWithoutAccounts.add(subWithoutAccount);
                return subWithoutAccounts;
            }
            else {
                return null;
            }
        });
    
        lenient().when(parkingSpotRepository.findParkingSpotById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(ParkingSpot_ID)) {
                ParkingSpot parkingSpot = new ParkingSpot();
                parkingSpot.setId(ParkingSpot_ID);
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
    
        lenient().when(subWithoutAccountRepository.findSubWithoutAccountsByLicenseNumber(anyString())).thenAnswer( (InvocationOnMock invocation) -> { 
            
    
            if (invocation.getArgument(0).equals(license_number1)) {
                SubWithoutAccount subWithoutAccount1 =  new SubWithoutAccount();
                subWithoutAccount1.setId(RESERVATION_ID);
                subWithoutAccount1.setDate(date1);
                subWithoutAccount1.setLicenseNumber(license_number1);
                subWithoutAccount1.setNbrMonths(nbrMonths2);
                List<SubWithoutAccount> subWithoutAccounts = new ArrayList<SubWithoutAccount>();
                subWithoutAccounts.add(subWithoutAccount1);
                return subWithoutAccounts;
            }
    
            else if (invocation.getArgument(0).equals(ParkingSpot_ID)){
                SubWithoutAccount subWithoutAccount2 =  new SubWithoutAccount();
                subWithoutAccount2.setId(RESERVATION_ID2);
                subWithoutAccount2.setDate(date2);
                subWithoutAccount2.setLicenseNumber(license_number2);
                subWithoutAccount2.setNbrMonths(nbrMonths2);
                List<SubWithoutAccount> subWithoutAccounts = new ArrayList<SubWithoutAccount>();
                subWithoutAccounts.add(subWithoutAccount2);
                return subWithoutAccounts;
    
            }
            else {
                return null;
            }
    
            
    
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
                return invocation.getArgument(0);
            };
            lenient().when(subWithoutAccountRepository.save(any(SubWithoutAccount.class))).thenAnswer(returnParameterAsAnswer);
    }
    
    @Test
    public void testCreateSubWithoutAccountSuccessfully() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
    
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(RESERVATION_ID, date1, license_number1, nbrMonths, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(subWithoutAccount);
        assertEquals(RESERVATION_ID, subWithoutAccount.getId());
        assertEquals(date1, subWithoutAccount.getDate());
        assertEquals(license_number1, subWithoutAccount.getLicenseNumber());
        assertEquals(nbrMonths, subWithoutAccount.getNbrMonths());
        assertEquals(ParkingSpot_ID, subWithoutAccount.getParkingSpot().getId());
    
    }
    
    @Test
    public void testCreateSubWithoutAccountWithNegativeId() {
        int id = -1;
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(id, date1, license_number1, nbrMonths, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("ReservationId cannot be negative.", error);
    
    }
    
    @Test
    public void testCreateSubWithoutAccountWithExistingId() {
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(RESERVATION_ID, date1, license_number1, nbrMonths, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount );
        assertEquals("ReservationId is in use.", error);
    
    }
    
    @Test
    public void testCreateSubWithoutAccountWithEmptyDate() {
        Date date = null;
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(RESERVATION_ID, date, license_number1, nbrMonths, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("date cannot be empty.", error);
    
    }
    
    @Test
    public void testCreateSubWithoutAccountWithEmptyLicenseNumber() {
        String licenseNum = null;
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(RESERVATION_ID, date1, licenseNum, nbrMonths, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("license number cannot be empty", error);
    
    }

    @Test
    public void testCreateSubWithoutAccountWithIncorrectLicenseNumberFormat() {
        String licenseNum = "!@!#$$";
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(RESERVATION_ID, date1, licenseNum, nbrMonths, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("Incorrect format for licenseNumber", error);
    
    }

    @Test
    public void testCreateSubWithoutAccountWithInvalidNbrMonths() {
        int length = 0;
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(RESERVATION_ID, date1, license_number1, length, ParkingSpot_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("the length of reservation must be at least 1 month.", error);
    
    }
    
    @Test
    public void testUpdateSubWithoutAccountSuccessfully() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
    
        
        Date newDate = Date.valueOf("2023-04-01");
        int newParkingSpotId = 15;
        String newLicense = "QC0000";
        int newNbrMonths = 12;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(RESERVATION_ID, newDate, newLicense, newNbrMonths, newParkingSpotId);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(subWithoutAccount);
        assertEquals(RESERVATION_ID, subWithoutAccount.getId());
        assertEquals(newDate, subWithoutAccount.getDate());
        assertEquals(newLicense, subWithoutAccount.getLicenseNumber());
        assertEquals(newNbrMonths, subWithoutAccount.getNbrMonths());
        assertEquals(newParkingSpotId, subWithoutAccount.getParkingSpot().getId());
    
    }

    @Test
    public void testUpdateSubWithoutAccountWithNoExistingID() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
        String error = null;
        int id = 99999;
        Date newDate = Date.valueOf("2023-04-01");
        int newParkingSpotId = 15;
        String newLicense = "QC0000";
        int newNbrMonths = 12;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(id, newDate, newLicense, newNbrMonths, newParkingSpotId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("SubWithoutAccount not found", error);
    
    }

    @Test
    public void testUpdateSubWithoutAccountWithNegativeId() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
        String error = null;
        int id = -100;
        Date newDate = Date.valueOf("2023-04-01");
        int newParkingSpotId = 15;
        String newLicense = "QC0000";
        int newNbrMonths = 12;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(id, newDate, newLicense, newNbrMonths, newParkingSpotId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("ReservationId cannot be negative.", error);
    
    }

    @Test
    public void testUpdateSubWithoutAccountWithEmptyDate() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
        String error = null;
        int id = 99999;
        Date newDate = null;
        int newParkingSpotId = 15;
        String newLicense = "QC0000";
        int newNbrMonths = 12;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(id, newDate, newLicense, newNbrMonths, newParkingSpotId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("date cannot be empty.", error);
    
    }
    
    @Test
    public void testUpdateSubWithoutAccountWithEmptyLicenseNumber() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
        String error = null;
        int id = 99999;
        Date newDate = Date.valueOf("2023-04-01");
        int newParkingSpotId = 15;
        String newLicense = null;
        int newNbrMonths = 12;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(id, newDate, newLicense, newNbrMonths, newParkingSpotId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("licenseNumber cannot be empty", error);
    
    }

    @Test
    public void testUpdateSubWithoutAccountWithIncorrectLicenseNumberFormat() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
        String error = null;
        int id = 99999;
        Date newDate = Date.valueOf("2023-04-01");
        int newParkingSpotId = 15;
        String newLicense = "$#@";
        int newNbrMonths = 12;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(id, newDate, newLicense, newNbrMonths, newParkingSpotId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("Incorrect licenseNumber format", error);
    
    }

    @Test
    public void testUpdateSubWithoutAccountWithInvalidNbrMonths() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
        String error = null;
        int id = 99999;
        Date newDate = Date.valueOf("2023-04-01");
        int newParkingSpotId = 15;
        String newLicense = "QC0000";
        int newNbrMonths = 0;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(id, newDate, newLicense, newNbrMonths, newParkingSpotId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("the number of month must be a natural integer", error);
    
    }

    @Test
    public void testDeleteSubWithoutAccountSuccessfully() {
        assertEquals(2, subWithoutAccountService.getAllSubWithoutAccounts().size());
    
        SubWithoutAccount subWithoutAccount = null; 
        try {
            subWithoutAccount = subWithoutAccountService.deleteSubWithoutAccount(RESERVATION_ID);
        } catch (IllegalArgumentException e) {
            fail();
        }
        SubWithoutAccount savedsubWithoutAccount = subWithoutAccountService.getSubWithoutAccountById(RESERVATION_ID);
        assertNotNull(savedsubWithoutAccount);
        assertEquals(savedsubWithoutAccount.getId(), subWithoutAccount.getId());
        assertEquals(savedsubWithoutAccount.getDate(), subWithoutAccount.getId());
        
    }
    @Test
    public void testDeleteSubWithoutAccountWithNoExistingId() {
        assertEquals(2, subWithoutAccountService.getAllSubWithoutAccounts().size());
    
        int id = 462;
        String error = null;
        SubWithoutAccount subWithoutAccount = null; 
        try {
            subWithoutAccount = subWithoutAccountService.deleteSubWithoutAccount(id);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("Reservation does not exist.", error);
        
    }
    
    @Test
    public void testDeleteSubWithoutAccountWithInvalidId() {
        assertEquals(2, subWithoutAccountService.getAllSubWithoutAccounts().size());
    
        int id = -12;
        String error = null;
        SubWithoutAccount subWithoutAccount = null; 
        try {
            subWithoutAccount = subWithoutAccountService.deleteSubWithoutAccount(id);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(subWithoutAccount);
        assertEquals("ReservationId cannot be negative.", error);
        
    }
    
    @Test
    public void testGetSubWithoutAccountById() {
        SubWithoutAccount subWithoutAccount = subWithoutAccountService.getSubWithoutAccountById(RESERVATION_ID);
        assertNotNull(subWithoutAccount);
        assertEquals(RESERVATION_ID, subWithoutAccount.getId());
    }
    
    @Test
    public void testGetSubWithoutAccountsByDate() {
        List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountService.getSubWithoutAccountsByDate(date1);
        assertNotNull(subWithoutAccounts);
        assertEquals(subWithoutAccounts.get(0).getId(), RESERVATION_ID);
    }
    
    @Test
    public void testGetSubWithoutAccountsByParkingSpot() {
        List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountService.getSubWithoutAccountsByParkingSpot(ParkingSpot_ID);
        assertNotNull(subWithoutAccounts);
        assertEquals(subWithoutAccounts.get(0).getId(), RESERVATION_ID);
    }

    @Test
    public void testGetSubWithoutAccountsByLicenseNumber() {
        List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountService.getSubWithoutAccountsByLicenseNumber(license_number1);
        assertNotNull(subWithoutAccounts);
        assertEquals(subWithoutAccounts.get(0).getId(), RESERVATION_ID);
    }
    
    @Test
    public void testGetAllReservations() {
        List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountService.getAllSubWithoutAccounts();
        assertNotNull(subWithoutAccounts);
        assertEquals(2, subWithoutAccounts.size());
        assertEquals(subWithoutAccounts.get(0).getId(), RESERVATION_ID);
        assertEquals(subWithoutAccounts.get(1).getId(), RESERVATION_ID2);
    }
    
    @Test
    public void testDeleteAllReservations() {
        List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountService.getAllSubWithoutAccounts();
        subWithoutAccountService.deleteAllReservations();
        assertNotNull(subWithoutAccounts);
        assertEquals(0, subWithoutAccounts.size());
    }

    
    }
    


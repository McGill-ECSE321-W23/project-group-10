package ca.mcgill.ecse321.parkinglotsystem.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.parkinglotsystem.dao.SubWithoutAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;


@ExtendWith(MockitoExtension.class)
public class TestSubWithoutAccountService {
    
    @Mock
    private SubWithoutAccountRepository subWithoutAccountRepository;
    
    @Mock
    private ParkingSpotService parkingSpotService;
    
    
    @InjectMocks
    private SubWithoutAccountService subWithoutAccountService;
    
    
    private static final int RESERVATION_ID = 100;
    
    private static final Date date1 = Date.valueOf(LocalDate.now().minusYears(1));
    private static final int RESERVATION_ID2 = 999;
    private static final String license_number1 = "CA1234";
    private static final String license_number2 = "QC5555";
    private static final int nbrMonths = 999;
    private static final int nbrMonths2 = 1;
    
    private static final int ParkingSpot_ID = 2055;
    private static final int ParkingSpot_ID_UNUSED = 2056;
    private static final int ParkingSpot_ID2 = 1006;
    
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
                subWithoutAccount.setDate(date1);
                subWithoutAccount.setLicenseNumber(license_number2);
                subWithoutAccount.setNbrMonths(nbrMonths2);
                return subWithoutAccount;
            }
            else {
                return null;
            }
        });
    
        lenient().when(subWithoutAccountService.getAllSubWithoutAccounts()).thenAnswer( (InvocationOnMock invocation) -> {
            SubWithoutAccount subWithoutAccount1 =  new SubWithoutAccount();
            subWithoutAccount1.setId(RESERVATION_ID);
            subWithoutAccount1.setDate(date1);
            subWithoutAccount1.setLicenseNumber(license_number1);
            subWithoutAccount1.setNbrMonths(nbrMonths);
            SubWithoutAccount subWithoutAccount2 =  new SubWithoutAccount();
            subWithoutAccount2.setId(RESERVATION_ID2);
            subWithoutAccount2.setDate(date1);
            subWithoutAccount2.setLicenseNumber(license_number2);
            subWithoutAccount2.setNbrMonths(nbrMonths2);
            List<SubWithoutAccount> subWithoutAccounts = new ArrayList<SubWithoutAccount>();
            subWithoutAccounts.add(subWithoutAccount1);
            subWithoutAccounts.add(subWithoutAccount2);
            
            return subWithoutAccounts;
        });
    
        lenient().when(subWithoutAccountService.getSubWithoutAccountsByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(date1)) {
                SubWithoutAccount subWithoutAccount1 =  new SubWithoutAccount();
                subWithoutAccount1.setId(RESERVATION_ID);
                subWithoutAccount1.setDate(date1);
                subWithoutAccount1.setLicenseNumber(license_number1);
                subWithoutAccount1.setNbrMonths(nbrMonths);
            
                SubWithoutAccount subWithoutAccount2 =  new SubWithoutAccount();
                subWithoutAccount2.setId(RESERVATION_ID2);
                subWithoutAccount2.setDate(date1);
                subWithoutAccount2.setLicenseNumber(license_number2);
                subWithoutAccount2.setNbrMonths(nbrMonths2);
                List<SubWithoutAccount> subWithoutAccounts = new ArrayList<SubWithoutAccount>();
                subWithoutAccounts.add(subWithoutAccount1);
                subWithoutAccounts.add(subWithoutAccount2);
                return subWithoutAccounts;
            }
            else {
                return null;
            }
        });

        lenient().when(subWithoutAccountRepository.findSubWithoutAccountsByParkingSpot(any(ParkingSpot.class))).
            thenAnswer((InvocationOnMock invocation) -> {
                List<SubWithoutAccount> subWithoutAccounts= new ArrayList<>();
                ParkingSpot spot = invocation.getArgument(0);
                if(spot.getId() == ParkingSpot_ID) {
                    SubWithoutAccount subWithoutAccount =  new SubWithoutAccount();
                    subWithoutAccount.setId(RESERVATION_ID);
                    subWithoutAccount.setDate(date1);
                    subWithoutAccount.setLicenseNumber(license_number1);
                    subWithoutAccount.setNbrMonths(nbrMonths);
                    subWithoutAccount.setParkingSpot(spot);
                    subWithoutAccounts.add(subWithoutAccount);
                }
                else if(spot.getId() == ParkingSpot_ID2) {
                    SubWithoutAccount subWithoutAccount =  new SubWithoutAccount();
                    subWithoutAccount.setId(RESERVATION_ID2);
                    subWithoutAccount.setDate(date1);
                    subWithoutAccount.setLicenseNumber(license_number1);
                    subWithoutAccount.setNbrMonths(nbrMonths2);
                    subWithoutAccount.setParkingSpot(spot);
                    subWithoutAccounts.add(subWithoutAccount);
                }
                return subWithoutAccounts;
         });       

        lenient().when(subWithoutAccountRepository.save(any(SubWithoutAccount.class))).thenAnswer((InvocationOnMock invocation) -> {
            SubWithoutAccount subWithoutAccount = invocation.getArgument(0);
            subWithoutAccount.setId(RESERVATION_ID);
            return subWithoutAccount;
        });
        
    
        lenient().when(parkingSpotService.getParkingSpotById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
                ParkingSpotType type = new ParkingSpotType();
                type.setFee(0.50);
                type.setName("regular");
                ParkingSpot parkingSpot = new ParkingSpot();
                parkingSpot.setId(invocation.getArgument(0));
                parkingSpot.setType(type);
                return parkingSpot;
            
        });
    
        lenient().when(subWithoutAccountService.getSubWithoutAccountsByLicenseNumber(anyString())).thenAnswer( (InvocationOnMock invocation) -> { 
            if (invocation.getArgument(0).equals(license_number1)) {
                SubWithoutAccount subWithoutAccount1 =  new SubWithoutAccount();
                subWithoutAccount1.setId(RESERVATION_ID);
                subWithoutAccount1.setDate(date1);
                subWithoutAccount1.setLicenseNumber(license_number1);
                subWithoutAccount1.setNbrMonths(nbrMonths);
                List<SubWithoutAccount> subWithoutAccounts = new ArrayList<SubWithoutAccount>();
                subWithoutAccounts.add(subWithoutAccount1);
                return subWithoutAccounts;
            }
    
            else if (invocation.getArgument(0).equals(license_number2)){
                SubWithoutAccount subWithoutAccount2 =  new SubWithoutAccount();
                subWithoutAccount2.setId(RESERVATION_ID2);
                subWithoutAccount2.setDate(date1);
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
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(license_number2, ParkingSpot_ID_UNUSED);
        } catch (CustomException e) {
            fail(e.getMessage());
        }
        assertNotNull(subWithoutAccount);
        assertEquals(license_number2, subWithoutAccount.getLicenseNumber());
        assertEquals(1, subWithoutAccount.getNbrMonths());
        assertEquals(ParkingSpot_ID_UNUSED, subWithoutAccount.getParkingSpot().getId());
    
    }
    
    @Test
    public void testCreateSubWithoutAccountWithInvalidParkingId() {
        
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(license_number1, ParkingSpot_ID2);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(subWithoutAccount);
        assertEquals("The parking spot is not available for guest customers.", error);
    
    }

    @Test
    public void testCreateSubWithoutAccountWithTakenParkingSpot() {
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(license_number2, ParkingSpot_ID);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(subWithoutAccount);
        assertEquals("The parking spot is currently reserved by another customer.", error);
    
    }
    
    @Test
    public void testCreateSubWithoutAccountWithEmptyLicenseNumber() {
        String licenseNum = "";
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(licenseNum, ParkingSpot_ID);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(subWithoutAccount);
        assertEquals("licenseNumber cannot be empty", error);
    
    }

    @Test
    public void testCreateSubWithoutAccountWithIncorrectLicenseNumberFormat() {
        String licenseNum = "!@!#$$";
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(licenseNum, ParkingSpot_ID);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(subWithoutAccount);
        assertEquals("Incorrect licenseNumber format", error);
    
    }

    @Test
    public void testCreateSubWithoutAccountWithExistingReservation() {
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(license_number1, ParkingSpot_ID_UNUSED);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(subWithoutAccount);
        assertEquals("The license number already has an active subscription.", error);
    }
    
    @Test
    public void testUpdateSubWithoutAccountSuccessfully() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
        int num = subWithoutAccountService.getActiveByLicenseNumber(license_number1).getNbrMonths();

        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.updateSubWithoutAccount(license_number1,num+1);
        } catch (CustomException e) {
            fail(e.getMessage());
        }
        assertNotNull(subWithoutAccount);
        assertEquals(license_number1, subWithoutAccount.getLicenseNumber());
        assertEquals(num + 1, subWithoutAccount.getNbrMonths());
    
    }

    @Test
    public void testUpdateSubWithoutAccountWithNoExistingLicense() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
        String error = null;;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.updateSubWithoutAccount(license_number2,1);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(subWithoutAccount);
        assertEquals("There is no active subscription with this License number", error);
    
    }
    
    @Test
    public void testUpdateSubWithoutAccountWithEmptyLicenseNumber() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
        String error = null;
        String newLicense = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.updateSubWithoutAccount(newLicense,1);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(subWithoutAccount);
        assertEquals("licenseNumber cannot be empty", error);
    
    }

    @Test
    public void testUpdateSubWithoutAccountWithIncorrectLicenseNumberFormat() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
        String error = null;
        String newLicense = "$#@";
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.updateSubWithoutAccount(newLicense,1);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(subWithoutAccount);
        assertEquals("Incorrect licenseNumber format", error);
    
    }

    @Test
    public void testUpdateSubWithoutAccountWithNoActiveSub() {
        assertEquals(2 , subWithoutAccountService.getAllSubWithoutAccounts().size());
        String error = null;
        String licenseNumber = "ABC123";
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.updateSubWithoutAccount(licenseNumber,1);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(subWithoutAccount);
        assertEquals("There is no active subscription with this License number", error);
    
    }

    @Test
    public void testDeleteSubWithoutAccountSuccessfully() {
        assertEquals(2, subWithoutAccountService.getAllSubWithoutAccounts().size());
        SubWithoutAccount savedsubWithoutAccount = subWithoutAccountService.getSubWithoutAccountById(RESERVATION_ID);
        SubWithoutAccount subWithoutAccount = null; 
        try {
            subWithoutAccount = subWithoutAccountService.deleteSubWithoutAccount(RESERVATION_ID);
        } catch (CustomException e) {
            fail(e.getMessage());
        }
        
        assertNotNull(subWithoutAccount);
        assertEquals(savedsubWithoutAccount.getId(), subWithoutAccount.getId());
        
        
    }
    @Test
    public void testDeleteSubWithoutAccountWithNoExistingId() {
        assertEquals(2, subWithoutAccountService.getAllSubWithoutAccounts().size());
    
        int id = 462;
        String error = null;
        SubWithoutAccount subWithoutAccount = null; 
        try {
            subWithoutAccount = subWithoutAccountService.deleteSubWithoutAccount(id);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(subWithoutAccount);
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
        } catch (CustomException e) {
            error = e.getMessage();
        }
        assertNull(subWithoutAccount);
        assertEquals("ReservationId cannot be negative.", error);
        
    }
    
    @Test
    public void testGetSubWithoutAccountById() {
        SubWithoutAccount subWithoutAccount = subWithoutAccountService.getSubWithoutAccountById(RESERVATION_ID);
        assertNotNull(subWithoutAccount);
        assertEquals(RESERVATION_ID, subWithoutAccount.getId());
    }

    @Test
    public void testGetSubWithoutAccountByNonExistingId() {
        String error = null;
        SubWithoutAccount subWithoutAccount = null;
        try {
            subWithoutAccount = subWithoutAccountService.getSubWithoutAccountById(1234455);
        } catch (CustomException e) {
            error = e.getMessage();
        }
        
        assertNull(subWithoutAccount);
        assertEquals("SubWithoutAccount not found", error);
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
        assertEquals(1, subWithoutAccounts.size());
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
        List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountService.deleteAllSubWithoutAccounts();
        assertNotNull(subWithoutAccounts);
        assertEquals(2, subWithoutAccounts.size());
    }

    @Test
    public void testGetActiveByParkingSpot() {
        SubWithoutAccount sub = subWithoutAccountService.getActiveByParkingSpot(ParkingSpot_ID);
        assertNotNull(sub);
        assertEquals(ParkingSpot_ID, sub.getParkingSpot().getId());
    }

    @Test
    public void testHasActiveSingleReservationByParkingSpot() {
        boolean result = subWithoutAccountService.hasActiveByParkingSpot(ParkingSpot_ID);
        assertTrue(result);
    }
    
}

    


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
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.parkinglotsystem.dao.SubWithAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithAccount;

@ExtendWith(MockitoExtension.class)
public class SubWithAccountServiceTests {

    @Mock
    private SubWithAccountRepository repository;
    @Mock
    private MonthlyCustomerService monthlyCustomerService;
    @Mock
    private ParkingSpotService parkingSpotService;

    @InjectMocks
    private SubWithAccountService service;

    private static final int VALID_ID = 1;
    private static final int INVALID_ID = 2;
    private static final String VALID_CUSTOMER_EMAIL_INACTIVE = "inactiveEmail";
    private static final String VALID_CUSTOMER_EMAIL_ACTIVE = "activeEmail";
    private static final String INVALID_CUSTOMER_EMAIL = "invalidEmail";
    private static final int VALID_SPOT_ID_INACTIVE = 2500;
    private static final int VALID_SPOT_ID_ACTIVE = 2600;
    private static final int INVALID_SPOT_ID_1 = 1500;
    private static final int INVALID_SPOT_ID_2 = 3500;
    private static final int NBR_MONTHS_ACTIVE = 999;

    @BeforeEach
    public void setMockOutput() {

        lenient().when(repository.findSubWithAccountById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(VALID_ID)) {
                return dummy(VALID_ID, 1, dummyCustomer(VALID_CUSTOMER_EMAIL_INACTIVE),
                        dummyParkingSpot(VALID_SPOT_ID_INACTIVE));
            }
            return null;
        });

        lenient().when(repository.findSubWithAccountByCustomer(any(MonthlyCustomer.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<SubWithAccount> subs = new ArrayList<>();
                    MonthlyCustomer customer = invocation.getArgument(0);
                    if (customer.getEmail().equals(VALID_CUSTOMER_EMAIL_INACTIVE)) {
                        subs.add(dummy(1, 1, customer, dummyParkingSpot(VALID_SPOT_ID_INACTIVE)));
                    } else if (customer.getEmail().equals(VALID_CUSTOMER_EMAIL_ACTIVE)) {
                        subs.add(dummy(1, NBR_MONTHS_ACTIVE, customer, dummyParkingSpot(VALID_SPOT_ID_ACTIVE)));
                    }
                    return subs;
                });

        lenient().when(repository.findSubWithAccountByParkingSpot(any(ParkingSpot.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    List<SubWithAccount> subs = new ArrayList<>();
                    ParkingSpot spot = invocation.getArgument(0);
                    if (spot.getId() == VALID_SPOT_ID_INACTIVE) {
                        subs.add(dummy(1, 1, dummyCustomer(VALID_CUSTOMER_EMAIL_INACTIVE), spot));
                    } else if (spot.getId() == VALID_SPOT_ID_ACTIVE) {
                        subs.add(dummy(1, NBR_MONTHS_ACTIVE, dummyCustomer(VALID_CUSTOMER_EMAIL_ACTIVE), spot));
                    }
                    return subs;
                });

        lenient().when(repository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<SubWithAccount> subs = new ArrayList<>();
            subs.add(dummy(1, 1, null, null));
            subs.add(dummy(2, 1, null, null));
            return subs;
        });

        lenient().when(repository.save(any(SubWithAccount.class))).thenAnswer((InvocationOnMock invocation) -> {
            SubWithAccount sub = invocation.getArgument(0);
            sub.setId(VALID_ID);
            return sub;
        });

        lenient().when(monthlyCustomerService.getMonthlyCustomerByEmail(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    return dummyCustomer(invocation.getArgument(0));
                });

        lenient().when(parkingSpotService.getParkingSpotById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            return dummyParkingSpot(invocation.getArgument(0));
        });
    }

    @Test
    public void testCreateSub() {
        SubWithAccount sub = service.createSubWithAccount(VALID_CUSTOMER_EMAIL_INACTIVE, VALID_SPOT_ID_INACTIVE);
        assertNotNull(sub);
        var customer = sub.getCustomer();
        assertNotNull(customer);
        assertEquals(VALID_CUSTOMER_EMAIL_INACTIVE, sub.getCustomer().getEmail());
        var spot = sub.getParkingSpot();
        assertNotNull(spot);
        assertEquals(VALID_SPOT_ID_INACTIVE, spot.getId());
    }

    @Test
    public void testCreateSubInvalidParkingSpot1() {
        testCreateSubFailure(
                VALID_CUSTOMER_EMAIL_INACTIVE,
                INVALID_SPOT_ID_1,
                "The parking spot is not available for monthly customers.");
    }

    @Test
    public void testCreateSubInvalidParkingSpot2() {
        testCreateSubFailure(
                VALID_CUSTOMER_EMAIL_INACTIVE,
                INVALID_SPOT_ID_2,
                "The parking spot is not available for monthly customers.");
    }

    @Test
    public void testCreateSubReservedParkingSpot() {
        testCreateSubFailure(
                VALID_CUSTOMER_EMAIL_INACTIVE,
                VALID_SPOT_ID_ACTIVE,
                "The parking spot is currently reserved by another customer.");
    }

    @Test
    public void testCreateSubCustomerWithActiveSubscription() {
        testCreateSubFailure(
                VALID_CUSTOMER_EMAIL_ACTIVE,
                VALID_SPOT_ID_INACTIVE,
                "The monthly customer already has an active subscription.");
    }

    @Test
    public void testGetExistingSub() {
        SubWithAccount sub = service.getSubWithAccount(VALID_ID);
        assertNotNull(sub);
        assertEquals(VALID_ID, sub.getId());
    }

    @Test
    public void testGetNonExistingSub() {
        SubWithAccount sub = null;
        String errMsg = "";
        try {
            sub = service.getSubWithAccount(INVALID_ID);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(sub);
        assertEquals("The provided subscription ID is invalid.", errMsg);
    }

    @Test
    public void testGetExistingActiveSubByCustomer() {
        SubWithAccount sub = service.getActiveByCustomer(VALID_CUSTOMER_EMAIL_ACTIVE);
        assertNotNull(sub);
        assertEquals(VALID_CUSTOMER_EMAIL_ACTIVE, sub.getCustomer().getEmail());
    }

    @Test
    public void testGetNonExistingActiveSubByCustomer1() {
        SubWithAccount sub = null;
        String errMsg = "";
        try {
            sub = service.getActiveByCustomer(INVALID_CUSTOMER_EMAIL);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(sub);
        assertEquals("There is no active subscription", errMsg);
    }

    @Test
    public void testGetNonExistingActiveSubByCustomer2() {
        SubWithAccount sub = null;
        String errMsg = "";
        try {
            sub = service.getActiveByCustomer(VALID_CUSTOMER_EMAIL_INACTIVE);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(sub);
        assertEquals("There is no active subscription", errMsg);
    }

    @Test
    public void testGetExistingActiveSubByParkingSpot() {
        SubWithAccount sub = service.getActiveByParkingSpot(VALID_SPOT_ID_ACTIVE);
        assertNotNull(sub);
        assertEquals(VALID_SPOT_ID_ACTIVE, sub.getParkingSpot().getId());
    }

    @Test
    public void testGetNonExistingActiveSubByParkingSpot1() {
        SubWithAccount sub = null;
        String errMsg = "";
        try {
            sub = service.getActiveByParkingSpot(INVALID_SPOT_ID_1);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(sub);
        assertEquals("There is no active subscription", errMsg);
    }

    @Test
    public void testGetNonExistingActiveSubByParkingSpot2() {
        SubWithAccount sub = null;
        String errMsg = "";
        try {
            sub = service.getActiveByParkingSpot(VALID_SPOT_ID_INACTIVE);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertNull(sub);
        assertEquals("There is no active subscription", errMsg);
    }

    @Test
    public void testGetAllByCustomer() {
        List<SubWithAccount> subs = service.getAllByCustomer(VALID_CUSTOMER_EMAIL_INACTIVE);
        assertNotNull(subs);
        assertTrue(subs.size() > 0);
        for (SubWithAccount sub : subs) {
            assertEquals(VALID_CUSTOMER_EMAIL_INACTIVE, sub.getCustomer().getEmail());
        }
    }

    @Test
    public void testGetAllByParkingSpot() {
        List<SubWithAccount> subs = service.getAllByParkingSpot(VALID_SPOT_ID_INACTIVE);
        assertNotNull(subs);
        assertTrue(subs.size() > 0);
        for (SubWithAccount sub : subs) {
            assertEquals(VALID_SPOT_ID_INACTIVE, sub.getParkingSpot().getId());
        }
    }

    @Test
    public void testGetAll() {
        List<SubWithAccount> subs = service.getAll();
        assertNotNull(subs);
        assertTrue(subs.size() > 0);
    }

    @Test
    public void testUpdateSub() {
        SubWithAccount sub = service.updateSubWithAccount(VALID_CUSTOMER_EMAIL_ACTIVE);
        assertNotNull(sub);
        assertEquals(NBR_MONTHS_ACTIVE + 1, sub.getNbrMonths());
    }

    private void testCreateSubFailure(String monthlyCustomerEmail, int parkingSpotId, String message) {
        SubWithAccount sub = null;
        String errMsg = "";
        try {
            sub = service.createSubWithAccount(monthlyCustomerEmail, parkingSpotId);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        assertEquals(message, errMsg);
        assertNull(sub);
    }

    private MonthlyCustomer dummyCustomer(String email) {
        MonthlyCustomer customer = new MonthlyCustomer();
        customer.setEmail(email);
        customer.setLicenseNumber("someLicense");
        customer.setName("Marco");
        customer.setPassword("123");
        customer.setPhone("1234567890");
        return customer;
    }

    private ParkingSpot dummyParkingSpot(int id) {
        ParkingSpotType spotType = new ParkingSpotType();
        spotType.setName("someName");
        spotType.setFee(1);
        ParkingSpot spot = new ParkingSpot();
        spot.setId(id);
        spot.setType(spotType);
        return spot;
    }

    private SubWithAccount dummy(int id, int nbrMonths, MonthlyCustomer customer, ParkingSpot parkingSpot) {
        SubWithAccount sub = new SubWithAccount();
        sub.setId(id);
        sub.setDate(Date.valueOf(LocalDate.now().minusYears(1)));
        sub.setNbrMonths(nbrMonths);
        sub.setCustomer(customer);
        sub.setParkingSpot(parkingSpot);
        return sub;
    }
}
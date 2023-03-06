package ca.mcgill.ecse321.parkinglotsystem.dao;

import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservationRepositoryTests {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;
    @Autowired
    private ParkingSpotTypeRepository parkingSpotTypeRepository;
    @Autowired
    private MonthlyCustomerRepository monthlyCustomerRepository;

    @AfterEach
    public void clearDataBase() {
        reservationRepository.deleteAll();
        parkingSpotRepository.deleteAll();
        parkingSpotTypeRepository.deleteAll();
        monthlyCustomerRepository.deleteAll();
    }

    @Test
    public void testPersistenceAndLoadReservation() {
        // Create test data
        ParkingSpotType typeForSingle = new ParkingSpotType();
        typeForSingle.setName("regular");
        typeForSingle.setFee(3.50);
        typeForSingle = parkingSpotTypeRepository.save(typeForSingle);

        ParkingSpot spotForSingle = new ParkingSpot();
        spotForSingle.setId(2);
        spotForSingle.setType(typeForSingle);
        spotForSingle = parkingSpotRepository.save(spotForSingle);

        ParkingSpotType typeForSubWithAccount = new ParkingSpotType();
        typeForSubWithAccount.setName("regular");
        typeForSubWithAccount.setFee(20);
        typeForSubWithAccount = parkingSpotTypeRepository.save(typeForSubWithAccount);

        ParkingSpot spotForSubWithAccount = new ParkingSpot();
        spotForSubWithAccount.setId(3);
        spotForSubWithAccount.setType(typeForSubWithAccount);
        spotForSubWithAccount = parkingSpotRepository.save(spotForSubWithAccount);

        MonthlyCustomer customer1 = new MonthlyCustomer();
        customer1.setEmail("customer1@gmail.com");
        customer1.setName("Jennifer Black");
        customer1.setPassword("123456");
        customer1.setPhone("514-320-1349");
        customer1.setLicenseNumber("CA1562");
        customer1 = monthlyCustomerRepository.save(customer1);

        ParkingSpotType typeForSubWithoutAccount = new ParkingSpotType();
        typeForSubWithoutAccount.setName("regular");
        typeForSubWithoutAccount.setFee(20);
        typeForSubWithoutAccount = parkingSpotTypeRepository.save(typeForSubWithoutAccount);

        ParkingSpot spotForSubWithoutAccount = new ParkingSpot();
        spotForSubWithoutAccount.setId(4);
        spotForSubWithoutAccount.setType(typeForSubWithoutAccount);
        spotForSubWithoutAccount = parkingSpotRepository.save(spotForSubWithoutAccount);

        // Create new reservations
        SingleReservation singleReservation = new SingleReservation();
        SubWithAccount subWithAccount = new SubWithAccount();
        SubWithoutAccount subWithoutAccount = new SubWithoutAccount();
        singleReservation.setDate(Date.valueOf("2023-02-28"));
        singleReservation.setLicenseNumber("CA021B");
        singleReservation.setParkingSpot(spotForSingle);
        singleReservation.setParkingTime(30);

        subWithAccount.setDate(Date.valueOf("2023-02-28"));
        subWithAccount.setNbrMonths(3);
        subWithAccount.setParkingSpot(spotForSubWithAccount);
        subWithAccount.setCustomer(customer1);

        subWithoutAccount.setDate(Date.valueOf("2023-02-28"));
        subWithoutAccount.setLicenseNumber("CA123E");
        subWithoutAccount.setNbrMonths(5);
        subWithoutAccount.setParkingSpot(spotForSubWithoutAccount);


        // Save the reservations to the ReservationRepository
        singleReservation = reservationRepository.save(singleReservation);
        subWithAccount = reservationRepository.save(subWithAccount);
        subWithoutAccount = reservationRepository.save(subWithoutAccount);
        int idSingle = singleReservation.getId();
        int idSubWith = subWithAccount.getId();
        int idSubWithOut = subWithoutAccount.getId();

        // Read the reservations from the ReservationRepository
        singleReservation = (SingleReservation) reservationRepository.findReservationById(idSingle);
        subWithAccount = (SubWithAccount) reservationRepository.findReservationById(idSubWith);
        subWithoutAccount = (SubWithoutAccount) reservationRepository.findReservationById(idSubWithOut);

        // assert all reservations have the correct attributes
        assertNotNull(singleReservation);
        assertNotNull(subWithoutAccount);
        assertNotNull(subWithAccount);
        assertEquals(Date.valueOf("2023-02-28"), singleReservation.getDate());
        assertEquals(Date.valueOf("2023-02-28"), subWithAccount.getDate());
        assertEquals(Date.valueOf("2023-02-28"), subWithoutAccount.getDate());
        assertEquals(spotForSingle.getId(), singleReservation.getParkingSpot().getId());
        assertEquals(spotForSubWithAccount.getId(), subWithAccount.getParkingSpot().getId());
        assertEquals(spotForSubWithoutAccount.getId(), subWithoutAccount.getParkingSpot().getId());
        assertEquals("CA021B", singleReservation.getLicenseNumber());
        assertEquals("CA123E", subWithoutAccount.getLicenseNumber());
        assertEquals(3, subWithAccount.getNbrMonths());
        assertEquals(5, subWithoutAccount.getNbrMonths());
        assertEquals(customer1.getEmail(), subWithAccount.getCustomer().getEmail());


    }
}

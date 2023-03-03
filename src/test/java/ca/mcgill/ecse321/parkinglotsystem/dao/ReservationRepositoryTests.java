package ca.mcgill.ecse321.parkinglotsystem.dao;

import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
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

    @AfterEach
    public void clearDataBase() {
        reservationRepository.deleteAll();
    }

    @Test
    public void testPersistenceAndLoadReservation() {
        // Create some data
        ParkingSpot spotForSingle = new ParkingSpot();
        spotForSingle.setId(2);
        // spotForSingle = ParkingSpotRepository.save(spotForSingle);
        ParkingSpotType typeForSingle = new ParkingSpotType();
        typeForSingle.setName("regular");
        typeForSingle.setFee(3.50);
        spotForSingle.setType(typeForSingle);
        // typeForSingle = ParkingSpotTypeRepository.save(typeForSingle);

        ParkingSpot spotForSubWithAccount = new ParkingSpot();
        spotForSubWithAccount.setId(3);
        //spotForSubWithAccount = ParkingSpotRepository.save(spotForSubWithAccount);

        ParkingSpotType typeForSubWithAccount = new ParkingSpotType();
        typeForSubWithAccount.setName("regular");
        typeForSubWithAccount.setFee(20);
        spotForSubWithAccount.setType(typeForSubWithAccount);
        // typeForSubWithAccount = ParkingSpotTypeRepository.save(typeForSubWithAccount);
        
        MonthlyCustomer customer1 = new MonthlyCustomer();
        customer1.setEmail("customer1@gmail.com");
        customer1.setName("Jennifer Black");
        customer1.setPassword("123456");
        customer1.setPhone("514-320-1349");
        customer1.setLicenseNumber("CA1562");
        // customer1 = MonthlyCustomerRepository.save(customer1);

        ParkingSpot spotForSubWithoutAccount = new ParkingSpot();
        spotForSubWithoutAccount.setId(4);
        // spotForSubWithoutAccount = ParkingSpotRepository.save(spotForSubWithoutAccount);

        ParkingSpotType typeForSubWithoutAccount = new ParkingSpotType();
        typeForSubWithoutAccount.setName("regular");
        typeForSubWithoutAccount.setFee(20);
        spotForSubWithoutAccount.setType(typeForSubWithoutAccount);
        // typeForSubWithoutAccount = ParkingSpotTypeRepository.save(typeForSubWithoutAccount);

        // Create new reservations
        SingleReservation singleReservation = new SingleReservation();
        SubWithAccount subWithAccount = new SubWithAccount();
        SubWithoutAccount subWithoutAccount = new SubWithoutAccount();
        singleReservation.setId(01);
        singleReservation.setDate(Date.valueOf("2023-02-28"));
        singleReservation.setLicenseNumber("CA021B");
        singleReservation.setParkingSpot(spotForSingle);
        singleReservation.setParkingTime(30);

        subWithAccount.setId(05);
        subWithAccount.setDate(Date.valueOf("2023-02-28"));
        subWithAccount.setNbrMonths(3);
        subWithAccount.setParkingSpot(spotForSubWithAccount);
        subWithAccount.setCustomer(customer1);

        subWithoutAccount.setId(10);
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
        int idSubWithOut = subWithAccount.getId();

        // Read the reservations from the ReservationRepository
        singleReservation = reservationRepository.findSingleReservationById(idSingle);
        subWithAccount = reservationRepository.findSubWithAccountById(idSubWith);
        subWithoutAccount = reservationRepository.findSubWithoutAccountById(idSubWithOut);

        // assert all reservations have the correct attributes
        assertNotNull(singleReservation);
        assertNotNull(subWithoutAccount);
        assertNotNull(subWithAccount);
        assertEquals(01, singleReservation.getId());
        assertEquals(05, subWithAccount.getId());
        assertEquals(10,subWithoutAccount.getId());
        assertEquals(Date.valueOf("2023-02-28"), singleReservation.getDate());
        assertEquals(Date.valueOf("2023-02-28"), subWithAccount.getDate());
        assertEquals(Date.valueOf("2023-02-28"), subWithoutAccount.getDate());
        assertEquals(spotForSingle, singleReservation.getParkingSpot());
        assertEquals(spotForSubWithAccount, subWithAccount.getParkingSpot());
        assertEquals(spotForSubWithoutAccount, subWithoutAccount.getParkingSpot());
        assertEquals("CA012B", singleReservation.getLicenseNumber());
        assertEquals("CA123E", subWithoutAccount.getLicenseNumber());
        assertEquals(3,subWithAccount.getNbrMonths());
        assertEquals(5, subWithoutAccount.getNbrMonths());
        assertEquals(customer1, subWithAccount.getCustomer());


    }
}

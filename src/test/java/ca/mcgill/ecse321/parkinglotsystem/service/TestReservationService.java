package ca.mcgill.ecse321.parkinglotsystem.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.parkinglotsystem.dao.ReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;


@ExtendWith(MockitoExtension.class)
public class TestReservationService {
    
@Mock
private ReservationRepository reservationRepository;
@Mock
private ParkingSpotService parkingSpotService;

@InjectMocks
private ReservationService reservationService;


private static final int RESERVATION_ID = 100;
private static final Date date1 = Date.valueOf("2023-03-22");
private static final int RESERVATION_ID2 = 999;
private static final Date date2 = Date.valueOf("2023-03-23");
private static final int Available_Id = 217;

private static final int ParkingSpot_ID = 1;
private static final int ParkingSpot_ID2 = 2;

//private static final String TYPE_NAME = "regular";
//private static final String TYPE_NAME2 = "large";


@BeforeEach
public void setMockOutput() {
    lenient().when(reservationRepository.findReservationById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(RESERVATION_ID)) {
            Reservation reservation = (Reservation) new SingleReservation();
            reservation.setId(RESERVATION_ID);
            reservation.setDate(date1);
            reservation.setParkingSpot(parkingSpotService.getParkingSpotById(ParkingSpot_ID));
            return reservation;
        } 
        else if (invocation.getArgument(0).equals(RESERVATION_ID2)) {
            Reservation reservation = (Reservation) new SingleReservation();
            reservation.setId(RESERVATION_ID2);
            reservation.setDate(date2);
            reservation.setParkingSpot(parkingSpotService.getParkingSpotById(ParkingSpot_ID2));
            return reservation;
        }
        else {
            return null;
        }
    });

    lenient().when(reservationRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
        Reservation reservation1 =  (Reservation) new SingleReservation();
        reservation1.setId(RESERVATION_ID);
        reservation1.setDate(date1);
        reservation1.setParkingSpot(parkingSpotService.getParkingSpotById(ParkingSpot_ID));
        Reservation reservation2 = (Reservation) new SingleReservation();
        reservation2.setId(RESERVATION_ID2);
        reservation2.setDate(date2);
        reservation2.setParkingSpot(parkingSpotService.getParkingSpotById(ParkingSpot_ID2));
        List<Reservation> reservations = new ArrayList<Reservation>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        return reservations;
    });

    lenient().when(reservationRepository.findReservationsByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
        if (invocation.getArgument(0).equals(date1)) {
            Reservation reservation = (Reservation) new SingleReservation();
            reservation.setId(RESERVATION_ID);
            reservation.setDate(date1);
            reservation.setParkingSpot(parkingSpotService.getParkingSpotById(ParkingSpot_ID));
            List<Reservation> reservations = new ArrayList<Reservation>();
            reservations.add(reservation);
            return reservations;
        } 
        else if (invocation.getArgument(0).equals(date2)) {
            Reservation reservation = (Reservation) new SingleReservation();
            reservation.setId(RESERVATION_ID2);
            reservation.setDate(date2);
            reservation.setParkingSpot(parkingSpotService.getParkingSpotById(ParkingSpot_ID2));
            List<Reservation> reservations = new ArrayList<Reservation>();
            reservations.add(reservation);
            return reservations;
        }
        else {
            return null;
        }
    });

    lenient().when(reservationRepository.findReservationsByParkingSpot(any(ParkingSpot.class))).
        thenAnswer((InvocationOnMock invocation) -> {
            List<Reservation> reservations = new ArrayList<>();
            ParkingSpot spot = invocation.getArgument(0);
            if(spot.getId() == ParkingSpot_ID) {
                Reservation reservation = (Reservation) new SingleReservation();
                reservation.setId(RESERVATION_ID);
                reservation.setDate(date1);
                reservation.setParkingSpot(spot);
                reservations.add(reservation);
            }
            else if(spot.getId() == ParkingSpot_ID2) {
                Reservation reservation = (Reservation) new SingleReservation();
                reservation.setId(RESERVATION_ID2);
                reservation.setDate(date2);
                reservation.setParkingSpot(spot);
                reservations.add(reservation);
            }
            return reservations;
    });

    lenient().when(reservationRepository.save(any(Reservation.class))).thenAnswer((InvocationOnMock invocation) -> {
        Reservation reservation = invocation.getArgument(0);
        reservation.setId(RESERVATION_ID);
        return reservation;
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

    
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(reservationRepository.save(any(Reservation.class))).thenAnswer(returnParameterAsAnswer);
}

@Test
public void testCreateReservationSuccessfully() {
    assertEquals(2 , reservationService.getAllReservations().size());

    Reservation reservation = null;
    try {
        reservation = reservationService.createReservation(date1, ParkingSpot_ID);
    } catch (IllegalArgumentException e) {
        fail(e.getMessage());
    }
    assertNotNull(reservation);
    assertEquals(date1, reservation.getDate());
    assertEquals(ParkingSpot_ID, reservation.getParkingSpot().getId());

}

@Test
public void testCreateReservationWithNegativeId() {
    int id = -1;
    String error = null;
    Reservation reservation = null;
    try {
        reservation = reservationService.createReservation(date1, ParkingSpot_ID);
    } catch (IllegalArgumentException e) {
        error = e.getMessage();
    }
    assertNull(reservation);
    assertEquals("ReservationId cannot be negative.", error);

}

@Test
public void testCreateReservationWithExistingId() {
    String error = null;
    Reservation reservation = null;
    try {
        reservation = reservationService.createReservation(date1, ParkingSpot_ID);
    } catch (IllegalArgumentException e) {
        error = e.getMessage();
    }
    assertNull(reservation);
    assertEquals("ReservationId is in use.", error);

}

@Test
public void testCreateReservationWithEmptyDate() {
    Date date = null;
    String error = null;
    Reservation reservation = null;
    try {
        reservation = reservationService.createReservation(date, ParkingSpot_ID);
    } catch (IllegalArgumentException e) {
        error = e.getMessage();
    }
    assertNull(reservation);
    assertEquals("date cannot be empty.", error);

}

@Test
public void testDeleteReservationSuccessfully() {
    assertEquals(2, reservationService.getAllReservations().size());

    Reservation reservation = null; 
    try {
        reservation = reservationService.deleteReservation(RESERVATION_ID);
    } catch (IllegalArgumentException e) {
        fail();
    }
    Reservation savedReservation = reservationService.getReservationById(RESERVATION_ID);
    assertNotNull(reservation);
    assertEquals(savedReservation.getId(), reservation.getId());
    assertEquals(savedReservation.getDate(), reservation.getDate());
    
}
@Test
public void testDeleteReservationWithNoExistingId() {
    assertEquals(2, reservationService.getAllReservations().size());

    int id = 462;
    String error = null;
    Reservation reservation = null; 
    try {
        reservation = reservationService.deleteReservation(id);
    } catch (IllegalArgumentException e) {
        error = e.getMessage();
    }
    assertNull(reservation);
    assertEquals("ReservationId does not exist.", error);
    
}

@Test
public void testDeleteReservationWithInvalidId() {
    assertEquals(2, reservationService.getAllReservations().size());

    int id = -12;
    String error = null;
    Reservation reservation = null; 
    try {
        reservation = reservationService.deleteReservation(id);
    } catch (IllegalArgumentException e) {
        error = e.getMessage();
    }
    assertNull(reservation);
    assertEquals("ReservationId cannot be negative.", error);
    
}

@Test
public void testGetReservationById() {
    Reservation reservation = reservationService.getReservationById(RESERVATION_ID);
    assertNotNull(reservation);
    assertEquals(RESERVATION_ID, reservation.getId());
}

@Test
public void testGetReservationsByDate() {
    List<Reservation> reservations = reservationService.getReservationsByDate(date1);
    assertNotNull(reservations);
    assertEquals(reservations.get(0).getId(), RESERVATION_ID);
}

@Test
public void testGetReservationsByParkingSpot() {

    List<Reservation> reservations = reservationService.getReservationsByParkingSpot(ParkingSpot_ID);
    assertNotNull(reservations);
    assertEquals(reservations.get(0).getId(), RESERVATION_ID);
}

@Test
public void testGetAllReservations() {
    List<Reservation> reservations = reservationService.getAllReservations();
    assertNotNull(reservations);
    assertEquals(2, reservations.size());
    assertEquals(reservations.get(0).getId(), RESERVATION_ID);
    assertEquals(reservations.get(1).getId(), RESERVATION_ID2);
}

@Test
public void testDeleteAllReservations() {
    List<Reservation> reservations = reservationService.deleteAllReservations();
    assertNotNull(reservations);
    assertEquals(2, reservations.size());
}
}

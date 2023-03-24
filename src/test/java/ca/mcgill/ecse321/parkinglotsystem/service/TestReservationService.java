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
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ReservationRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.service.ParkingSpotService;
import ca.mcgill.ecse321.parkinglotsystem.service.ReservationService;

@ExtendWith(MockitoExtension.class)
public class TestReservationService {
    
@Mock
private ReservationRepository reservationRepository;
@Mock
private ParkingSpotRepository parkingSpotRepository;
@Mock
private ParkingSpotTypeRepository parkingSpotTypeRepository;


@InjectMocks
private ReservationService reservationService;
@InjectMocks
private ParkingSpotService parkingSpotService;

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
            return reservation;
        } 
        else if (invocation.getArgument(0).equals(RESERVATION_ID2)) {
            Reservation reservation = (Reservation) new SingleReservation();
            reservation.setId(RESERVATION_ID2);
            reservation.setDate(date2);
            return reservation;
        }
        else {
            return null;
        }
    });

    lenient().when(reservationRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
        Reservation reservation1 = (Reservation) new SingleReservation();
        reservation1.setId(RESERVATION_ID);
        reservation1.setDate(date1);
        Reservation reservation2 = (Reservation) new SingleReservation();
        reservation2.setId(RESERVATION_ID2);
        reservation2.setDate(date2);
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
            List<Reservation> reservations = new ArrayList<Reservation>();
            reservations.add(reservation);
            return reservations;
        } 
        else if (invocation.getArgument(0).equals(date2)) {
            Reservation reservation = (Reservation) new SingleReservation();
            reservation.setId(RESERVATION_ID2);
            reservation.setDate(date2);
            
            List<Reservation> reservations = new ArrayList<Reservation>();
            reservations.add(reservation);
            return reservations;
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

    lenient().when(reservationRepository.findReservationsByParkingSpot(any(ParkingSpot.class))).thenAnswer( (InvocationOnMock invocation) -> { 
        

        if (invocation.getArgument(0).equals(parkingSpotService.getParkingSpotById(ParkingSpot_ID))) {
            Reservation reservation1 = (Reservation) new SingleReservation();
            reservation1.setId(RESERVATION_ID);
            reservation1.setDate(date1);

            List<Reservation> reservations = new ArrayList<Reservation>();
            reservations.add(reservation1);
            return reservations;
        }

        else if (invocation.getArgument(0).equals(parkingSpotService.getParkingSpotById(ParkingSpot_ID2))){
            Reservation reservation2 = (Reservation) new SingleReservation();
            reservation2.setId(RESERVATION_ID2);
            reservation2.setDate(date2);
            
            List<Reservation> reservations = new ArrayList<Reservation>();
            reservations.add(reservation2);
            return reservations;

        }
        else {
            return null;
        }

        

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
        reservation = reservationService.createReservation(Available_Id, date1, ParkingSpot_ID);
    } catch (IllegalArgumentException e) {
        fail(e.getMessage());
    }
    assertNotNull(reservation);
    assertEquals(Available_Id, reservation.getId());
    assertEquals(date1, reservation.getDate());
    assertEquals(ParkingSpot_ID, reservation.getParkingSpot().getId());

}

@Test
public void testCreateReservationWithNegativeId() {
    int id = -1;
    String error = null;
    Reservation reservation = null;
    try {
        reservation = reservationService.createReservation(id, date1, ParkingSpot_ID);
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
        reservation = reservationService.createReservation(RESERVATION_ID, date1, ParkingSpot_ID);
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
        reservation = reservationService.createReservation(Available_Id, date, ParkingSpot_ID);
    } catch (IllegalArgumentException e) {
        error = e.getMessage();
    }
    assertNull(reservation);
    assertEquals("date cannot be empty.", error);

}

/** 
@Test
public void testUpdateReservationSuccessfully() {
    assertEquals(2 , reservationService.getAllReservations().size());

    
    Date newDate = Date.valueOf("2023-04-01");
    int newParkingSpotId = 15;
    Reservation reservation = null;
    try {
        reservation = reservationService.createReservation(RESERVATION_ID, newDate, newParkingSpotId);
    } catch (IllegalArgumentException e) {
        fail();
    }
    assertNotNull(reservation);
    assertEquals(RESERVATION_ID, reservation.getId());
    assertEquals(newDate, reservation.getDate());
    assertEquals(newParkingSpotId, reservation.getParkingSpot().getId());

}
*/
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
    assertEquals(reservations.get(1).getId(), RESERVATION_ID2);
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

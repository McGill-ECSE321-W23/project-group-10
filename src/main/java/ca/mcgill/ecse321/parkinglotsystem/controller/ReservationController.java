package ca.mcgill.ecse321.parkinglotsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.parkinglotsystem.dto.*;
import ca.mcgill.ecse321.parkinglotsystem.model.*;
import ca.mcgill.ecse321.parkinglotsystem.service.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ca.mcgill.ecse321.parkinglotsystem.service.ReservationService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/reservation")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private AuthenticationService authService;
   
/**
 * find all Reservations 
 * @param token - the token to authenticate the user that have access to the reservations
 * @return List of All Reservations as Dtos
 */
@GetMapping(value = { "", "/" })
public List<ReservationDto> getAllReservations(@RequestHeader String token) {
    authService.authenticateEmployee(token);
	return reservationService.getAllReservations().stream().map(r -> convertToDto(r)).collect(Collectors.toList());
}

/**
 * find the reservation associated with an Id
 * @param id - the id of the reservation to be found
 * @return a reservation associated with the id as Dtos
 */
@GetMapping(value = { "/{id}", "/{id}/"})
public ReservationDto getReservationById(@PathVariable int id){
    Reservation reservation = reservationService.getReservationById(id);
    return convertToDto(reservation);
}

/**
 * find a list of reservations with a date
 * @param date - the date of the reservation to be found
 * @return a list of reservations associated with the date as Dtos
 */
@GetMapping(value = { "/all-by-date/{date}", "/all-by-date/{date}/"})
public List<ReservationDto> getReservationsByDate(@PathVariable Date date){
    List<ReservationDto> reservationDtos = new ArrayList<ReservationDto>();
    List<Reservation> reservations = reservationService.getReservationsByDate(date);
    for (Reservation r : reservations){
        reservationDtos.add(convertToDto(r));
    }
    return reservationDtos;
}

/**
 * find the reservation associated with a parkingSpot
 * @param parkingSpotId - the id of the parkingSpot
 * @return a reservation associated with the parkingSpot as Dtos
 */
@GetMapping(value = { "/all-by-parking-spot/{id}", "/all-by-parking-spot/{id}/"})
public List<ReservationDto> getReservationsByParkingSpot(@PathVariable("id") int parkingSpotId){
    List<ReservationDto> reservationDtos = new ArrayList<ReservationDto>();
    List<Reservation> reservations = reservationService.getReservationsByParkingSpot(parkingSpotId);
    for (Reservation r : reservations){
        reservationDtos.add(convertToDto(r));
    }
    return reservationDtos;
}

/**
 * create a instance of the reservation
 * @param date - the date of the reservation
 * @param parkingSpotId the id of the parkingSpot
 * @return the created reservation as Dtos
 */
@PostMapping(value = { "", "/"})
public ReservationDto createReservation(@RequestParam(name ="date") Date date, @RequestParam(name = "parking-spot-id") int parkingSpotId) {
    Reservation reservation = reservationService.createReservation(date, parkingSpotId);
    return convertToDto(reservation);
}

/**
 * convert a parkingSpot object into a Dto
 * @param spot - a parkingSpot object
 * @return a ParkingSpot Dto
 */
private ParkingSpotDto convertToDto(ParkingSpot spot){
    return new ParkingSpotDto(spot.getId(), convertToDto(spot.getType()));
}
/**
 * convert a parkingSpotType object into a Dto
 * @param type - a parkingSpotType object
 * @return a ParkingSpotType Dto
 */
private ParkingSpotTypeDto convertToDto(ParkingSpotType type){
    return new ParkingSpotTypeDto(type.getName(), type.getFee());
}

/**
 * convert a reservation object into a Dto
 * @param reservation - a reservation object
 * @return a reservation Dto
 */
private ReservationDto convertToDto(Reservation reservation) {
	ParkingSpotDto pDto = convertToDto(reservation.getParkingSpot());
	
	return new ReservationDto(reservation.getId(), reservation.getDate(), pDto);
}
}

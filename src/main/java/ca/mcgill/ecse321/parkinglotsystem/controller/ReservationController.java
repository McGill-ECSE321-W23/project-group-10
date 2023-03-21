package ca.mcgill.ecse321.parkinglotsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping(value = "/api/reseravtion/")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ParkingSpotService parkingSpotService;
   

@GetMapping(value = { "/all", "/all/" })
public List<ReservationDto> getAllReservations() {
	return reservationService.getAllReservations().stream().map(r -> convertToDto(r)).collect(Collectors.toList());
}

@GetMapping(value = { "/by-id/{id}", "/by-id/{id}/"})
public ReservationDto getReservationById(int id){
    Reservation reservation = reservationService.getReservationById(id);
    return convertToDto(reservation);
}

@GetMapping(value = { "/reservations/{date}", "/reservations/{date}/"})
public List<ReservationDto> getReservationsByDate(Date date){
    List<ReservationDto> reservationDtos = new ArrayList<ReservationDto>();
    List<Reservation> reservations = reservationService.getReservationsByDate(date);
    for (Reservation r : reservations){
        reservationDtos.add(convertToDto(r));
    }
    return reservationDtos;
}

@GetMapping(value = { "/reservations/{date}", "/reservations/{date}/"})
public List<ReservationDto> getReservationsByParkingSpot(int parkingSpotId){
    List<ReservationDto> reservationDtos = new ArrayList<ReservationDto>();
    ParkingSpot spot = parkingSpotService.getParkingSpotById(parkingSpotId);
    List<Reservation> reservations = reservationService.getReservationsByParkingSpot(spot);
    for (Reservation r : reservations){
        reservationDtos.add(convertToDto(r));
    }
    return reservationDtos;
}

@GetMapping(value = { "/reservations/{id}/{date}/{parkingSpot}", "/reservations/{id}/{date}/{parkingSpot}/"})
public ReservationDto createReservation(@PathVariable("id") int id, @RequestParam(name ="date") Date date, @RequestParam(name = "parking-spot-id") int parkingSpotId) {
    Reservation reservation = reservationService.createReservation(id, date, parkingSpotId);
    return convertToDto(reservation);
}

private ParkingSpotDto convertToDto(ParkingSpot spot){
    return new ParkingSpotDto(spot.getId(), convertToDto(spot.getType()));
}

private ParkingSpotTypeDto convertToDto(ParkingSpotType type){
    return new ParkingSpotTypeDto(type.getName(), type.getFee());
}
private ReservationDto convertToDto(Reservation reservation) {
	ParkingSpotDto pDto = convertToDto(reservation.getParkingSpot());
	
	return new ReservationDto(reservation.getId(), reservation.getDate(), pDto);
}
}

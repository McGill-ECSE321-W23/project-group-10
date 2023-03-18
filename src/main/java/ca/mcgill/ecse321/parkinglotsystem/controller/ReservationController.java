package ca.mcgill.ecse321.parkinglotsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.parkinglotsystem.dto.*;
import ca.mcgill.ecse321.parkinglotsystem.model.*;
import ca.mcgill.ecse321.parkinglotsystem.service.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import ca.mcgill.ecse321.parkinglotsystem.service.ReservationService;

@CrossOrigin(origins = "*")
@RestController
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ParkingSpotService parkingSpotService;

@GetMapping(value = { "/reservations", "/reservations/" })
public List<ReservationDto> getAllReservations() {
	return reservationService.getAllReservations().stream().map(r -> convertToDto(r)).collect(Collectors.toList());
}

@PostMapping(value = { "/parkingSpot/{id}/{type}", "/parkingSpot/{id}/{type}/" } )
public ParkingSpotDto createParkingSpot(@PathVariable("id") int id, @PathVariable("type") ParkingSpotType type){
    ParkingSpot spot = parkingSpotService.createParkingSpot(id, type);
    return convertToDto(spot);
}

@GetMapping(value = { "/reservations/{id}/{date}/{parkingSpot}", "/reservations/{id}/{date}/{parkingSpot}/"})
public ReservationDto createReservation(@PathVariable("id") int id, @PathVariable("date") Date date, @PathVariable("parkingSpot") ParkingSpot parkingSpot) {
    Reservation reservation = reservationService.createReservation(id, date, parkingSpot);
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

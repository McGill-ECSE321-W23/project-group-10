package ca.mcgill.ecse321.parkinglotsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.service.SingleReservationService;
import ca.mcgill.ecse321.parkinglotsystem.service.AuthenticationService;
import ca.mcgill.ecse321.parkinglotsystem.service.ParkingSpotService;
import ca.mcgill.ecse321.parkinglotsystem.dto.*;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SingleReservation;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/single-reservation/")
public class SingleReservationController {
    @Autowired
    private SingleReservationService singleReservationService;
    @Autowired
    private AuthenticationService authService;

    /**
    * get a list of all the existing singleReservations
    * @author Mike 
    * @return a list of all the existing singleReservations
    */
    @GetMapping(value = {"", "/"})
    public List<SingleReservationDto> getAllSingleReservations() {
        List<SingleReservationDto> singleReservationDtos = new ArrayList<SingleReservationDto>();
        List<SingleReservation> singleReservations = singleReservationService.getAllSingleReservations();
        for (SingleReservation sR : singleReservations){
            singleReservationDtos.add(convertToDto(sR));
        }
        return singleReservationDtos;

    }

    /**
    * create a new singleReservation object
    * @author Mike 
    * @param licenseNumber - the licenseNumber to be linked to the singleReservation
    * @param parkingTime - the length of the singleReservation in minutes
    * @return the singleReservation created as Dto
    */
    @PostMapping(value = {"", "/" })
    public SingleReservationDto createSingleReservation(@RequestParam(name = "licenseNumber") String licenseNumber, @RequestParam(name = "parkingTime") int parkingTime, @RequestParam(name = "parkingSpotId") int parkingSpotId){
        SingleReservation singleReservation = singleReservationService.createSingleReservation(licenseNumber, parkingTime, parkingSpotId);
        return convertToDto(singleReservation);
    }

    /**
    * get a list of singleReservation with the given parkingSpotId
    * @author Mike 
    * @param parkingSpotId - id of a parkingSpot 
    * @return a list of singleReservation associated with the parkingSpot as Dtos
    */
    @GetMapping(value = { "/all-by-parking-spot", "/all-by-parking-spot/"})
    public List<SingleReservationDto> getSingleReservationsByParkingSpot(@PathVariable int parkingSpotId){
        List<SingleReservationDto> singleReservationDtos = new ArrayList<SingleReservationDto>();
        List<Reservation> reservations = singleReservationService.getReservationsByParkingSpot(parkingSpotId);
        for (Reservation r : reservations){
           singleReservationDtos.add((SingleReservationDto) convertToDto(r));
        }
        return singleReservationDtos;
    }

    /**
    * get a list of singleReservation with the given date
    * @author Mike 
    * @param date - date object
    * @return a list of singleReservation associated with the date as Dtos
    */
    @GetMapping(value = { "/all-by-date/{date}", "/all-by-date/{date}/"})
    public List<SingleReservationDto> getSingleReservationsByDate(@PathVariable Date date){
        List<SingleReservationDto> singleReservationDtos = new ArrayList<SingleReservationDto>();
        List<Reservation> reservations = singleReservationService.getReservationsByDate(date);
        for (Reservation r : reservations){
           singleReservationDtos.add((SingleReservationDto) convertToDto(r));
        }
        return singleReservationDtos;
    }

    /**
    * get the singlereservation by its id
    * @author Mike 
    * @param id - id of a singleReservation 
    * @return the singleReservation found with the id as Dto
    */
    @GetMapping(value = { "/{id}", "/{id}/"})
    public SingleReservationDto getSingleReservationById(@PathVariable int id){
        
        SingleReservation singleReservation = singleReservationService.getSingleReservationById(id);
        
        return convertToDto(singleReservation);
    }

    /**
    * get a list of singleReservation with the given licenseNumber
    * @author Mike 
    * @param licenseNumber - licenseNumber of a singleReservation
    * @return a list of singleReservation associated with the licenseNumber as Dtos
    */
    @GetMapping(value = { "/all-by-license-number/{licenseNumber}", "/all-by-license-number/{licenseNumber}/"})
    public List<SingleReservationDto> getSingleReservationsByLicenseNumber(@PathVariable String licenseNumber){
        List<SingleReservationDto> singleReservationDtos = new ArrayList<SingleReservationDto>();
        List<SingleReservation> reservations = singleReservationService.getSingleReservationsByLicenseNumber(licenseNumber);
        for (Reservation r : reservations){
           singleReservationDtos.add((SingleReservationDto) convertToDto(r));
        }
        return singleReservationDtos;
    }

    /**
    * update a singleReservation
    * @author Mike 
    * @param LicenseNumber - licenseNumber of a singleReservation
    * @param newParkingTime - length of the singleReservation that one wishes to extend
    * @return the updated singleReservation with the licenseNumber
    */
    @PutMapping(value = { "/{id}", "/{id}/" })
	public SingleReservationDto updateSingleReservationDto( 
        @RequestParam String LicenseNumber, 
        @RequestParam int newParkingTime) {
		SingleReservation singleReservation = singleReservationService.updateSingleReservation(LicenseNumber, newParkingTime);
		return convertToDto(singleReservation);
	}

    /**
    * delete a singlereservation object
    * @author Mike 
    * @param id - id of a singleReservation to be deleted
    * @param token - a token to authenticate the user who should be a manager
    * @return the deleted singleReservation as Dto
    */
    @DeleteMapping(value = {"/{id}", "/{id}/"})
    public SingleReservationDto deleteSingleReservationDto(@PathVariable("id") int id, @RequestHeader String token){
        authService.authenticateManager(token);
        SingleReservation sR = singleReservationService.deleteSingleReservation(id);
        return convertToDto(sR);
    }

    /**
    * calculate the fee for a singlereservation
    * @author Mike 
    * @param startTime - licenseNumber of a singleReservation
    * @return a list of singleReservation associated with the licenseNumber as Dtos
    */
    @GetMapping(value = {"/calculate-fee", "/calculate-fee/"})
    public Double calculateFee(@RequestParam(name = "startTime") Time startTime, @RequestParam(name = "singleReservationId") int singleReservationId){
        return singleReservationService.calculateFee(startTime, singleReservationId);
    }

    private ReservationDto convertToDto(Reservation reservation) {
	    ParkingSpotDto pDto = convertToDto(reservation.getParkingSpot());
        return new ReservationDto(reservation.getId(), reservation.getDate(), pDto);
    }

    private SingleReservationDto convertToDto(SingleReservation singleReservation) {
        ParkingSpotDto pDto = convertToDto(singleReservation.getParkingSpot());
        
        return new SingleReservationDto(singleReservation.getId(), singleReservation.getDate(), singleReservation.getLicenseNumber(), singleReservation.getParkingTime(), pDto);
    }

    private ParkingSpotDto convertToDto(ParkingSpot spot){
        return new ParkingSpotDto(spot.getId(), convertToDto(spot.getType()));
    }
    
    private ParkingSpotTypeDto convertToDto(ParkingSpotType type){
        return new ParkingSpotTypeDto(type.getName(), type.getFee());
    }
}

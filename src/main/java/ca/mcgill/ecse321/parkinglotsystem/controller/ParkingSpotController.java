package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.List;
import java.util.stream.Collectors;

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


import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dto.ParkingSpotDto;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.service.*;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.*;

/**
 * author Shaun Soobagrah
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/parking-spot")
public class ParkingSpotController {

    @Autowired
    ParkingSpotService parkingSpotService;

    @Autowired
    ParkingSpotTypeRepository parkingSpotTypeRepository;

    @Autowired
    ParkingSpotTypeService parkingSpotTypesService;

    @Autowired
    AuthenticationService authService;


    /**
     * Controller method to get all parking spots.
     * @return A List of ParkingSpotDto
     */
    @GetMapping(value = {"","/"})
    public List<ParkingSpotDto> getAllParkingSpotDtos(){
       return parkingSpotService.getAllParkingSpots().stream().map(
        pSpot -> HelperMethods.convertParkingSpotToDto(pSpot)).collect(Collectors.toList());
    }

    /**
     * Controller method to create a parking spot.
     * @param id the parking spot id
     * @param parkingSpotTypeName the parking spot type name
     * @return A ParkingSpotDto
     */
    @PostMapping(value = {"/{id}", "/{id}/"})
    public ParkingSpotDto createParkingSpotDto(
        @PathVariable int id,               
        @RequestParam String parkingSpotTypeName,
        @RequestHeader String token){
        authService.authenticateManager(token);
        ParkingSpot parkingSpot = parkingSpotService.createParkingSpot(id, parkingSpotTypeName);
        return HelperMethods.convertParkingSpotToDto(parkingSpot);                             
    }

    /**
     * Controller method to get a parking spot using its parking spot type.
     * @param typeName the name of parking spot type
     * @return A list of ParkingSpotDto
     */
    @GetMapping(value = {"/all-by-type/{typeName}", "/all-by-type/{typeName}/"})
    public List<ParkingSpotDto> getParkingSpotDtoByType(@PathVariable("typeName") String typeName) {
        return parkingSpotService.getParkingSpotByType(typeName).stream().map(
                HelperMethods::convertParkingSpotToDto).collect(Collectors.toList());
    }

    /**
     * Controller Method to get parking spot using the id.
     * @param id the id of parking spot
     * @return A ParkingSpotDto
     */
    @GetMapping(value = {"/{id}", "/{id}/"})
    public ParkingSpotDto getParkingSpotDtoById(@PathVariable("id") int id) {
        ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(id);
        return HelperMethods.convertParkingSpotToDto(parkingSpot);
    }

    /**
     * Controller method to delete parking spot using id
     * @param id id of parking spot
     * @return A ParkingSpotDto
     */
    @DeleteMapping(value = {"/{id}","/{id}/"})
    public ParkingSpotDto deleteParkingSpotDtoById(@PathVariable("id") int id, @RequestHeader String token) {
        authService.authenticateManager(token);
        ParkingSpot spot = parkingSpotService.deleteParkingSpotById(id);
        return HelperMethods.convertParkingSpotToDto(spot);
    }

    /**
     * Controller method to send request to update parking spot type.
     * @param id the id of parking spot
     * @param typeName the name of the parking spot type
     * @return A ParkingSpotDto
     */
    @PutMapping(value ={"/{id}", "/{id}/"})
    public ParkingSpotDto updateParkingSpotDto(
        @PathVariable("id") int id,
        @RequestParam("typeName") String typeName,
        @RequestHeader String token) {
        authService.authenticateManager(token);
        ParkingSpot parkingSpot = parkingSpotService.updateParkingSpot(id, typeName);
        return HelperMethods.convertParkingSpotToDto(parkingSpot);

    } 
    
}

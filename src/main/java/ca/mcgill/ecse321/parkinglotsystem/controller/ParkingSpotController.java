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


    /**
     * method to send request to get all parking spot Dtos
     * @return List<ParkingSpotDto> 
     */
    @GetMapping(value = {"","/"})
    public List<ParkingSpotDto> getAllParkingSpotDtos(){
       return parkingSpotService.getAllParkingSpots().stream().map(
        pSpot -> HelperMethods.convertParkingSpotToDto(pSpot)).collect(Collectors.toList());
    }

    /**
     * create a parking spot
     * @param id
     * @param fee
     * @return parking spot type dto
     */
    @PostMapping(value = {"/{id}", "/{id}/"})
    public ParkingSpotDto createParkingSpotDto(
        @PathVariable int id,               
        @RequestParam String parkingSpotTypeName){
        ParkingSpot parkingSpot = parkingSpotService.createParkingSpot(id, parkingSpotTypeName);
        return HelperMethods.convertParkingSpotToDto(parkingSpot);                             
    }

    /**
     * method to send request to 
     * @param typeName
     * @return
     */
    @GetMapping(value = {"/all-by-type/{typeName}", "/all-by-type/{typeName}/"})
    public List<ParkingSpotDto> getParkingSpotDtoByType(@PathVariable("typeName") String typeName) {
        return parkingSpotService.getParkingSpotByType(typeName).stream().map(
            spots -> HelperMethods.convertParkingSpotToDto(spots)).collect(Collectors.toList());
    }

    /**
     * method to send request to get parking spot by id
     * @param id
     * @return parking spot dto
     */
    @GetMapping(value = {"/{id}", "/{id}/"})
    public ParkingSpotDto getParkingSpotDtoById(@PathVariable("id") int id) {
        ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(id);
        return HelperMethods.convertParkingSpotToDto(parkingSpot);
    }

    /**
     * method to send request to delete parking spot using id
     * @param id
     * @return ParkingSpotDto spot
     */
    @DeleteMapping(value = {"/{id}","/{id}/"})
    public ParkingSpotDto deleteParkingSpotDtoById(@PathVariable("id") int id) {
        ParkingSpot spot = parkingSpotService.deleteParkingSpotBy(id);
        return HelperMethods.convertParkingSpotToDto(spot);
    }

    /**
     * method to send request to update parking spot type
     * @param id
     * @param typeName
     * @return parking spot type dto
     */
    @PutMapping(value ={"/{id}", "/{id}/"})
    public ParkingSpotDto updateParkingSpotDto(@PathVariable("id") int id, @RequestParam("typeName") String typeName) {
        ParkingSpot parkingSpot = parkingSpotService.updateParkingSpot(id, typeName);
        return HelperMethods.convertParkingSpotToDto(parkingSpot);

    } 
    
}

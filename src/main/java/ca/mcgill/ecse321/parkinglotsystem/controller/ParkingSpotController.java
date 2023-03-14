package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;


import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotTypeRepository;
import ca.mcgill.ecse321.parkinglotsystem.dto.ParkingSpotDto;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.service.*;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.*;

/**
 * author Shaun Soobagrah
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/api/parking_spot", "/api/parking_spot/"})
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
    @GetMapping(value = {"/all/","/all"})
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
    @PostMapping(value = {"/create/{id}/{parkingSpotTypeName}", "/create/{id}/{parkingSpotTypeName}/"})
    public ParkingSpotDto createParkingSpotDto(@PathVariable("id") int id, 
                                            @PathVariable("parkingSpotTypeName") String parkingSpotTypeName){

        try {
            ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(parkingSpotTypeName);
            ParkingSpot parkingSpot = parkingSpotService.createParkingSpot(id, parkingSpotType);
            return HelperMethods.convertParkingSpotToDto(parkingSpot);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }                                  
        
    }

    /**
     * method to send request to 
     * @param parkingSpotType
     * @return
     */
    @GetMapping(value = {"/getByType/{typeName}", "/getByType/{typeName}/"})
    public List<ParkingSpotDto> getParkingSpotDtoByType(@PathVariable("typeName") String typeName) {
        List<ParkingSpotDto> spotDtos = new ArrayList<ParkingSpotDto>();
        try {
            List<ParkingSpot> spots = parkingSpotService.getParkingSpotByType(parkingSpotTypesService.getParkingSpotTypeByName(typeName));
            if (spots.size() != 0){
                for (ParkingSpot spot: spots){
                    spotDtos.add(HelperMethods.convertParkingSpotToDto(spot));
                }
            }     
            return spotDtos;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
        
    }

    /**
     * method to send request to get parking spot by id
     * @param id
     * @return parking spot dto
     */
    @GetMapping(value = {"/getById/{id}", "/getById/{id}/"})
    public ParkingSpotDto getParkingSpotDtoById(@PathVariable("id") int id) {
        try {
            ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(id);
            return HelperMethods.convertParkingSpotToDto(parkingSpot);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }

    /**
     * method to send request to delete parking spot using id
     * @param id
     * @return ParkingSpotDto spot
     */
    @DeleteMapping(value = {"/delete/{id}","/delete/{id}/"})
    public ParkingSpotDto deleteParkingSpotDtoById(@PathVariable("id") int id) {
        try {
            ParkingSpot spot = parkingSpotService.deleteParkingSpotBy(id);
            return HelperMethods.convertParkingSpotToDto(spot);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } 
    }

    @PutMapping(value ={"/update/{id}/{typeName}", "/update/{id}/{typeName}/"})
    public ParkingSpotDto updateParkingSpotDto(@PathVariable("id") int id, @PathVariable("typeName") String typeName) {
        try {
            ParkingSpot parkingSpot = parkingSpotService.updateParkingSpot
             (id, parkingSpotTypesService.getParkingSpotTypeByName(typeName));
            
            return HelperMethods.convertParkingSpotToDto(parkingSpot);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } 
    } 
    
}

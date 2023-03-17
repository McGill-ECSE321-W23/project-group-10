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
import ca.mcgill.ecse321.parkinglotsystem.dto.ParkingSpotTypeDto;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.service.ParkingSpotService;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/api/parking_spot", "/api/parking_spot/"})
public class ParkingSpotController {

    @Autowired
    ParkingSpotService parkingSpotService;

    @Autowired
    ParkingSpotTypeRepository parkingSpotTypeRepository;


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


    
}

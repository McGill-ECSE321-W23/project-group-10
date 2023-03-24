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
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

/**
 * author Shaun Soobagrah
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/api/parking-spot", "/api/parking-spot/"})
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
    @GetMapping(value = {"/", ""})
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
    @PostMapping(value = {"/{id}/{parkingSpotTypeName}", "/{id}/{parkingSpotTypeName}/"})
    public ParkingSpotDto createParkingSpotDto(@PathVariable("id") int id, 
                                            @PathVariable("parkingSpotTypeName") String parkingSpotTypeName){
        ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(parkingSpotTypeName);
        ParkingSpot parkingSpot = parkingSpotService.createParkingSpot(id, parkingSpotType);
        return HelperMethods.convertParkingSpotToDto(parkingSpot);                             
        
    }

    /**
     * method to send request to 
     * @param parkingSpotType
     * @return
     */
    @GetMapping(value = {"/by-type/{typeName}", "/by-type/{typeName}/"})
    public List<ParkingSpotDto> getParkingSpotDtoByType(@PathVariable("typeName") String typeName) {
        List<ParkingSpotDto> spotDtos = new ArrayList<ParkingSpotDto>();
        List<ParkingSpot> spots = parkingSpotService.getParkingSpotByType(parkingSpotTypesService.getParkingSpotTypeByName(typeName));
        if (spots.size() != 0){
            for (ParkingSpot spot: spots){
                spotDtos.add(HelperMethods.convertParkingSpotToDto(spot));
            }
        }     
        return spotDtos;

        
    }

    /**
     * method to send request to get parking spot by id
     * @param id
     * @return parking spot dto
     */
    @GetMapping(value = {"/by-id/{id}", "/by-id/{id}/"})
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
    @PutMapping(value ={"/{id}/{typeName}", "/{id}/{typeName}/"})
    public ParkingSpotDto updateParkingSpotDto(@PathVariable("id") int id, @PathVariable("typeName") String typeName) {

        ParkingSpot parkingSpot = parkingSpotService.updateParkingSpot
            (id, parkingSpotTypesService.getParkingSpotTypeByName(typeName));
        
        return HelperMethods.convertParkingSpotToDto(parkingSpot);

    } 
    
}

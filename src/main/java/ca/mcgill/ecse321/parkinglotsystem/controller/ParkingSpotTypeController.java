package ca.mcgill.ecse321.parkinglotsystem.controller;


import java.util.ArrayList;
import java.util.List;

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
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.parkinglotsystem.dto.ParkingSpotTypeDto;
import ca.mcgill.ecse321.parkinglotsystem.model.*;
import ca.mcgill.ecse321.parkinglotsystem.service.AuthenticationService;
import ca.mcgill.ecse321.parkinglotsystem.service.ParkingSpotTypeService;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

/**
 * author Shaun Soobagrah
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/parking-spot-type")
public class ParkingSpotTypeController {
    
    @Autowired
    ParkingSpotTypeService parkingSpotTypeService;

    @Autowired
    AuthenticationService authService;

    /**
     * Controller method to get all parking spot type.
     * @return A List of ParkingSpotTypeDto
     */
    @GetMapping(value = {"", "/"})
    public List<ParkingSpotTypeDto> getAllParkingSpotTypes() throws Exception{
        List<ParkingSpotTypeDto> pList = new ArrayList<ParkingSpotTypeDto>();
        for (ParkingSpotType parkingSpotType: parkingSpotTypeService.getAllParkingSpotTypes()) {
            pList.add(convertParkingSpotTypeToDto(parkingSpotType));
        }
        return pList;
    }

    /**
     * Controller method to get request to create a parking spot type.
     * @param name the name of the parking spot type
     * @param fee the fee of the parking spot type
     * @return A ParkingSpotTypeDto
     */
    @PostMapping(value = {"/{name}", "/{name}/"})
    public ParkingSpotTypeDto createParkingSpotType(
        @PathVariable String name, 
        @RequestParam double fee,
        @RequestHeader String token){
        authService.authenticateManager(token);    
        ParkingSpotType parkingSpotType = parkingSpotTypeService.createParkingSpotType(name, fee);
        return convertParkingSpotTypeToDto(parkingSpotType);
    }

    /**
     * Controller method to get a parking spot type by name.
     * @param name the name of the parking spot type
     * @return A ParkingSpotTypeDto
     */
    @GetMapping(value = {"/{name}", "/{name}/"})
    public ParkingSpotTypeDto getParkingSpotTypeByName(@PathVariable("name") String name){
        ParkingSpotType parkingSpotType = parkingSpotTypeService.getParkingSpotTypeByName(name);
        return convertParkingSpotTypeToDto(parkingSpotType);
    }

    /**
     * Controller method to delete a parking spot type.
     * @param name the name of the parking spot type
     * @return the deleted ParkingSpotTypeDto
     */
    @DeleteMapping(value = {"/{name}", "/{name}/"})
    public ParkingSpotTypeDto deleteParkingSpotTypeByName(@PathVariable String name, @RequestHeader String token) {
        authService.authenticateManager(token);
        ParkingSpotType parkingSpotType = parkingSpotTypeService.deleteParkingSpotType(name);
        return convertParkingSpotTypeToDto(parkingSpotType);   
    }

    /**
     * Controller method to Update a parking spot type fee
     * @param name the name of the parking spot type
     * @param fee the fee of the parking spot type
     * @return the updated ParkingSpotTypeDto
     * @throws IllegalArgumentException if to update parking spot type fee fail
     */
    @PutMapping(value = {"/{name}","/{name}/" })
    public ParkingSpotTypeDto updateParkingSpotTypeFee(
        @PathVariable String name, 
        @RequestParam double fee,
        @RequestHeader String token){
        authService.authenticateManager(token);
        
        ParkingSpotType parkingSpotType = parkingSpotTypeService.updateParkingSpotTypeFee(name, fee);
        return convertParkingSpotTypeToDto(parkingSpotType);
        
        }
    

    /**
     * Helper method to convert parking spot type to a DTO.
     * @param parkingSpotType parking spot type instance
     * @return A parkingSpotTypeDto
     */
    private ParkingSpotTypeDto convertParkingSpotTypeToDto(ParkingSpotType parkingSpotType) {

        if (parkingSpotType == null) {
            throw new CustomException("There is no such parking spot type! ", HttpStatus.BAD_REQUEST);
        }
        ParkingSpotTypeDto parkingSpotTypeDto = new ParkingSpotTypeDto();
        parkingSpotTypeDto.setName(parkingSpotType.getName());
        parkingSpotTypeDto.setFee(parkingSpotType.getFee());
        return parkingSpotTypeDto;
    }
}

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.parkinglotsystem.dto.ParkingSpotTypeDto;
import ca.mcgill.ecse321.parkinglotsystem.model.*;
import ca.mcgill.ecse321.parkinglotsystem.service.ParkingSpotTypeService;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/api/parking_spot_type", "/api/parking_spot_type/"})
public class ParkingSpotTypeController {
    
    @Autowired
    ParkingSpotTypeService parkingSpotTypeService;

    /**
     * method to get all parking spot type
     * @return List<ParkingSpotTypeDto>
     * @throws Exception
     */
    @GetMapping(value = {"", "/"})
    public List<ParkingSpotTypeDto> getAllParkingSpotTypes() throws Exception{
        List<ParkingSpotTypeDto> pList = new ArrayList<ParkingSpotTypeDto>();
        for (ParkingSpotType parkingSpotType: parkingSpotTypeService.getAllParkingSpotTypes()) {
            pList.add(convertParkingSpotTypeToDto(parkingSpotType));
        }
        if (pList.size() == 0) throw new CustomException("There are no parking spot types! ", HttpStatus.BAD_REQUEST);
        return pList;
    }

    /**
     * create a parking spot type
     * @param name
     * @param fee
     * @return parking spot type dto
     */
    @PostMapping(value = {"/{name}/{fee}", "/{name}/{fee}/"})
    public ParkingSpotTypeDto createParkingSpotType(@PathVariable("name") String name, @PathVariable("fee") double fee){       
   
        ParkingSpotType parkingSpotType = parkingSpotTypeService.createParkingSpotType(name, fee);
        return convertParkingSpotTypeToDto(parkingSpotType);

    }

    /**
     * method to get a parking spot type by name
     * @param name
     * @return ParkingSpotTypeDto
     */
    @GetMapping(value = {"/{name}/", "/{name}"})
    public ParkingSpotTypeDto getParkingSpotTypeByName(@PathVariable("name") String name){

        ParkingSpotType parkingSpotType = parkingSpotTypeService.getParkingSpotTypeByName(name);
        return convertParkingSpotTypeToDto(parkingSpotType);


    }

    /**
     * delete a parking spot type
     * @param name
     * @return parking spot type deleted
     */
    @DeleteMapping(value = {"/{name}", "/{name}/"})
    public ParkingSpotTypeDto deleteParkingSpotTypeByName(@PathVariable("name") String name) {
        
        ParkingSpotType parkingSpotType = parkingSpotTypeService.deleteParkingSpotType(name);
        return convertParkingSpotTypeToDto(parkingSpotType);   
    }

    /**
     * update a parking spot type fee
     * @param name
     * @param fee
     * @return parking spot type updated
     */
    @PutMapping(value = {"/{name}/{fee}/","/{name}/{fee}" })
    public ParkingSpotTypeDto updateParkingSpotTypeFee(@PathVariable("name") String name, @PathVariable("fee") double fee){
   
        ParkingSpotType parkingSpotType = parkingSpotTypeService.updateParkingSpotTypeFee(name, fee);
        return convertParkingSpotTypeToDto(parkingSpotType);
    }

    /**
     * Helper method to convert parking spot type to a DTO
     * @param parking spot type instance
     * @return Dto
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

// Author :Chenxin Xun
// 2023-03-19

package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.dto.ParkingLotSystemDto;
import ca.mcgill.ecse321.parkinglotsystem.service.AuthenticationService;
import ca.mcgill.ecse321.parkinglotsystem.service.ParkingLotSystemService;

import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.convertParkingLotSystemToDto;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/parking-lot-system")

public class ParkingLotSystemController {
    @Autowired
    private ParkingLotSystemService service;

    @Autowired
    private AuthenticationService authService;

    /**
     * Gets all ParkingLotSystem.
     * 
     * @return the list of ParkingLotSystem as DTOs
     */
    @GetMapping(value = { "", "/" })
    public List<ParkingLotSystemDto> getAll() {
        return service.getAll().stream().map(p -> convertParkingLotSystemToDto(p)).collect(Collectors.toList());
    }

    /**
     * Gets ParkingLotSystem with such ID.
     * 
     * @return a ParkingLotSystem as DTOs
     */
    @GetMapping(value = { "/{id}", "/{id}/" })
    public ParkingLotSystemDto getById(@PathVariable("id") int id) {
        return convertParkingLotSystemToDto(service.getById(id));
    }

    /**
     * Gets ParkingLotSystem with such openTime.
     * 
     * @return a ParkingLotSystem as DTOs
     */
    @GetMapping(value = { "/all-by-open-time/{openTime}", "/all-by-open-time/{openTime}/" })
    public List<ParkingLotSystemDto> getAllByOpenTime(@PathVariable("openTime") Time openTime) {
        return service.getAllByOpenTime(openTime).stream().map(s -> convertParkingLotSystemToDto(s))
                .collect(Collectors.toList());
    }

    /**
     * Gets ParkingLotSystem with such closeTime.
     * 
     * @return a ParkingLotSystem as DTOs
     */
    @GetMapping(value = { "/all-by-close-time/{closeTime}", "/all-by-close-time/{closeTime}/" })
    public List<ParkingLotSystemDto> getAllByCloseTime(@PathVariable("closeTime") Time closeTime) {
        return service.getAllByCloseTime(closeTime).stream().map(s -> convertParkingLotSystemToDto(s))
                .collect(Collectors.toList());
    }

    /**
     * Create ParkingLotSystem with such ID.
     * 
     * @return a ParkingLotSystem as DTOs
     */
    @PostMapping(value = { "{id}", "/{id}" })
    public ParkingLotSystemDto createParkingLotSystem(
            @PathVariable(value = "id") int id,
            @RequestParam(value = "openTime") Time openTime,
            @RequestParam(value = "closeTime") Time closeTime,
            @RequestHeader String token) {
        authService.authenticateManager(token);
        return convertParkingLotSystemToDto(service.createParkingLotSystem(id, openTime, closeTime));
    }

    /**
     * Updates ParkingLotSystem with such ID.
     * 
     * @return a ParkingLotSystem as DTOs
     */
    @PutMapping(value = { "/{id}", "/{id}/" })
    public ParkingLotSystemDto updateParkingLotSystem(
            @PathVariable(value = "id") int id,
            @RequestParam(value = "openTime") Time openTime,
            @RequestParam(value = "closeTime") Time closeTime,
            @RequestHeader String token) {
        authService.authenticateManager(token);
        return convertParkingLotSystemToDto(service.updateParkingLotSystem(id, openTime, closeTime));
    }

}

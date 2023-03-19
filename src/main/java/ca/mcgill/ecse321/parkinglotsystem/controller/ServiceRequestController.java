package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.dto.ParkingSpotTypeDto;
import ca.mcgill.ecse321.parkinglotsystem.model.*;
import ca.mcgill.ecse321.parkinglotsystem.service.ParkingSpotTypeService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/api/service_request", "/api/service_request/"})
public class ServiceRequestController {

    @Autowired
    ServiceRequestService serviceRequestService;

}

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


import ca.mcgill.ecse321.parkinglotsystem.dao.ManagerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dto.ManagerDto;
import ca.mcgill.ecse321.parkinglotsystem.model.Manager;
import ca.mcgill.ecse321.parkinglotsystem.service.*;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/api/manager", "/api/manager/"})

public class ManagerController {

    
    @Autowired
    Manager manager;    //This line of code fails building

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    ManagerService managerService;


    @GetMapping(value={"/all/","/all"})
    public List<ManagerDto> getAllManagerDtos(){
        return managerService.getAllManagers().stream().map(ma -> HelperMethods.convertManagerToDto(ma)).collect(Collectors.toList());
    }


    @PostMapping(value = {"/create/{email}/{name}/{phone}/{password}", "/create/{email}/{name}/{phone}/{password}/"})
    public ManagerDto createManagerDto(@PathVariable("email") String email, 
        @PathVariable("name") String name,
        @PathVariable("phone") String phone,
        @PathVariable("password") String password){
        try {
            //ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findParkingSpotTypeByName(parkingSpotTypeName);
            Manager manager = managerService.createManager(email,name,phone,password);
            return HelperMethods.convertManagerToDto(manager);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }                                   
    }


    @GetMapping(value = {"/getByName/{name}", "/getByName/{name}/"})
    public List<ManagerDto> getManagerDtoByName(@PathVariable("name") String name) {
        List<ManagerDto> managerDtos = new ArrayList<ManagerDto>();
        try {
            List<Manager> managers = managerService.getManagerByName(name);
            if (managers.size() != 0){
                for (Manager ma: managers){
                    managerDtos.add(HelperMethods.convertManagerToDto(ma));
                }
            }     
            return managerDtos;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @GetMapping(value = {"/getByPhone/{phone}", "/getByPhone/{phone}/"})
    public List<ManagerDto> getManagerDtoByPhone(@PathVariable("phone") String phone) {
        List<ManagerDto> managerDtos = new ArrayList<ManagerDto>();
        try {
            List<Manager> managers = managerService.getManagerByPhone(phone);
            if (managers.size() != 0){
                for (Manager ma: managers){
                    managerDtos.add(HelperMethods.convertManagerToDto(ma));
                }
            }     
            return managerDtos;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @GetMapping(value = {"/getByPassword/{password}", "/getByPassword/{password}/"})
    public List<ManagerDto> getManagerDtoByPassword(@PathVariable("name") String password) {
        List<ManagerDto> managerDtos = new ArrayList<ManagerDto>();
        try {
            List<Manager> managers = managerService.getManagerByPassword(password);
            if (managers.size() != 0){
                for (Manager ma: managers){
                    managerDtos.add(HelperMethods.convertManagerToDto(ma));
                }
            }     
            return managerDtos;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @GetMapping(value = {"/getByEmail/{email}", "/getByEmail/{email}/"})
    public ManagerDto getManagerDtoByEmail(@PathVariable("email") String email) {
        try {
            Manager ma = managerService.getManagerByEmail(email);
            return HelperMethods.convertManagerToDto(ma);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }   
    }


    @DeleteMapping(value = {"/delete/{email}","/delete/{email}/"})
    public ManagerDto deleteManagerDtoByEmail(@PathVariable("email") String email) {
        try {
            Manager ma = managerService.deleteManagerByEmail(email);
            return HelperMethods.convertManagerToDto(ma);
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } 
    }


    @PutMapping(value ={"/update/{email}/{name}/{phone}/{password}", "/update/{email}/{name}/{phone}/{password}/"})
    public ManagerDto updateManagerDto(@PathVariable("email") String email, 
        @PathVariable("name") String name,
        @PathVariable("phone") String phone,
        @PathVariable("password") String password) {
        try {
            Manager ma = managerService.updateManager(email, name, phone, password);
            return HelperMethods.convertManagerToDto(ma);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } 
    } 


}

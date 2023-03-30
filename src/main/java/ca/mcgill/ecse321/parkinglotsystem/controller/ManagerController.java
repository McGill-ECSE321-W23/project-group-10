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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import ca.mcgill.ecse321.parkinglotsystem.dao.ManagerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dto.ManagerDto;
import ca.mcgill.ecse321.parkinglotsystem.model.Manager;
import ca.mcgill.ecse321.parkinglotsystem.service.*;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/manager")

public class ManagerController {


    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    ManagerService managerService;

    /**
     * Controller method to get all managers
     * @return A List of ManagerDto or null
     */
    @GetMapping(value={"/",""})
    public List<ManagerDto> getAllManagerDtos(){
        return managerService.getAllManagers().stream().map(ma -> HelperMethods.convertManagerToDto(ma)).collect(Collectors.toList());
    }

    /**
     * Controller method to create a manager
     * @param email the email of the manager
     * @param name the name of the manager
     * @param phone the phone number of the manager
     * @param password the password of the manager
     * @return newly created ManagerDto or exception
     */
    @PostMapping(value = {"/{email}", "/{email}/"})
    public ManagerDto createManagerDto(@PathVariable("email") String email, 
        @RequestParam("name") String name,
        @RequestParam("phone") String phone,
        @RequestParam("password") String password){

        Manager manager = managerService.createManager(email,name,phone,password);
        return HelperMethods.convertManagerToDto(manager);                                  
    }

    /**
     * Controller method to get managers by name
     * @param name the name of the manager
     * @return A List of ManagerDto or null
     */
    @GetMapping(value = {"/all-by-name/{name}", "/all-by-name/{name}/"})
    public List<ManagerDto> getManagerDtoByName(@PathVariable("name") String name) {
        List<ManagerDto> managerDtos = new ArrayList<ManagerDto>();
        List<Manager> managers = managerService.getManagerByName(name);
        if (managers.size() != 0){
            for (Manager ma: managers){
                managerDtos.add(HelperMethods.convertManagerToDto(ma));
            }
        }     
        return managerDtos; 
    }

    /**
     * Controller method to get managers by phone
     * @param phone the phone number of the manager
     * @return A List of ManagerDto or null
     */
    @GetMapping(value = {"/all-by-phone/{phone}", "/all-by-phone/{phone}/"})
    public List<ManagerDto> getManagerDtoByPhone(@PathVariable("phone") String phone) {
        List<ManagerDto> managerDtos = new ArrayList<ManagerDto>();
        List<Manager> managers = managerService.getManagerByPhone(phone);
        if (managers.size() != 0){
            for (Manager ma: managers){
                managerDtos.add(HelperMethods.convertManagerToDto(ma));
            }
        }     
        return managerDtos;
    }

    /**
     * Controller method to get a manager by email
     * @param email the email of the manager
     * @return A ManagerDto or exception
     */
    @GetMapping(value = {"/{email}", "/{email}/"})
    public ManagerDto getManagerDtoByEmail(@PathVariable("email") String email) {
        Manager ma = managerService.getManagerByEmail(email);
        if(ma==null){
            return null;
        }
        return HelperMethods.convertManagerToDto(ma);
    }

    /**
     * Controller method to delete a manager
     * @param email the email of the manager
     * @return newly deleted ManagerDto or exception
     */
    @DeleteMapping(value = {"/{email}","/{email}/"})
    public ManagerDto deleteManagerDtoByEmail(@PathVariable("email") String email) {
        Manager ma = managerService.deleteManagerByEmail(email);
        return HelperMethods.convertManagerToDto(ma);
    }

     /**
     * Controller method to update a manager
     * @param email the email of the manager
     * @param name the name of the manager
     * @param phone the phone number of the manager
     * @param password the password of the manager
     * @return newly updated ManagerDto or exception
     */
    @PutMapping(value ={"/{email}", "/{email}/"})
    public ManagerDto updateManagerDto(@PathVariable("email") String email, 
        @RequestParam("name") String name,
        @RequestParam("phone") String phone,
        @RequestParam("password") String password) {
        Manager ma = managerService.updateManager(email, name, phone, password);
        return HelperMethods.convertManagerToDto(ma);
    } 

}
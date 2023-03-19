package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dto.ServicesDto;
import ca.mcgill.ecse321.parkinglotsystem.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.model.*;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.convertServicesToDto;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/api/services", "/api/services/"})
public class ServicesController {

    @Autowired
    ServicesService servicesService;

    /**
     * method to get all Services
     *
     * @return List<ServicesDto>
     * @throws Exception
     */
    @GetMapping(value = {"/all", "/all/"})
    public List<ServicesDto> getAllServices() throws Exception {
        List<ServicesDto> pList = new ArrayList<>();
        for (Services services : servicesService.getAllServices()) {
            pList.add(convertServicesToDto(services));
        }
        if (pList.size() == 0) throw new Exception("There are no services");
        return pList;
    }

    /**
     * create a service
     *
     * @param description
     * @param price
     * @return ServicesDto
     */
    @PostMapping(value = {"/create/{description}/{price}", "/create/{description}/{price}/"})
    public ServicesDto createServices(@PathVariable("description") String description, @PathVariable("price") int price) {
        try {
            Services services = servicesService.createService(description, price);
            return convertServicesToDto(services);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());

        }
    }

    /**
     * method to get a service by description
     *
     * @param description
     * @return ServicesDto
     */
    @GetMapping(value = {"/getByDescription/{description}/", "/getByDescription/{description}"})
    public ServicesDto getServicesByDescription(@PathVariable("description") String description) {
        try {
            Services services = servicesService.getServiceByDescription(description);
            return convertServicesToDto(services);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());

        }

    }

    /**
     * method to get services by price
     *
     * @return List<ServicesDto>
     * @throws Exception
     */
    @GetMapping(value = {"/getByPrice/{price}", "/getByPrice/{price}/"})
    public List<ServicesDto> getServicesByPrice(@PathVariable("price") int price) {
        List<ServicesDto> sList = new ArrayList<>();
        try {
            List<Services> services = servicesService.getServiceByPrice(price);
            if (services.size() != 0) {
                for (Services se : services) {
                    sList.add(convertServicesToDto(se));
                }
            }
            return sList;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * delete a service
     *
     * @param description
     * @return service deleted
     */
    @DeleteMapping(value = {"/delete/{description}", "/delete/{description}/"})
    public ServicesDto deleteServicesByDescription(@PathVariable("description") String description) {
        try {
            Services services = servicesService.deleteServiceByDescription(description);
            return convertServicesToDto(services);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());

        }
    }

    /**
     * update a service
     *
     * @param description
     * @param price
     * @return service updated
     */
    @PutMapping(value = {"/update/{description}/{price}/", "/update/{description}/{price}"})
    public ServicesDto updateServicesByDescription(@PathVariable("description") String description, @PathVariable("price") int price) {
        try {
            Services services = servicesService.updateService(description, price);
            return convertServicesToDto(services);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

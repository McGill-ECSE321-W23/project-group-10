package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dto.ServiceDto;
import ca.mcgill.ecse321.parkinglotsystem.service.AuthenticationService;
import ca.mcgill.ecse321.parkinglotsystem.service.ServicesService;
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

import ca.mcgill.ecse321.parkinglotsystem.model.*;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.convertServiceToDto;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/service")
public class ServicesController {

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private AuthenticationService authService;

    /**
     * Controller to get all services
     * @return A List of ServicesDto
     */
    @GetMapping(value = {"", "/"})
    public List<ServiceDto> getAllServices() throws Exception {
        List<ServiceDto> pList = new ArrayList<>();
        for (Service service : servicesService.getAllServices()) {
            pList.add(convertServiceToDto(service));
        }
        if (pList.size() == 0) throw new Exception("There are no services");
        return pList;
    }

    /**
     * Controller to create a service
     * @param description the description of the service
     * @param price the price of the service
     * @return A ServicesDto
     * @throws IllegalArgumentException if to create the service fail
     */
    @PostMapping(value = {"/{description}", "/{description}/"})
    public ServiceDto createServices(
        @PathVariable("description") String description, 
        @RequestParam int price,
        @RequestHeader String token) {
        authService.authenticateManager(token);
        try {
            Service services = servicesService.createService(description, price);
            return convertServiceToDto(services);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());

        }
    }

    /**
     * Controller to get a service by description
     * @param description the description of the service
     * @return A ServicesDto
     * @throws IllegalArgumentException if to get the service fail
     */
    @GetMapping(value = {"/{description}/", "/{description}"})
    public ServiceDto getServicesByDescription(@PathVariable("description") String description) {
        try {
            Service services = servicesService.getServiceByDescription(description);
            return convertServiceToDto(services);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());

        }

    }

    /**
     * Controller to get a list of services by price
     * @param price the price of service
     * @return A List of ServicesDto
     * @throws IllegalArgumentException if to get services fail
     */
    @GetMapping(value = {"/all-by-price/{price}", "/all-by-price/{price}/"})
    public List<ServiceDto> getServicesByPrice(@PathVariable("price") int price) {
        List<ServiceDto> sList = new ArrayList<>();
        try {
            List<Service> services = servicesService.getServiceByPrice(price);
            if (services.size() != 0) {
                for (Service se : services) {
                    sList.add(convertServiceToDto(se));
                }
            }
            return sList;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Controller for delete a service
     * @param description the description of the service
     * @return service deleted
     * @throws IllegalArgumentException if to delete service fail
     */
    @DeleteMapping(value = {"/{description}", "/{description}/"})
    public ServiceDto deleteServicesByDescription(@PathVariable("description") String description) {
        try {
            Service services = servicesService.deleteServiceByDescription(description);
            return convertServiceToDto(services);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());

        }
    }

    /**
     * Controller to update a service
     * @param description the description of the service
     * @param price the price of the service
     * @return A serviceDto
     * @throws IllegalArgumentException if to update the service fail
     */
    @PutMapping(value = {"/{description}", "/{description}/"})
    public ServiceDto updateServicesByDescription(
        @PathVariable("description") String description, 
        @RequestParam int price,
        @RequestHeader String token) {
        authService.authenticateManager(token);
        try {
            Service services = servicesService.updateService(description, price);
            return convertServiceToDto(services);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

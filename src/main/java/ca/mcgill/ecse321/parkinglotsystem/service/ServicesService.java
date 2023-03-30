package ca.mcgill.ecse321.parkinglotsystem.service;

import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
public class ServicesService {
    @Autowired
    ServiceRepository servicesRepository;

    /**
     * method to create a service
     *
     * @param description
     * @param price
     * @return newly created service or null
     */
    @Transactional
    public Service createService(String description, int price) {
        // Input validation
        String val_int = price + "";
        if (servicesRepository.findServiceByDescription(description)!=null) {
            throw new IllegalArgumentException("service already exist!");
        }
        if (description.equals("")) {
            throw new IllegalArgumentException("service description cannot be empty!");
        }
        if ((val_int == null || price < 0)) {
            throw new IllegalArgumentException("price input cannot be empty or less than zero!");
        }

        Service service = new Service();
        service.setDescription(description);
        service.setPrice(price);
        servicesRepository.save(service);
        return service;
    }

    @Transactional
    public List<Service> getAllServices() {
        Iterable<Service> pIterable = servicesRepository.findAll();
        return HelperMethods.toList(pIterable);

    }

    // method to find a service by description
    @Transactional
    public Service getServiceByDescription(String description) {
        Service service = servicesRepository.findServiceByDescription(description);
        if(service == null) {
            throw new CustomException("No service found.", HttpStatus.NOT_FOUND);
        }
        return service;
    }

    // method to find a service by price
    @Transactional
    public List<Service> getServiceByPrice(int price) {
        return servicesRepository.findServiceByPrice(price);
    }

    //method to delete a service
    @Transactional
    public Service deleteServiceByDescription(String description) {
        String error = "";
        Service services = servicesRepository.findServiceByDescription(description);
        if (services == null) {
            error = error + "No service with that description was found!";
        }
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        } else {
            servicesRepository.delete(services);
            return services;
        }
    }

    //method to update a service
    @Transactional
    public Service updateService(String description, int price) {
        String error = "";

        Service services = servicesRepository.findServiceByDescription(description);
        if (servicesRepository.findServiceByDescription(description) == null) {
            error = error + "No service with that description exists!";
        }
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        if (price < 0) {
            throw new IllegalArgumentException("price input cannot be empty or less than zero!");
        }
        services.setPrice(price);
        return services;
    }
}


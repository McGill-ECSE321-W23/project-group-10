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
     * method to create a service.
     * @author Luke
     * @param description the description of the service
     * @param price the price of the service
     * @return A Service
     * @throws CustomException if to create the service fail
     */
    @Transactional
    public Service createService(String description, int price) {
        // Input validation
        String val_int = price + "";
        if (servicesRepository.findServiceByDescription(description)!=null) {
            throw new CustomException("Service already exists!", HttpStatus.BAD_REQUEST);
        }
        if (description.equals("")) {
            throw new CustomException("Service description cannot be empty!", HttpStatus.BAD_REQUEST);
        }
        if ((val_int == null || price < 0)) {
            throw new CustomException("Price input cannot be empty or less than zero!", HttpStatus.BAD_REQUEST);

            
        }

        Service service = new Service();
        service.setDescription(description);
        service.setPrice(price);
        servicesRepository.save(service);
        return service;
    }

    /**
     * method to get all services.
     * @author Luke
     * @return A List of Service
     */
    @Transactional
    public List<Service> getAllServices() {
        Iterable<Service> pIterable = servicesRepository.findAll();
        return HelperMethods.toList(pIterable);

    }

    /**
     * method to get a service by description.
     * @author Luke
     * @param description the description of the service
     * @return A Service
     * @throws CustomException if to get the service fail
     */
    @Transactional
    public Service getServiceByDescription(String description) {
        Service service = servicesRepository.findServiceByDescription(description);
        if(service == null) {
            throw new CustomException("No service found.", HttpStatus.NOT_FOUND);
        }
        return service;
    }

    /**
     * method to get services by price.
     * @author Luke
     * @param price the price of the service
     * @return A List of Service
     * @throws CustomException if to get the service fail
     */
    @Transactional
    public List<Service> getServiceByPrice(int price) {
        return servicesRepository.findServiceByPrice(price);
    }

    /**
     * method to delete a service by description.
     * @author Luke
     * @param description the description of the service
     * @return the deleted Service
     * @throws CustomException if to delete the service fail
     */
    @Transactional
    public Service deleteServiceByDescription(String description) {
        String error = "";
        Service services = servicesRepository.findServiceByDescription(description);
        if (services == null) {
            error = error + "No service with that description was found!";
        }
        if (error.length() > 0) {
            throw new CustomException(error, HttpStatus.NOT_FOUND);
        } else {
            servicesRepository.delete(services);
            return services;
        }
    }

    /**
     * method to update a service.
     * @author Luke
     * @param description the description of the service
     * @param price the price of the service
     * @return the updated Service
     * @throws CustomException if to update the service fail
     */
    @Transactional
    public Service updateService(String description, int price) {
        String error = "";

        Service services = servicesRepository.findServiceByDescription(description);
        if (servicesRepository.findServiceByDescription(description) == null) {
            error = error + "No service with that description exists!";
        }
        if (error.length() > 0) {
            throw new CustomException(error, HttpStatus.NOT_FOUND);
        }
        if (price < 0) {

            throw new CustomException("Price input cannot be empty or less than zero!", HttpStatus.BAD_REQUEST);

        }
        services.setPrice(price);
        return services;
    }
}


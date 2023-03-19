package ca.mcgill.ecse321.parkinglotsystem.service;

import ca.mcgill.ecse321.parkinglotsystem.dao.ServicesRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicesService {
    @Autowired
    ServicesRepository servicesRepository;

    /**
     * method to create a service
     * @param description
     * @param price
     * @return newly created service or null
     */
    @Transactional
    public Services createServices(String description, int price){
        // Input validation
        String val_int = price + "";
        if (description == null) {
            throw new IllegalArgumentException("service description cannot be empty!");
        }
        if((val_int==null || price<0)){
            throw new IllegalArgumentException("price input cannot be empty or less than zero!");
        }

        Services services=new Services();
        services.setDescription(description);
        services.setPrice(price);
        servicesRepository.save(services);
        return services;
    }

    public List<Services> getAllServices() {
        Iterable<Services> pIterable = servicesRepository.findAll();
        return HelperMethods.toList(pIterable);

    }

    // method to find a service by description
    @Transactional
    public Services getServicesByDescription(String description) {
        return servicesRepository.findServiceByDescription(description);
    }

    // method to find a service by price
    @Transactional
    public List<Services> getServicesByPrice(int price) {
        return servicesRepository.findServiceByPrice(price);
    }

    //method to delete a service
    @Transactional
    public Services deleteServicesByDescription(String description){
        String error="";
        Services services=servicesRepository.findServiceByDescription(description);
        if(services==null){
            error=error+"No service with that description was found!";
        }
        if(error.length()>0){
            throw new IllegalArgumentException(error);
        }else{
            servicesRepository.delete(services);
            return services;
        }
    }

    //method to update a service
    @Transactional
    public Services updateServices(String description, int price){
        String error="";

        Services services=servicesRepository.findServiceByDescription(description);
        if(servicesRepository.findServiceByDescription(description)==null){
            error=error+"No service with that description exists!";
        }
        if(error.length()>0){
            throw new IllegalArgumentException(error);
        }else{
            services.setPrice(price);
            return services;
        }
    }
}

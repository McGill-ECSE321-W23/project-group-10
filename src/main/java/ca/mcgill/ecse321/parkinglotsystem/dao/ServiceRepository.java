package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;

public interface ServiceRepository extends CrudRepository<Service, String> {

    //find a service by description
    Service findServiceByDescription(String description);

    //find services by price
    List<Service> findServiceByPrice(int price);
}
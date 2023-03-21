package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.model.Services;
import org.springframework.data.repository.CrudRepository;

public interface ServicesRepository extends CrudRepository<Services, String> {

    //find a service by description
    Services findServiceByDescription(String description);

    //find services by price
    List<Services> findServiceByPrice(int price);
}
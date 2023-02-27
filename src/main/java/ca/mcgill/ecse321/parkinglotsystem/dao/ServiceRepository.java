package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;

public interface ServiceRepository extends CrudRepository<Service, String>{
    Service findServiceTopById (Integer id);
    List<Service> findServicesByAssigned (Boolean isAssigned);
    List<Service> findServicesByService (Service service);
}
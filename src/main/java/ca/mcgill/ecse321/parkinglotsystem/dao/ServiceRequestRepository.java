package ca.mcgill.ecse321.parkinglotsystem.dao;

import ca.mcgill.ecse321.parkinglotsystem.model.Service;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceRequestRepository extends CrudRepository<ServiceRequest, String>{
    ServiceRequest findServiceRequestById (Integer id);
    List<ServiceRequest> findServiceRequestByAssigned (Boolean isAssigned);
    List<ServiceRequest> findServiceRequestByService (Service service);
}
package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.Service;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;

public interface ServiceRequestRepository extends CrudRepository<ServiceRequest, Integer> {

    ServiceRequest findServiceRequestById(int id);

    List<ServiceRequest> findServiceRequestByIsAssigned(boolean isAssigned);

    List<ServiceRequest> findServiceRequestByService(Service service);

}
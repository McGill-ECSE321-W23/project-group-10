package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.Service;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;

public interface ServiceRequestRepository extends CrudRepository<ServiceRequest, Integer> {

    //find a service request by id
    ServiceRequest findServiceRequestById(int id);

    //find service requests by checking whether been assigned
    List<ServiceRequest> findServiceRequestByIsAssigned(boolean isAssigned);

    //find service requests by service
    List<ServiceRequest> findServiceRequestByService(Service service);

}
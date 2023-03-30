package ca.mcgill.ecse321.parkinglotsystem.service;

import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceRequestRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceRequestService {
    @Autowired
    ServiceRequestRepository serviceRequestRepository;
    @Autowired
    ServicesService serviceRepository;

    /**
     * method to get all service requests.
     * @author Luke
     * @return A List of ServiceRequest
     */
    @Transactional
    public List<ServiceRequest> getAllServiceRequest() {
        Iterable<ServiceRequest> pIterable = serviceRequestRepository.findAll();
        return HelperMethods.toList(pIterable);
    }

    /**
     * method to get a service request by id.
     * @author Luke
     * @param id the id of the service request
     * @return A ServiceRequest
     */
    @Transactional
    public ServiceRequest getServiceRequestById(int id) {
        return serviceRequestRepository.findServiceRequestById(id);
    }

    /**
     * method to get service requests by isAssigned.
     * @author Luke
     * @param isAssigned whether the service request is assigned
     * @return A List of ServiceRequest
     */
    @Transactional
    public List<ServiceRequest> getServiceRequestByIsAssigned(boolean isAssigned) {
        return serviceRequestRepository.findServiceRequestByIsAssigned(isAssigned);
    }

    /**
     * method to get service requests by service description.
     * @author Luke
     * @param description the description of the service
     * @return A List of ServiceRequest
     */
    @Transactional
    public List<ServiceRequest> getServiceRequestByServices(String description) {
        return serviceRequestRepository.findServiceRequestByService(serviceRepository.getServiceByDescription(description));
    }

}

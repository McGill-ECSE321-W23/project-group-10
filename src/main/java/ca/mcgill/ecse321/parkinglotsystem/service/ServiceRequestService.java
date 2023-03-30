package ca.mcgill.ecse321.parkinglotsystem.service;

import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceRequestRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;
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
     * Method to get all service requests.
     * @return A List of ServiceRequest
     */
    @Transactional
    public List<ServiceRequest> getAllServiceRequest() {
        Iterable<ServiceRequest> pIterable = serviceRequestRepository.findAll();
        return HelperMethods.toList(pIterable);
    }

    /**
     * Method to get a service request by id.
     * @param id the id of the service request
     * @return A ServiceRequest
     */
    @Transactional
    public ServiceRequest getServiceRequestById(int id) {
        return serviceRequestRepository.findServiceRequestById(id);
    }

    /**
     * Method to get service requests by isAssigned.
     * @param isAssigned whether the service request is assigned
     * @return A List of ServiceRequest
     */
    @Transactional
    public List<ServiceRequest> getServiceRequestByIsAssigned(boolean isAssigned) {
        return serviceRequestRepository.findServiceRequestByIsAssigned(isAssigned);
    }

    /**
     * Method to get service requests by service description.
     * @param description the description of the service
     * @return A List of ServiceRequest
     */
    @Transactional
    public List<ServiceRequest> getServiceRequestByServices(String description) {
        return serviceRequestRepository.findServiceRequestByService(serviceRepository.getServiceByDescription(description));
    }

}

package ca.mcgill.ecse321.parkinglotsystem.service;

import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceRequestRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;
import ca.mcgill.ecse321.parkinglotsystem.model.Services;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceRequestService {
    @Autowired
    ServiceRequestRepository serviceRequestRepository;

    // method to find all service requests
    @Transactional
    public List<ServiceRequest> getAllServiceRequest() {
        Iterable<ServiceRequest> pIterable = serviceRequestRepository.findAll();
        return HelperMethods.toList(pIterable);
    }

    // method to find a service request by id
    @Transactional
    public ServiceRequest getServiceRequestById(int id) {
        return serviceRequestRepository.findServiceRequestById(id);
    }

    // method to find a service request by is assigned
    @Transactional
    public List<ServiceRequest> getServiceRequestByIsAssigned(boolean isAssigned) {
        return serviceRequestRepository.findServiceRequestByIsAssigned(isAssigned);
    }

    // method to find a service request by service
    @Transactional
    public List<ServiceRequest> getPaymentServiceByServices(Services services) {
        return serviceRequestRepository.findServiceRequestByService(services);
    }

}

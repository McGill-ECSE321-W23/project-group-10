package ca.mcgill.ecse321.parkinglotsystem.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dto.ServiceRequestDto;
import ca.mcgill.ecse321.parkinglotsystem.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.model.*;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.convertServiceRequestToDto;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/service-request")
public class ServiceRequestController {

    @Autowired
    ServiceRequestService serviceRequestService;

    /**
     * Controller to get all Service Requests
     * @return A List of ServiceRequestDto
     * @throws Exception if to get service requests fail
     */
    @GetMapping(value = {"", "/"})
    public List<ServiceRequestDto> getAllServiceRequest() throws Exception {
        List<ServiceRequestDto> sList = new ArrayList<>();
        for (ServiceRequest serviceRequest : serviceRequestService.getAllServiceRequest()) {
            sList.add(convertServiceRequestToDto(serviceRequest));
        }
        if (sList.size() == 0) throw new Exception("There are no service request");
        return sList;
    }

    /**
     * Controller to get a service request by id
     * @param id the id of the service request
     * @return A ServiceRequestDto
     */
    @GetMapping(value = {"/{id}", "/{id}/"})
    public ServiceRequestDto getServiceRequestById(@PathVariable("id") int id) {
        ServiceRequest serviceRequest = serviceRequestService.getServiceRequestById(id);
        return convertServiceRequestToDto(serviceRequest);
        
    }

    /**
     * Controller to get service requests by is assigned
     * @param isAssigned whether the service request is assigned status
     * @return A List of ServiceRequestDto
     */
    @GetMapping(value = {"/all-by-is-assigned/{isAssigned}", "/all-by-is-assigned/{isAssigned}/"})
    public List<ServiceRequestDto> getServiceRequestByIsAssigned(@PathVariable("isAssigned") boolean isAssigned) {
        List<ServiceRequestDto> seList = new ArrayList<>();
        List<ServiceRequest> serviceRequests = serviceRequestService.getServiceRequestByIsAssigned(isAssigned);
        if (serviceRequests.size() != 0) {
            for (ServiceRequest se : serviceRequests) {
                seList.add(convertServiceRequestToDto(se));
            }
        }
        return seList;
        
    }

    /**
     * Controller to get service requests by service
     * @param
     * @return List<ServiceRequestDto>
     */
    @GetMapping(value = {"/all-by-service-id/{id}", "/all-by-service-id/{id}/"})
    public List<ServiceRequestDto> getServiceRequestsByService(@PathVariable("service") String serviceDescription) {
        List<ServiceRequestDto> ServiceRequests = new ArrayList<>();
        List<ServiceRequest> serviceRequest = serviceRequestService.getServiceRequestByServices(serviceDescription);
        if (serviceRequest.size() != 0) {
            for (ServiceRequest se : serviceRequest) {
                ServiceRequests.add(convertServiceRequestToDto(se));
            }
        }
        return ServiceRequests;
        
    }
}

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
@RequestMapping("/api/service_request")
public class ServiceRequestController {

    @Autowired
    ServiceRequestService serviceRequestService;

    /**
     * method to get all Service Request
     *
     * @return List<ServiceRequestDto>
     * @throws Exception
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
     * method to get a service request by id
     *
     * @param id
     * @return ServiceRequestDto
     */
    @GetMapping(value = {"/{id}", "/{id}/"})
    public ServiceRequestDto getServiceRequestById(@PathVariable("id") int id) {
        try {
            ServiceRequest serviceRequest = serviceRequestService.getServiceRequestById(id);
            return convertServiceRequestToDto(serviceRequest);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * method to get service requests by is assigned
     *
     * @return List<ServiceRequestDto>
     * @throws Exception
     */
    @GetMapping(value = {"/all-by-is-assigned/{isAssigned}", "/all-by-is-assigned/{isAssigned}/"})
    public List<ServiceRequestDto> getServiceRequestByIsAssigned(@PathVariable("isAssigned") boolean isAssigned) {
        List<ServiceRequestDto> seList = new ArrayList<>();
        try {
            List<ServiceRequest> serviceRequests = serviceRequestService.getServiceRequestByIsAssigned(isAssigned);
            if (serviceRequests.size() != 0) {
                for (ServiceRequest se : serviceRequests) {
                    seList.add(convertServiceRequestToDto(se));
                }
            }
            return seList;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * method to get service requests by service
     *
     * @return List<ServiceRequestDto>
     * @throws Exception
     */
    @GetMapping(value = {"/all-by-service/{service}", "/all-by-service/{service}/"})
    public List<ServiceRequestDto> getServiceRequestsByService(@PathVariable("service") Service service) {
        // TODO: use IDs as arguments, not model objects
        List<ServiceRequestDto> ServiceRequests = new ArrayList<>();
        try {
            List<ServiceRequest> serviceRequest = serviceRequestService.getServiceRequestByService(service);
            if (serviceRequest.size() != 0) {
                for (ServiceRequest se : serviceRequest) {
                    ServiceRequests.add(convertServiceRequestToDto(se));
                }
            }
            return ServiceRequests;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

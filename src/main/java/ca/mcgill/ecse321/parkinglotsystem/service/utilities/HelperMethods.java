package ca.mcgill.ecse321.parkinglotsystem.service.utilities;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dto.*;
import ca.mcgill.ecse321.parkinglotsystem.model.*;

public class HelperMethods {

    /**
     * Converts an Iterable to a List.
     *
     * @param <T> Element type
     * @param iterable
     * @return A List containing the elements of the iterable
     */
    public static <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * Helper method to convert parking spot type to a DTO
     *
     * @param parkingSpotType
     * @return Dto
     */
    public static ParkingSpotTypeDto convertParkingSpotTypeToDto(ParkingSpotType parkingSpotType) {

        if (parkingSpotType == null) {
            throw new IllegalArgumentException("There is no such parking spot type! ");
        }
        ParkingSpotTypeDto parkingSpotTypeDto = new ParkingSpotTypeDto();
        parkingSpotTypeDto.setName(parkingSpotType.getName());
        parkingSpotTypeDto.setFee(parkingSpotType.getFee());
        return parkingSpotTypeDto;
    }

    /**
     * Helper method to convert parking spot type to a DTO
     *
     * @param parkingSpot
     * @return Dto
     */
    public static ParkingSpotDto convertParkingSpotToDto(ParkingSpot parkingSpot) {

        if (parkingSpot == null) {
            throw new IllegalArgumentException("There is no such parking spot! ");
        }
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setId(parkingSpot.getId());
        parkingSpotDto.setType(convertParkingSpotTypeToDto(parkingSpot.getType()));
        return parkingSpotDto;
    }

    /**
     * Helper method to convert parking spot type to a DTO
     *
     * @param manager
     * @return Dto
     */
    public static ManagerDto convertManagerToDto(Manager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("There is no such manager! ");
        }
        ManagerDto managerDto = new ManagerDto();
        managerDto.setEmail(manager.getEmail());
        managerDto.setName(manager.getName());
        managerDto.setPhone(manager.getPhone());
        managerDto.setPassword(manager.getPassword());
        return managerDto;
    }

    /**
     * Converts a SubWithAccount object to a DTO.
     *
     * @param subWithAccount
     * @return a DTO representing the SubWithAccount object
     */
    public static SubWithAccountDto convertSubWithAccountToDto(SubWithAccount subWithAccount) {
        ParkingSpotDto parkingSpot = convertParkingSpotToDto(subWithAccount.getParkingSpot());
        MonthlyCustomerDto monthlyCustomer =
                convertMonthlyCustomerToDto(subWithAccount.getCustomer());

        return new SubWithAccountDto(
                subWithAccount.getId(),
                subWithAccount.getDate(),
                parkingSpot,
                subWithAccount.getNbrMonths(),
                monthlyCustomer
        );
    }

    /**
     * Helper method to convert service to a DTO
     *
     * @param services
     * @return Dto
     */
    public static ServicesDto convertServicesToDto(Services services) {

        if (services == null) {
            throw new IllegalArgumentException("There is no such service! ");
        }
        ServicesDto servicesDto = new ServicesDto();
        servicesDto.setDescription(services.getDescription());
        servicesDto.setPrice(services.getPrice());
        return servicesDto;
    }

    /**
     * Helper method to convert payment service to a DTO
     *
     * @param paymentService
     * @return Dto
     */
    public static PaymentServiceDto convertPaymentServiceToDto(PaymentService paymentService) {

        if (paymentService == null) {
            throw new IllegalArgumentException("There is no such payment service! ");
        }

        PaymentServiceDto paymentServiceDto = new PaymentServiceDto();
        paymentServiceDto.setServiceRequestDto(convertServiceRequestToDto(paymentService.getServiceReq()));
        return paymentServiceDto;
    }

    /**
     * Helper method to convert service request to a DTO
     *
     * @param serviceRequest
     * @return Dto
     */
    public static ServiceRequestDto convertServiceRequestToDto(ServiceRequest serviceRequest) {

        if (serviceRequest == null) {
            throw new IllegalArgumentException("There is no such service request! ");
        }

        ServiceRequestDto serviceRequestDto = new ServiceRequestDto();
        serviceRequestDto.setId(serviceRequest.getId());
        serviceRequestDto.setIsAssigned(serviceRequest.getIsAssigned());
        serviceRequestDto.setServicesDto(convertServicesToDto(serviceRequest.getService()));
        return serviceRequestDto;
    }
}

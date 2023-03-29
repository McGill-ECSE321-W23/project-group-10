package ca.mcgill.ecse321.parkinglotsystem.service.utilities;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dto.*;
import ca.mcgill.ecse321.parkinglotsystem.model.*;

public class HelperMethods {

    /**
     * Converts an Iterable to a List.
     *
     * @param <T>      Element type
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
     * method to convert to dto
     * @param paymentReservation
     * @return
     */
    public static PaymentReservationDto convertPaymentReservationToDto(PaymentReservation paymentReservation) {
        if (paymentReservation == null) {
            throw new IllegalArgumentException("There is no such payment reservation to convert! ");
        }
        PaymentReservationDto paymentReservationDto = new PaymentReservationDto();
        paymentReservationDto.setId(paymentReservation.getId());
        paymentReservationDto.setAmount(paymentReservation.getAmount());
        paymentReservationDto.setDateTime(paymentReservation.getDateTime());
        paymentReservationDto.setReservation(paymentReservation.getReservation());
        return paymentReservationDto;
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
    public static ServiceDto convertServiceToDto(Service services) {

        if (services == null) {
            throw new IllegalArgumentException("There is no such service! ");
        }
        ServiceDto servicesDto = new ServiceDto();
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
        serviceRequestDto.setServicesDto(convertServiceToDto(serviceRequest.getService()));
        return serviceRequestDto;
    }



    /**
     * Helper method to convert parking spot type to a DTO
     * @param pls
     * @return Dto
     */
    public static ParkingLotSystemDto convertParkingLotSystemToDto(ParkingLotSystem pls) {
        if (pls == null) {
            throw new IllegalArgumentException("There is no such parkingLotSystem! ");
        }
        ParkingLotSystemDto plsDto = new ParkingLotSystemDto();
        plsDto.setId(pls.getId());
        plsDto.setOpenTime(pls.getOpenTime());
        plsDto.setCloseTime(pls.getCloseTime());
        return plsDto;
    }

    /**
     * Helper method to convert ServiceReqWithAccount type to a DTO
     * @param srwa
     * @return Dto
     */
    public static ServiceReqWithAccountDto convertServiceReqWithAccountToDto(ServiceReqWithAccount srwa) {
        if (srwa == null) {
            throw new IllegalArgumentException("There is no such ServiceReqWithAccount! ");
        }
        ServiceReqWithAccountDto srwaDto = new ServiceReqWithAccountDto(srwa.getId(), srwa.getIsAssigned(), convertServiceToDto(srwa.getService()), convertMonthlyCustomerToDto(srwa.getCustomer()));
        return srwaDto;
    }

    /**
     * Helper method to convert ServiceReqWithAccount type to a DTO
     * @param srwoa
     * @return Dto
     */
    public static ServiceReqWithoutAccountDto convertServiceReqWithoutAccountToDto(ServiceReqWithoutAccount srwoa) {
        if (srwoa == null) {
            throw new IllegalArgumentException("There is no such ServiceReqWithoutAccount! ");
        }
        ServiceReqWithoutAccountDto srwoaDto = new ServiceReqWithoutAccountDto(srwoa.getId(), srwoa.getIsAssigned(), convertServiceToDto(srwoa.getService()), srwoa.getLicenseNumber());
        return srwoaDto;
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
        return managerDto;
    }

    /**
     * Helper method to convert parking spot type to a DTO
     *
     * @param employee
     * @return Dto
     */
    public static EmployeeDto convertEmployeeToDto(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("There is no such employee! ");
        }
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setName(employee.getName());
        employeeDto.setPhone(employee.getPhone());
        return employeeDto;
    }


    /**
     * Helper method to convert parking spot type to a DTO
     *
     * @param mc
     * @return Dto
     */
    public static MonthlyCustomerDto convertMonthlyCustomerToDto(MonthlyCustomer mc) {
        if (mc == null) {
            throw new IllegalArgumentException("There is no such monthly customer! ");
        }
        MonthlyCustomerDto mcDto = new MonthlyCustomerDto();
        mcDto.setEmail(mc.getEmail());
        mcDto.setName(mc.getName());
        mcDto.setPhone(mc.getPhone());
        mcDto.setLicenseNumber(mc.getLicenseNumber());
        return mcDto;
    }


    /**
     * Helper method to verify email
     *
     * @param email
     * @return error
     */
    public static String verifyEmail(String email) {
        String error = "";
        if ((email == null || email.trim().length() == 0)) {
            error = error + "Email cannot be empty! ";
        } else if (email.indexOf("@") == -1) {
            error = error + "Email must contain @ ! ";
        }
        return error;
    }


    /**
     * Helper method to verify name
     *
     * @param name
     * @return error
     */
    public static String verifyName(String name) {
        String error = "";
        if ((name == null || name.trim().length() == 0)) {
            error = error + "Name cannot be empty! ";
        }
        return error;
    }


    /**
     * Helper method to verify phone
     *
     * @param phone
     * @return error
     */
    public static String verifyPhone(String phone) {
        String error = "";
        if (phone.trim().length() != 10) {
            error = error + "Phone must have exactlty 10 digits! ";
        }
        if (phone.trim().matches("\\d+") == false && phone.trim().length()>0) {
            error = error + "Phone cannot have non-number digits! ";
        }
        return error;
    }


    /**
     * Helper method to verify password
     *
     * @param password
     * @return error
     */
    public static String verifyPassword(String password) {
        String error = "";
        if (password.trim().length() < 8) {
            error = error + "Password cannot be shorter than 8 digits! ";
        }
        if (password.trim().matches(".*[a-zA-Z].*") == false) {
            error = error + "Password must contain letter! ";
        }
        if (password.trim().matches(".*\\d+.*") == false) {
            error = error + "Password must contain number! ";
        }
        return error;
    }

    /**
     * Helper method to verify licenseNumber
     *
     * @param licenseNumber
     * @return error
     */
    public static String verifyLicenseNumber(String licenseNumber) {
        String error = "";
        if (licenseNumber.trim().length() < 4) {
            error = error + "MonthlyCustomer license number cannot be shorter than 4 digits! ";
        }
        return error;
    }
}

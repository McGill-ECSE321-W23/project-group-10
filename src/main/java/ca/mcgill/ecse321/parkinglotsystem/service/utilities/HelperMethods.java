package ca.mcgill.ecse321.parkinglotsystem.service.utilities;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dto.*;
import ca.mcgill.ecse321.parkinglotsystem.model.*;

public class HelperMethods {

    /**
     * Converts an Iterable to a List.
     * @param <T> Element type
     * @param iterable
     * @return A List containing the elements of the iterable
     */
    public static <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

    /**
     * Helper method to convert parking spot type to a DTO
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
     * Helper method to convert parking spot type to a DTO
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
     * Helper method to convert parking spot type to a DTO
     * @param employee  
     * @return Dto
     */
    public static EmployeeDto convertEmployeeToDto(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("There is no such employee! ");
        }
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmail(employee.getEmail());
        employee.setName(employee.getName());
        employeeDto.setPhone(employee.getPhone());
        employeeDto.setPassword(employee.getPassword());
        return employeeDto;
    }


    /**
     * Helper method to convert parking spot type to a DTO
     * @param monthlyCustomer  
     * @return Dto
     */
    public static MonthlyCustomerDto convertMonthlyCustomerToDto(MonthlyCustomer mc) {
        if (mc == null) {
            throw new IllegalArgumentException("There is no such monthly customer! ");
        }
        MonthlyCustomerDto mcDto = new MonthlyCustomerDto();
        mcDto.setEmail(mc.getEmail());
        mc.setName(mc.getName());
        mcDto.setPhone(mc.getPhone());
        mcDto.setPassword(mc.getPassword());
        mcDto.setLicenseNumber(mc.getLicenseNumber());
        return mcDto;
    }
}

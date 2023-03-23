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
        employeeDto.setPassword(employee.getPassword());
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
        mcDto.setPassword(mc.getPassword());
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

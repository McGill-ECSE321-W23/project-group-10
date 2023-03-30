package ca.mcgill.ecse321.parkinglotsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.MonthlyCustomerRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import org.springframework.http.HttpStatus;

@Service
public class MonthlyCustomerService {
    
    @Autowired
    MonthlyCustomerRepository monthlyCustomerRepository;

    /**
     * method to create a monthly customer
     * @param email the email of the monthly customer
     * @param name the name of the monthly customer
     * @param phone the phone number of hte monthly customer
     * @param password the password of the monthly customer
     * @param licenseNumber the license number of the monthly customer
     * @return newly created MonthlyCustomer
     * @throws CustomException if to create monthly customer fail
     */
    @Transactional
     public MonthlyCustomer createMonthlyCustomer(String email,String name,String phone,String password,String licenseNumber){
        String error="";
        error=error+HelperMethods.verifyEmail(email)+HelperMethods.verifyName(name)+HelperMethods.verifyPhone(phone)
            +HelperMethods.verifyPassword(password)+HelperMethods.verifyLicenseNumber(licenseNumber);
        List<MonthlyCustomer> existing=getAllMonthlyCustomers();
        for(int i=0;i<existing.size();i++){
            if(existing.get(i).getEmail().trim().equals(email.trim())){
                error=error+"Cannot have the same email as an existing account! ";
            }
        }
        if(error.length()>0){
            throw new CustomException(error,HttpStatus.BAD_REQUEST);
        }
        MonthlyCustomer mc=new MonthlyCustomer();
        mc.setEmail(email);
        mc.setName(name);
        mc.setPassword(password);
        mc.setPhone(phone);
        mc.setLicenseNumber(licenseNumber);
        monthlyCustomerRepository.save(mc);
        return mc;
     }

    /**
     * method to get a monthly customer by email
     * @param email the email of the monthly customer
     * @return MonthlyCustomer
     * @throws CustomException if to get monthly customer fail
     */
     @Transactional
     public MonthlyCustomer getMonthlyCustomerByEmail(String email){
        MonthlyCustomer customer = monthlyCustomerRepository.findMonthlyCustomerByEmail(email);
        if(customer == null) {
            throw new CustomException("Invalid monthly customer email! ", HttpStatus.BAD_REQUEST);
        }
        return customer;
     }

    /**
     * method to get monthly customers by name
     * @param name the name of the monthly customer
     * @return A List of MonthlyCustomer or null
     */
     @Transactional
     public List<MonthlyCustomer> getMonthlyCustomerByName(String name){
        return monthlyCustomerRepository.findMonthlyCustomerByName(name);
     }

    /**
     * method to get monthly customers by phone
     * @param phone the phone number of the monthly customer
     * @return A List of MonthlyCustomer or null
     */
     @Transactional
     public List<MonthlyCustomer> getMonthlyCustomerByPhone(String phone){
        return monthlyCustomerRepository.findMonthlyCustomerByPhone(phone);
     }

    /**
     * method to get monthly customers by license number
     * @param licenseNumber the license number of the monthly customer
     * @return A List of MonthlyCustomer or null
     */
     @Transactional
     public List<MonthlyCustomer> getMonthlyCustomerByLicenseNumber(String licenseNumber){
        return monthlyCustomerRepository.findMonthlyCustomerByLicenseNumber(licenseNumber);
     }

    /**
     * method to get all monthly customers
     * @return A List of MonthlyCustomer or null
     */
     @Transactional
     public List<MonthlyCustomer> getAllMonthlyCustomers(){
        Iterable<MonthlyCustomer> mcIterable=monthlyCustomerRepository.findAll();
        return HelperMethods.toList(mcIterable);
     }

    /**
     * method to delete a monthly customer
     * @param email the email of the monthly customer
     * @return the deleted MonthlyCustomer
     * @throws CustomException if to delete monthly customer fail
     */
     @Transactional
     public MonthlyCustomer deleteMonthlyCustomerByEmail(String email){
        String error="";
        MonthlyCustomer mc=monthlyCustomerRepository.findMonthlyCustomerByEmail(email);
        if(mc==null){
            error=error+"No monthly customer with that email was found!";
        }
        if(error.length()>0){
            throw new CustomException(error,HttpStatus.BAD_REQUEST);
        }else{
            monthlyCustomerRepository.delete(mc);
            return mc;
        }
     }

    /**
     * method to update a monthly customer
     * @param email the email of the monthly customer
     * @param name the name of the monthly customer
     * @param phone the phone number of the monthly customer
     * @param password the password of the monthly customer
     * @param licenseNumber the license number of the monthly customer
     * @return the updated MonthlyCustomer
     * @throws CustomException if to update monthly customer fail
     */
     @Transactional
     public MonthlyCustomer updateMonthlyCustomer(String email,String name,String phone,String password,String licenseNumber){
        String error="";
        MonthlyCustomer mc=monthlyCustomerRepository.findMonthlyCustomerByEmail(email);
        if(monthlyCustomerRepository.findMonthlyCustomerByEmail(email)==null){
            error=error+"No monthly customer with that email exists!";
        }else{
            error=error+HelperMethods.verifyEmail(email)+HelperMethods.verifyName(name)+HelperMethods.verifyPhone(phone)
            +HelperMethods.verifyPassword(password)+HelperMethods.verifyLicenseNumber(licenseNumber);
        }
        if(error.length()>0){
            throw new CustomException(error,HttpStatus.BAD_REQUEST);
        }else{
            mc.setName(name);
            mc.setPhone(phone);
            mc.setPassword(password);
            mc.setLicenseNumber(licenseNumber);
            return mc;
        }
     }
}
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
     * method to a create monthly customer
     * @param email
     * @param name
     * @param phone
     * @param password
     * @param licenseNumber
     * @return newly created Monthly Customer or null
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


     @Transactional
     public MonthlyCustomer getMonthlyCustomerByEmail(String email){
        MonthlyCustomer customer = monthlyCustomerRepository.findMonthlyCustomerByEmail(email);
        if(customer == null) {
            throw new CustomException("Invalid monthly customer email! ", HttpStatus.BAD_REQUEST);
        }
        return customer;
     }


     @Transactional
     public List<MonthlyCustomer> getMonthlyCustomerByName(String name){
        return monthlyCustomerRepository.findMonthlyCustomerByName(name);
     }


     @Transactional
     public List<MonthlyCustomer> getMonthlyCustomerByPhone(String phone){
        return monthlyCustomerRepository.findMonthlyCustomerByPhone(phone);
     }

     @Transactional
     public List<MonthlyCustomer> getMonthlyCustomerByLicenseNumber(String licenseNumber){
        return monthlyCustomerRepository.findMonthlyCustomerByLicenseNumber(licenseNumber);
     }


     @Transactional
     public List<MonthlyCustomer> getAllMonthlyCustomers(){
        Iterable<MonthlyCustomer> mcIterable=monthlyCustomerRepository.findAll();
        return HelperMethods.toList(mcIterable);
     }


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
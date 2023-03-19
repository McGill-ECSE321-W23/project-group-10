package ca.mcgill.ecse321.parkinglotsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.MonthlyCustomerRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;

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
        if(name==null || name.trim().length()==0){
            error=error+"MonthlyCustomer name cannot be empty!";
        }
        if((email==null || email.trim().length()==0)){
            error=error+"MonthlyCustomer email cannot be empty!";
        }else if(email.indexOf("@")==-1){
            error=error+"MonthlyCustomer email must contain \"@\"!";
        }
        if(phone.trim().length()!=10){
            error=error+"MonthlyCustomer phone must have exactly 10 digits!";
        }
        if(phone.trim().matches("\\d+")==false){
            error=error+"MonthlyCustomer phone cannot have non-number digits!";
        }
        if(password.trim().length()<8){
            error=error+"MonthlyCustomer password cannot be shorter than 8 digits!";
        }
        if(licenseNumber.trim().length()<4){
            error=error+"MonthlyCustomer license number cannot be shorter than 4 digits!";
        }
        if(error.length()>0){
            throw new IllegalArgumentException(error);
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
        return monthlyCustomerRepository.findMonthlyCustomerByEmail(email);
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
     public List<MonthlyCustomer> getMonthlyCustomerByPassword(String password){
        return monthlyCustomerRepository.findMonthlyCustomerByName(password);
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
            throw new IllegalArgumentException(error);
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
        }
        if(error.length()>0){
            throw new IllegalArgumentException(error);
        }else{
            mc.setName(name);
            mc.setPhone(phone);
            mc.setPassword(password);
            mc.setLicenseNumber(licenseNumber);
            return mc;
        }
     }
}
package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.access.InvalidInvocationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ManagerRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Manager;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;

@Service
public class ManagerService {
    
    @Autowired
    ManagerRepository managerRepository;

    /**
     * method to a create manager
     * @param email
     * @param name
     * @param phone
     * @param password
     * @return newly created Manager or null
     */
    @Transactional
     public Manager createManager(String name,String email,String phone,String password){
        String error="";
        if(name==null || name.trim().length()==0){
            error=error+"Manager name cannot be empty!";
        }
        if((email==null || email.trim().length()==0)){
            error=error+"Manager email cannot be empty!";
        }else if(email.indexOf("@")==-1){
            error=error+"Manager email must contain \"@\"!";
        }
        if(phone.trim().length()!=10){
            error=error+"Manager phone must have exactlty 10 digits!";
        }
        if(phone.trim().matches("\\d+")==false){
            error=error+"Manager phone cannot have non-number digits!";
        }
        if(password.trim().length()<8){
            error=error+"Manager password cannot be shorter than 8 digits!";
        }
        if(error.length()>0){
            throw new IllegalArgumentException(error);
        }
        Manager manager=new Manager();
        manager.setEmail(email);
        manager.setName(name);
        manager.setPassword(password);
        manager.setPhone(phone);
        managerRepository.save(manager);
        return manager;
     }


     @Transactional
     public Manager getManagerByEmail(String email){
        return managerRepository.findManagerByEmail(email);
     }


     @Transactional
     public List<Manager> getManagerByName(String name){
        return managerRepository.findManagerByName(name);
     }


     @Transactional
     public List<Manager> getManagerByPhone(String phone){
        return managerRepository.findManagerByPhone(phone);
     }


     @Transactional
     public List<Manager> getManagerByPassword(String password){
        return managerRepository.findManagerByName(password);
     }


     @Transactional
     public List<Manager> getAllManagers(){
        Iterable<Manager> mIterable=managerRepository.findAll();
        return HelperMethods.toList(mIterable);
     }
}
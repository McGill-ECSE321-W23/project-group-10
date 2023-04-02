package ca.mcgill.ecse321.parkinglotsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ManagerRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.Manager;
import ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import org.springframework.http.HttpStatus;

@Service
public class ManagerService {
    
    @Autowired
    ManagerRepository managerRepository;

    /**
     * method to create a Manager
     * @author Louis
     * @param email the email of the manager
     * @param name the name of the manager
     * @param phone the phone number of the manager
     * @param password the password of the manager
     * @return A Manager
     * @throws CustomException if to create the manager fail
     */
    @Transactional
     public Manager createManager(String email,String name,String phone,String password){
        String error="";
        error=error+HelperMethods.verifyEmail(email)+HelperMethods.verifyName(name)+HelperMethods.verifyPhone(phone)
            +HelperMethods.verifyPassword(password);

        List<Manager> existing=getAllManagers();
        for(int i=0;i<existing.size();i++){
            if(existing.get(i).getEmail().trim().equals(email.trim())){
                error=error+"Cannot have the same email as an existing account! ";
            }
        }

        if(error.length()>0){
            throw new CustomException(error,HttpStatus.BAD_REQUEST);
        }
        Manager manager=new Manager();
        manager.setEmail(email);
        manager.setName(name);
        manager.setPassword(password);
        manager.setPhone(phone);
        managerRepository.save(manager);
        return manager;
     }


     /**
     * method to get a Manager by email
     * @author Louis
     * @param email the email of the manager
     * @return A Manager
      * @throws CustomException of to get the manager fail
     */
     @Transactional
     public Manager getManagerByEmail(String email){
        Manager ma = managerRepository.findManagerByEmail(email);
        if(ma == null) {
            throw new CustomException("Invalid manager email! ", HttpStatus.BAD_REQUEST);
        }
        return ma;
     }

     /**
     * method to get Managers by name
     * @author Louis
     * @param name the name of the manager
     * @return A List of Manager or null
     */
     @Transactional
     public List<Manager> getManagerByName(String name){
        return managerRepository.findManagerByName(name);
     }

     /**
     * method to get Managers by phone
     * @author Louis
     * @param phone the phone number of the manager
     * @return A List of Manager or null
     */
     @Transactional
     public List<Manager> getManagerByPhone(String phone){
        return managerRepository.findManagerByPhone(phone);
     }

    /**
     * method to get all Managers
     * @author Louis
     * @return A List of Manager or null
     */
     @Transactional
     public List<Manager> getAllManagers(){
        Iterable<Manager> mIterable=managerRepository.findAll();
        return HelperMethods.toList(mIterable);
     }

    /**
     * method to delete a Manager
     * @author Louis
     * @param email the email of the manager
     * @return newly deleted Manager
     * @throws CustomException if to delete the manager fail
     */
     @Transactional
     public Manager deleteManagerByEmail(String email){
        String error="";
        Manager ma=managerRepository.findManagerByEmail(email);
        if(ma==null){
            error=error+"No manager with that email was found!";
        }
        if(error.length()>0){
            throw new CustomException(error,HttpStatus.BAD_REQUEST);
        }else{
            managerRepository.delete(ma);
            return ma;
        }
     }

     /**
     * method to update a Manager
     * @author Louis
     * @param email the email of the manager
     * @param name the name of the manager
     * @param phone the phone number of the manager
     * @param password the password of the manager
     * @return newly updated Manager
      * @throws CustomException if to update the manager fail
     */
     @Transactional
     public Manager updateManager(String email,String name,String phone,String password){
        String error="";
        Manager ma=managerRepository.findManagerByEmail(email);
        if(managerRepository.findManagerByEmail(email)==null){
            error=error+"No manager with that email exists!";
        }else{
            error=error+HelperMethods.verifyEmail(email)+HelperMethods.verifyName(name)+HelperMethods.verifyPhone(phone)
                +HelperMethods.verifyPassword(password);
        }
        if(error.length()>0){
            throw new CustomException(error,HttpStatus.BAD_REQUEST);
        }else{
            ma.setName(name);
            ma.setPhone(phone);
            ma.setPassword(password);
            return ma;
        }
     }
}
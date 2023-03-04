package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;


public interface ServiceReqWithAccountRepository extends CrudRepository<ServiceReqWithAccount, Integer>{

    ServiceReqWithAccount findServiceReqWithAccountById(int id);
    List<ServiceReqWithAccount> findServiceReqWithAccountByIsAssigned(boolean isAssigned);
    List<ServiceReqWithAccount> findServiceReqWithAccountByService(Service service);
    List<ServiceReqWithAccount> findServiceReqWithAccountByCustomer(MonthlyCustomer monthlyCustomer);

}

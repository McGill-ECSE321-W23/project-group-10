package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.Services;


public interface ServiceReqWithAccountRepository extends CrudRepository<ServiceReqWithAccount, Integer> {

    //find a service request with account by id
    ServiceReqWithAccount findServiceReqWithAccountById(int id);

    //find service requests with account by checking whether been assigned
    List<ServiceReqWithAccount> findServiceReqWithAccountByIsAssigned(boolean isAssigned);

    //find service requests with account by service
    List<ServiceReqWithAccount> findServiceReqWithAccountByService(Services services);

    //find service requests with account by monthly customer
    List<ServiceReqWithAccount> findServiceReqWithAccountByCustomer(MonthlyCustomer monthlyCustomer);

}

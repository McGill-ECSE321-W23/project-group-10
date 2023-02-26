package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithoutAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;

public interface ServiceReqWithoutAccountRepository extends CrudRepository<ServiceReqWithoutAccount, Integer>{

    ServiceReqWithoutAccount findServiceReqWithoutAccountById(int id);
    List<ServiceReqWithoutAccount> findServiceReqWithoutAccountByIsAssigned(Boolean isAssigned);
    List<ServiceReqWithoutAccount> findServiceReqWithoutAccountByService(Service service);
    ServiceReqWithoutAccount findServiceReqWithoutAccountBylicenseNumber(String licenseNumber);

}

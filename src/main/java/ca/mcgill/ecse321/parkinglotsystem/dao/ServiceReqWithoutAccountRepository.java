package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithoutAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;

public interface ServiceReqWithoutAccountRepository extends ServiceRequestRepository {

    //find a service request without account by id
    ServiceReqWithoutAccount findServiceReqWithoutAccountById(int id);

    //find service requests without account by checking whether been assigned
    List<ServiceReqWithoutAccount> findServiceReqWithoutAccountByIsAssigned(Boolean isAssigned);

    //find service requests without account by service
    List<ServiceReqWithoutAccount> findServiceReqWithoutAccountByService(Service service);

    //find service requests without account by license number
    List<ServiceReqWithoutAccount> findServiceReqWithoutAccountByLicenseNumber(String licenseNumber);

}

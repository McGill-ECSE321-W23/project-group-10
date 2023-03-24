package ca.mcgill.ecse321.parkinglotsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceReqWithoutAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithoutAccount;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceRequest;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.toList;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;

@org.springframework.stereotype.Service
public class ServiceReqWithoutAccountService {
    @Autowired
    private ServiceReqWithoutAccountRepository serviceReqwithoutAccountRepository;
    @Autowired
    private ServicesService serviceService;

    @Transactional
    public ServiceReqWithoutAccount createServiceReqWithoutAccount(String licenseNumber, int price) {
        boolean flag = true;
        for (Service aService : serviceService.getServiceByPrice(price)) {
            if (aService.getPrice() == price) {
                flag = false;
            }
        }
        if (flag) {
            throw new CustomException("No service with such price", HttpStatus.BAD_REQUEST);
        }

        Service service = serviceService.getServiceByPrice(price).get(0);
        ServiceReqWithoutAccount serviceReqWithoutAccount = new ServiceReqWithoutAccount();
        serviceReqWithoutAccount.setIsAssigned(true);
        serviceReqWithoutAccount.setService(service);
        serviceReqWithoutAccount.setLicenseNumber(licenseNumber);
        ;
        serviceReqwithoutAccountRepository.save(serviceReqWithoutAccount);

        return serviceReqWithoutAccount;
    }

    @Transactional
    public ServiceReqWithoutAccount getServiceReqWithoutAccountById(int id) {
        if (serviceReqwithoutAccountRepository.findServiceReqWithoutAccountById(id) == null) {
            throw new CustomException("No serviceRequest Id", HttpStatus.BAD_REQUEST);
        }
        return serviceReqwithoutAccountRepository.findServiceReqWithoutAccountById(id);
    }

    @Transactional
    public List<ServiceReqWithoutAccount> getServiceReqWithoutAccountByIsAssigned(boolean isAssigned) {
        return serviceReqwithoutAccountRepository.findServiceReqWithoutAccountByIsAssigned(isAssigned);
    }

    // @Transactional
    // public List<ServiceReqWithoutAccount>
    // getServiceReqwithoutAccountByService(Service service){
    // if
    // (serviceReqwithoutAccountRepository.findServiceReqWithoutAccountByService(service).isEmpty()){
    // throw new CustomException("No serviceRequest with such service",
    // HttpStatus.BAD_REQUEST);
    // }
    // return
    // serviceReqwithoutAccountRepository.findServiceReqWithoutAccountByService(service);
    // }

    @Transactional
    public List<ServiceReqWithoutAccount> findServiceReqWithoutAccountByLicenseNumber(String licenseNumber) {
        if (serviceReqwithoutAccountRepository.findServiceReqWithoutAccountByLicenseNumber(licenseNumber).isEmpty()) {
            throw new CustomException("No serviceRequest with such service", HttpStatus.BAD_REQUEST);
        }
        return serviceReqwithoutAccountRepository.findServiceReqWithoutAccountByLicenseNumber(licenseNumber);
    }

    @Transactional
    public List<ServiceReqWithoutAccount> getAll() {
        return (List<ServiceReqWithoutAccount>) (ServiceRequest) toList(serviceReqwithoutAccountRepository.findAll());
    }

    @Transactional
    public ServiceReqWithoutAccount updateIsAssignedById(int id, boolean isAssigned) {
        ServiceReqWithoutAccount serviceReqwithoutAccount = serviceReqwithoutAccountRepository
                .findServiceReqWithoutAccountById(id);
        serviceReqwithoutAccount.setIsAssigned(isAssigned);
        serviceReqwithoutAccountRepository.save(serviceReqwithoutAccount);
        return serviceReqwithoutAccount;
    }

}

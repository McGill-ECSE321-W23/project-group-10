package ca.mcgill.ecse321.parkinglotsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceReqWithoutAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithoutAccount;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.toList;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;

@org.springframework.stereotype.Service
public class ServiceReqWithoutAccountService {
    @Autowired
    private ServiceReqWithoutAccountRepository serviceReqwithoutAccountRepository;
    @Autowired
    private ServicesService serviceService;

    /**
     * Method to create a service request without account.
     * @author Chenxin
     * @param licenseNumber the license number of service request without account
     * @param description the description of the service request without account
     * @return A ServiceReqWithoutAccount
     * @throws CustomException if to create the service request without account fail
     */
    @Transactional
    public ServiceReqWithoutAccount createServiceReqWithoutAccount(String licenseNumber, String description) {

        Service service = serviceService.getServiceByDescription(description);
        if (licenseNumber.equals("")) {
            throw new CustomException("License Number empty! ", HttpStatus.BAD_REQUEST);
        }

        ServiceReqWithoutAccount serviceReqWithoutAccount = new ServiceReqWithoutAccount();
        serviceReqWithoutAccount.setIsAssigned(false);
        serviceReqWithoutAccount.setService(service);
        serviceReqWithoutAccount.setLicenseNumber(licenseNumber);
        serviceReqwithoutAccountRepository.save(serviceReqWithoutAccount);
        return serviceReqWithoutAccount;
    }

    /**
     * Method to get a service request without account with id.
     * @author Chenxin
     * @param id the id of the service request without account
     * @return A serviceReqWithoutAccount
     * @throws CustomException if to get the service request without account fail
     */
    @Transactional
    public ServiceReqWithoutAccount getServiceReqWithoutAccountById(int id) {
        if (serviceReqwithoutAccountRepository.findServiceReqWithoutAccountById(id) == null) {
            throw new CustomException("No serviceRequest Id", HttpStatus.BAD_REQUEST);
        }
        return serviceReqwithoutAccountRepository.findServiceReqWithoutAccountById(id);
    }

    /**
     * Method to get the assigned ServiceReqWithoutAccount.
     * @author Chenxin
     * @param isAssigned evaluate to true if the service request without account is assigned
     * @return A List of ServiceReqWithoutAccount
     * @throws CustomException if to get the service request without account fail
     */
    @Transactional
    public List<ServiceReqWithoutAccount> getServiceReqWithoutAccountByIsAssigned(boolean isAssigned) {
        if (serviceReqwithoutAccountRepository.findServiceReqWithoutAccountByIsAssigned(isAssigned).size() <= 0) {
            throw new CustomException("No serviceRequest with such IsAssigned", HttpStatus.BAD_REQUEST);
        }
        return serviceReqwithoutAccountRepository.findServiceReqWithoutAccountByIsAssigned(isAssigned);
    }

    /**
     * Method to get service requests without account with license number.
     * @author Chenxin
     * @param licenseNumber the license number of the service request without account
     * @return A List of ServiceReqWithoutAccount
     * @throws CustomException if to get the service request without account fail
     */
    @Transactional
    public List<ServiceReqWithoutAccount> findServiceReqWithoutAccountByLicenseNumber(String licenseNumber) {
        if (serviceReqwithoutAccountRepository.findServiceReqWithoutAccountByLicenseNumber(licenseNumber).isEmpty()) {
            throw new CustomException("No serviceRequest with such service", HttpStatus.BAD_REQUEST);
        }
        return serviceReqwithoutAccountRepository.findServiceReqWithoutAccountByLicenseNumber(licenseNumber);
    }

    /**
     * Method to get all service requests without account.
     * @author Chenxin
     * @return A List of ServiceReqWithoutAccount.
     */
    @Transactional
    public List<ServiceReqWithoutAccount> getAll() {
        List<ServiceReqWithoutAccount> list = toList(serviceReqwithoutAccountRepository.findAll());
        return list;
    }

    /**
     * Method to update a service request without account of the given id
     * @author Chenxin
     * @param id the id of the service request without account
     * @param isAssigned evaluate to true if the service request without account is assigned
     * @return the updated ServiceReqWithoutAccount
     * @throws CustomException if to update the service request without account fail
     */
    @Transactional
    public ServiceReqWithoutAccount updateIsAssignedById(int id, boolean isAssigned) {
        ServiceReqWithoutAccount serviceReqwithoutAccount = serviceReqwithoutAccountRepository
                .findServiceReqWithoutAccountById(id);
        if (serviceReqwithoutAccount == null) {
            throw new CustomException("The ServiceReqWithoutAccount Id does not exist", HttpStatus.BAD_REQUEST);
        }
        serviceReqwithoutAccount.setIsAssigned(isAssigned);
        serviceReqwithoutAccountRepository.save(serviceReqwithoutAccount);
        return serviceReqwithoutAccount;
    }

}

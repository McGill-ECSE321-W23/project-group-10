package ca.mcgill.ecse321.parkinglotsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ServiceReqWithAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.ServiceReqWithAccount;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.toList;
import ca.mcgill.ecse321.parkinglotsystem.model.Service;

@org.springframework.stereotype.Service
public class ServiceReqWithAccountService {
    @Autowired
    private ServiceReqWithAccountRepository serviceReqWithAccountRepository;
    @Autowired
    private MonthlyCustomerService monthlyCustomerService;
    @Autowired
    private ServicesService serviceService;

    /**
     * Creates a ServiceReqWithAccount with the given monthly customer email and
     * description.
     * 
     * @param monthlyCustomerEmail the email of the monthly customer
     * @param description          description of the service
     * @return the new ServiceReqWithAccount
     */
    @Transactional
    public ServiceReqWithAccount createServiceReqWithAccount(String monthlyCustomerEmail, String description) {

        Service service = serviceService.getServiceByDescription(description);
        MonthlyCustomer monthlyCustomer = monthlyCustomerService.getMonthlyCustomerByEmail(monthlyCustomerEmail);
        ServiceReqWithAccount serviceReqWithAccount = new ServiceReqWithAccount();
        serviceReqWithAccount.setIsAssigned(true);
        serviceReqWithAccount.setService(service);
        serviceReqWithAccount.setCustomer(monthlyCustomer);
        serviceReqWithAccountRepository.save(serviceReqWithAccount);

        return serviceReqWithAccount;
    }

    /**
     * Gets a serviceReqWithAccount with the given ID.
     * 
     * @param id the ID of the serviceReqWithAccount
     * @return a serviceReqWithAccount
     */
    @Transactional
    public ServiceReqWithAccount getServiceReqWithAccountById(int id) {
        if (serviceReqWithAccountRepository.findServiceReqWithAccountById(id) == null) {
            throw new CustomException("No serviceRequest Id", HttpStatus.BAD_REQUEST);
        }
        return serviceReqWithAccountRepository.findServiceReqWithAccountById(id);
    }

    /**
     * Gets the assigned ServiceReqWithAccount.
     * 
     * @param isAssigned
     * @return the assigned ServiceReqWithAccount. Throws a CustomException if no
     *         assigned ServiceReqWithAccount is found.
     */
    @Transactional
    public List<ServiceReqWithAccount> getServiceReqWithAccountByIsAssigned(boolean isAssigned) {
        if (serviceReqWithAccountRepository.findServiceReqWithAccountByIsAssigned(isAssigned).size() <= 0) {
            throw new CustomException("No serviceRequest with such IsAssigned", HttpStatus.BAD_REQUEST);
        }
        return serviceReqWithAccountRepository.findServiceReqWithAccountByIsAssigned(isAssigned);
    }

    /**
     * Gets the ServiceReqWithAccount of the given monthly customer Email.
     * 
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return the assigned ServiceReqWithAccount. Throws a CustomException if no
     *         ServiceReqWithAccount is found.
     */
    @Transactional
    public List<ServiceReqWithAccount> getServiceReqWithAccountByCustomer(String monthlyCustomerEmail) {
        MonthlyCustomer monthlyCustomer = monthlyCustomerService.getMonthlyCustomerByEmail(monthlyCustomerEmail);
        if (serviceReqWithAccountRepository.findServiceReqWithAccountByCustomer(monthlyCustomer).isEmpty()) {
            throw new CustomException("No serviceRequest with this customer", HttpStatus.BAD_REQUEST);
        }
        return serviceReqWithAccountRepository.findServiceReqWithAccountByCustomer(monthlyCustomer);
    }

    /**
     * Gets all ServiceReqWithAccount.
     * 
     * @return the list of ServiceReqWithAccount.
     */
    @Transactional
    public List<ServiceReqWithAccount> getAll() {
        List<ServiceReqWithAccount> list = toList(serviceReqWithAccountRepository.findAll());
        return list;
    }

    /**
     * Updates the ServiceReqWithAccount of the given ID
     * update isAssigned.
     * 
     * @param id
     * @param isAssigned
     * @return the updated ServiceReqWithAccount
     */
    @Transactional
    public ServiceReqWithAccount updateIsAssignedById(int id, boolean isAssigned) {
        ServiceReqWithAccount serviceReqWithAccount = serviceReqWithAccountRepository.findServiceReqWithAccountById(id);
        if (serviceReqWithAccount == null) {
            throw new CustomException("The ServiceReqWithAccount Id does not exist", HttpStatus.BAD_REQUEST);
        }
        serviceReqWithAccount.setIsAssigned(isAssigned);
        serviceReqWithAccountRepository.save(serviceReqWithAccount);
        return serviceReqWithAccount;
    }

}

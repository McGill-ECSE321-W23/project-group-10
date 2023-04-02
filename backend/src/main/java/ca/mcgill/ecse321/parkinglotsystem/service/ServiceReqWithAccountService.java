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
     * Method to create a service request with account.
     * @author Chenxin
     * @param monthlyCustomerEmail the email of the monthly customer
     * @param description description of the service request with account
     * @return A ServiceReqWithAccount
     * @throws CustomException if to create the service request with account fail
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
     * Method to get a service request with account with the given id.
     * @author Chenxin
     * @param id the ID of the service request with account
     * @return A serviceReqWithAccount
     * @throws CustomException if to get the service request with account fail
     */
    @Transactional
    public ServiceReqWithAccount getServiceReqWithAccountById(int id) {
        if (serviceReqWithAccountRepository.findServiceReqWithAccountById(id) == null) {
            throw new CustomException("No serviceRequest Id", HttpStatus.BAD_REQUEST);
        }
        return serviceReqWithAccountRepository.findServiceReqWithAccountById(id);
    }

    /**
     * Method to get the assigned service requests with account.
     * @author Chenxin
     * @param isAssigned evaluate to true if the service request with account is assigned
     * @return A List of ServiceReqWithAccount
     * @throws CustomException if to get the service request with account fail
     */
    @Transactional
    public List<ServiceReqWithAccount> getServiceReqWithAccountByIsAssigned(boolean isAssigned) {
        if (serviceReqWithAccountRepository.findServiceReqWithAccountByIsAssigned(isAssigned).size() <= 0) {
            throw new CustomException("No serviceRequest with such IsAssigned", HttpStatus.BAD_REQUEST);
        }
        return serviceReqWithAccountRepository.findServiceReqWithAccountByIsAssigned(isAssigned);
    }

    /**
     * Method to get service requests with account by customer.
     * @author Chenxin
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return A List of ServiceReqWithAccount
     * @throws CustomException if to get the service request with account fail
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
     * Gets all service requests with account.
     * @author Chenxin
     * @return A List of ServiceReqWithAccount.
     */
    @Transactional
    public List<ServiceReqWithAccount> getAll() {
        List<ServiceReqWithAccount> list = toList(serviceReqWithAccountRepository.findAll());
        return list;
    }

    /**
     * Method to update a service request with account of the given id
     * @author Chenxin
     * @param id the id of the service request with account
     * @param isAssigned evaluate to true if the service request with account is assigned
     * @return the updated ServiceReqWithAccount
     * @throws CustomException if to update the service request with account fail
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

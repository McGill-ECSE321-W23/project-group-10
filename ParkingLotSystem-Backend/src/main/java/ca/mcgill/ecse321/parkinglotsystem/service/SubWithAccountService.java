package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.SubWithAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithAccount;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.toList;

@Service
public class SubWithAccountService {

    @Autowired
    private SubWithAccountRepository subWithAccountRepository;
    @Autowired
    private MonthlyCustomerService monthlyCustomerService;
    @Autowired
    private ParkingSpotService parkingSpotService;

    /**
     * Service method to create a subscription with an account with the given monthly customer and parking spot.
     * @author Marco
     * @param monthlyCustomerEmail the email of the monthly customer for whom to create the subscription
     * @param parkingSpotId the id of the parking spot to reserve
     * @return the new subscription
     */
    @Transactional
    public SubWithAccount createSubWithAccount(
        String monthlyCustomerEmail, int parkingSpotId) {
        
        // Data validation
        if (!(parkingSpotId >= 2000 && parkingSpotId < 3000)){
            throw new CustomException(
                "The parking spot is not available for monthly customers.", HttpStatus.BAD_REQUEST);
        }
        ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(parkingSpotId);
        for(SubWithAccount sub : subWithAccountRepository.findSubWithAccountByParkingSpot(parkingSpot)) {
            if (isActive(sub)) {
                throw new CustomException(
                    "The parking spot is currently reserved by another customer.", HttpStatus.BAD_REQUEST);
            }
        }
        MonthlyCustomer monthlyCustomer = monthlyCustomerService.getMonthlyCustomerByEmail(monthlyCustomerEmail);
        for(SubWithAccount sub : subWithAccountRepository.findSubWithAccountByCustomer(monthlyCustomer)) {
            if (isActive(sub)) {
                throw new CustomException(
                    "The monthly customer already has an active subscription.", HttpStatus.BAD_REQUEST);
            }
        }

        // Create & save new subscription
        Date date = new Date((new java.util.Date()).getTime());
        SubWithAccount subWithAccount = new SubWithAccount();
        subWithAccount.setDate(date);
        subWithAccount.setNbrMonths(1);
        subWithAccount.setParkingSpot(parkingSpot);
        subWithAccount.setCustomer(monthlyCustomer);
        subWithAccount = subWithAccountRepository.save(subWithAccount);

        return subWithAccount;
    }

    /**
     * Service method to get a subscription with the given ID.
     * @author Marco
     * @param id the id of the subscription
     * @return a subscription
     */
    @Transactional
    public SubWithAccount getSubWithAccount(int id) {

        var subWithAccount = subWithAccountRepository.findSubWithAccountById(id);
        if (subWithAccount == null) {
            throw new CustomException("The provided subscription ID is invalid.", HttpStatus.NOT_FOUND);
        }

        return subWithAccount;
    }

    /**
     * Service method to get the active subscription of the given monthly customer.
     * @author Marco
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return the active subscription. Throws a CustomException if no active subscription is found.
     */
    @Transactional
    public SubWithAccount getActiveByCustomer(String monthlyCustomerEmail) {

        List<SubWithAccount> subs = getAllByCustomer(monthlyCustomerEmail);
        if(subs.size() <= 0) {
            throw new CustomException("There is no active subscription", HttpStatus.NOT_FOUND);
        }
        SubWithAccount latestSub = subs.get(subs.size() - 1);
        if (!isActive(latestSub)) {
            throw new CustomException("There is no active subscription", HttpStatus.NOT_FOUND);
        }
        
        return latestSub;
    }

    /**
     * Service method to get the active subscription of the given parking spot.
     * @author Marco
     * @param parkingSpotId the ID of the parking spot
     * @return the active subscription. Throws a CustomException if no active subscription is found.
     */
    @Transactional
    public SubWithAccount getActiveByParkingSpot(int parkingSpotId) {

        List<SubWithAccount> subs = getAllByParkingSpot(parkingSpotId);
        if(subs.size() <= 0) {
            throw new CustomException("There is no active subscription", HttpStatus.NOT_FOUND);
        }
        SubWithAccount latestSub = subs.get(subs.size() - 1);
        if (!isActive(latestSub)) {
            throw new CustomException("There is no active subscription", HttpStatus.NOT_FOUND);
        }
        
        return latestSub;
    }

    /**
     * Service method to check is there is an active subscription with the given parking spot.
     * @param parkingSpotId the ID of the parking spot
     * @return true if there is an active subscription with the given parking spot.
     */
    @Transactional
    public boolean hasActiveByParkingSpot(int parkingSpotId) {

        List<SubWithAccount> subs = getAllByParkingSpot(parkingSpotId);
        if(subs.size() <= 0) {
            return false;
        }
        SubWithAccount latestSub = subs.get(subs.size() - 1);
        return isActive(latestSub);
    }

    /**
     * Service method to get all subscriptions of the given monthly customer.
     * @author Marco
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return the list of subscriptions sorted by date (ascending).
     */
    @Transactional
    public List<SubWithAccount> getAllByCustomer(String monthlyCustomerEmail) {

        MonthlyCustomer monthlyCustomer = monthlyCustomerService.getMonthlyCustomerByEmail(monthlyCustomerEmail);
        List<SubWithAccount> subs = toList(subWithAccountRepository.findSubWithAccountByCustomer(monthlyCustomer));
        Collections.sort(subs, Comparator.comparing(SubWithAccount::getDate));

        return subs;
    }

    /**
     * Service method to get all subscriptions of the given parking spot.
     * @author Marco
     * @param parkingSpotId the ID of the parking spot
     * @return the list of subscriptions sorted by date (ascending).
     */
    @Transactional
    public List<SubWithAccount> getAllByParkingSpot(int parkingSpotId) {

        ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(parkingSpotId);
        List<SubWithAccount> subs = toList(subWithAccountRepository.findSubWithAccountByParkingSpot(parkingSpot));
        Collections.sort(subs, Comparator.comparing(SubWithAccount::getDate));

        return subs;
    }

    /**
     * Service method to get all subscriptions.
     * @author Marco
     * @return the list of subscriptions sorted by date (ascending).
     */
    @Transactional
    public List<SubWithAccount> getAll() {

        List<SubWithAccount> subs = toList(subWithAccountRepository.findAll());
        Collections.sort(subs, Comparator.comparing(SubWithAccount::getDate));

        return subs;
    }

    /**
     * Service method to update the number of month of the given monthly customer
     * @author Marco
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return the updated subscription
     */
    @Transactional
    public SubWithAccount updateSubWithAccount(String monthlyCustomerEmail, int numberOfMonths) {
        SubWithAccount sub = getActiveByCustomer(monthlyCustomerEmail);
        sub.setNbrMonths(numberOfMonths);
        subWithAccountRepository.save(sub);
        return sub;
    }

    /**
     * Service method to delete the subscription with the given ID.
     * @author Marco
     * @param id the id of the subscription with an account
     * @throws CustomException if to delete the subscription with an account fail
     */
    public void deleteSubWithAccount(int id) {
        SubWithAccount sub = subWithAccountRepository.findSubWithAccountById(id);
        if(sub == null) {
            throw new CustomException("Invalid reservation ID.", HttpStatus.BAD_REQUEST);
        }
        subWithAccountRepository.deleteById(id);
    }

    /**
     * Checks whether the last day of the subscription is after the current day.
     * @author Marco
     * @param sub SubWithAccount
     * @return true if the subscription is still active.
     */
    private boolean isActive(SubWithAccount sub) {

        Date date = new Date((new java.util.Date()).getTime());
        Date lastSubDate = Date.valueOf(sub.getDate().toLocalDate().plusMonths(sub.getNbrMonths()).minusDays(1));
        return lastSubDate.after(date);
    }

}

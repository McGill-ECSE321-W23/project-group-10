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

    // TODO: Check if other service methods (MonthlyCustomer, ParkingSpot) perform input valdidation (IMPORTANT)

    /**
     * Creates a subscription with the given monthly customer and parking spot.
     * 
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
     * Gets a subcription with the given ID.
     * 
     * @param id the ID of the subscription
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
     * Gets the active subscription of the given monthly customer.
     * 
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
     * Gets the active subscription of the given parking spot.
     * 
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
     * Gets all subscriptions of the given monthly customer.
     * 
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
     * Gets all subscriptions of the given parking spot.
     * 
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
     * Gets all subcriptions.
     * 
     * @return the list of subscriptions sorted by date (ascending).
     */
    @Transactional
    public List<SubWithAccount> getAll() {

        List<SubWithAccount> subs = toList(subWithAccountRepository.findAll());
        Collections.sort(subs, Comparator.comparing(SubWithAccount::getDate));

        return subs;
    }

    /**
     * Updates the active subscription of the given monthly customer
     * by incrementing the number of months by one.
     * 
     * @param monthlyCustomerEmail the email of the monthly customer
     * @return the updated subscription
     */
    @Transactional
    public SubWithAccount updateSubWithAccount(String monthlyCustomerEmail) {

        SubWithAccount sub = getActiveByCustomer(monthlyCustomerEmail);
        sub.setNbrMonths(sub.getNbrMonths() + 1);
        subWithAccountRepository.save(sub);

        return sub;
    }

    /**
     * Checks whether the last day of the subscription is after the current day.
     * 
     * @param sub
     * @return true if the subscription is still active.
     */
    private boolean isActive(SubWithAccount sub) {

        Date date = new Date((new java.util.Date()).getTime());
        Date lastSubDate = Date.valueOf(sub.getDate().toLocalDate().plusMonths(sub.getNbrMonths()).minusDays(1));
        return lastSubDate.after(date);
    }

}

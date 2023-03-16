package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.MonthlyCustomerRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.SubWithAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithAccount;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.toList;

@Service
public class SubWithAccountService {

    @Autowired
    private SubWithAccountRepository subWithAccountRepository;
    @Autowired
    private MonthlyCustomerRepository monthlyCustomerRepository;
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    // TODO: Use service methods to get monthly customers & parking spot

    /**
     * Creates a subscription with the given monthly customer account and parking spot.
     * 
     * @param monthlyCustomerEmail the email of the monthly customer for whom to create the subscription
     * @param parkingSpotId the id of the parking spot to reserve
     * @return a DTO representing the new subscription.
     */
    @Transactional
    public SubWithAccount createSubWithAccount(
        String monthlyCustomerEmail, int parkingSpotId) {
        
        // Get monthly customer
        var monthlyCustomer = monthlyCustomerRepository.findMonthlyCustomerByEmail(monthlyCustomerEmail);
        if (monthlyCustomer == null) {
            throw new IllegalArgumentException("The provided monthly customer email is invalid.");
        }

        // Get parking spot
        var parkingSpot = parkingSpotRepository.findParkingSpotById(parkingSpotId);
        if (parkingSpot == null) {
            throw new IllegalArgumentException("The provided parking spot ID is invalid.");
        }
        if (!(parkingSpotId >= 2000 && parkingSpotId < 3000)){
            throw new IllegalArgumentException("The parking spot is not available for monthly customers");
        }

        // Check whether an active subscription exists
        for(SubWithAccount sub : subWithAccountRepository.findSubWithAccountByCustomer(monthlyCustomer)) {
            if (isActive(sub)) {
                throw new IllegalArgumentException("The monthly customer already has an active subscription.");
            }
        }

        // Create & save new subscription
        Date date = new Date((new java.util.Date()).getTime());
        SubWithAccount subWithAccount = new SubWithAccount();
        subWithAccount.setDate(date);
        subWithAccount.setNbrMonths(1);
        subWithAccount.setParkingSpot(parkingSpot);
        subWithAccount.setCustomer(monthlyCustomer);
        subWithAccountRepository.save(subWithAccount);

        return subWithAccount;
    }

    @Transactional
    public SubWithAccount getSubWithAccount(int id) {

        var subWithAccount = subWithAccountRepository.findSubWithAccountById(id);
        if (subWithAccount == null) {
            throw new IllegalArgumentException("The provided subscription ID is invalid.");
        }
        return subWithAccount;
    }

    @Transactional
    public SubWithAccount getActiveSubWithAccount(String monthlyCustomerEmail) {

        // Get list of subscriptions
        List<SubWithAccount> subs = getAll(monthlyCustomerEmail);

        // Get current sbscription
        SubWithAccount latestSub = subs.get(subs.size() - 1);
        if (!isActive(latestSub)) {
            throw new IllegalArgumentException("There is no active subscription");
        }

        return latestSub;
    }

    @Transactional
    public List<SubWithAccount> getAll(String monthlyCustomerEmail) {

        // Get monthly customer
        var monthlyCustomer = monthlyCustomerRepository.findMonthlyCustomerByEmail(monthlyCustomerEmail);
        if (monthlyCustomer == null) {
            throw new IllegalArgumentException("The provided monthly customer email is invalid.");
        }

        // Get sorted list of subscriptions (from oldest to most recent)
        List<SubWithAccount> subs = toList(subWithAccountRepository.findSubWithAccountByCustomer(monthlyCustomer));
        if (subs.size() <= 0) {
            throw new IllegalArgumentException("No subscription found");
        }
        Collections.sort(subs, Comparator.comparing(SubWithAccount::getDate));

        return subs;
    }

    @Transactional
    public List<SubWithAccount> getAll() {

        // Get list of SubWithAccounts
        List<SubWithAccount> subs = toList(subWithAccountRepository.findAll());
        if (subs.size() <= 0) {
            throw new IllegalArgumentException("No subscription found");
        }

        return subs;
    }

    @Transactional
    public SubWithAccount updateSubWithAccount(String monthlyCustomerEmail) {

        // Get subscription
        SubWithAccount sub = getActiveSubWithAccount(monthlyCustomerEmail);

        // Update subscription
        sub.setNbrMonths(sub.getNbrMonths() + 1);
        subWithAccountRepository.save(sub);

        return sub;
    }

    /**
     * Checks whether the last day of the subscription is after the current day.
     * 
     * @param sub
     * @return true if the subscription is still active
     */
    private boolean isActive(SubWithAccount sub) {

        Date date = new Date((new java.util.Date()).getTime());
        Date lastSubDate = Date.valueOf(
            sub.getDate().toLocalDate().plusMonths(sub.getNbrMonths()).minusDays(1));
        return lastSubDate.after(date);
    }

}

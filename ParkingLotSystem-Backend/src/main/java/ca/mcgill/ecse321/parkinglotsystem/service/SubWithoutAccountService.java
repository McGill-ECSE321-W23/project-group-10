package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.SubWithoutAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;

@Service
public class SubWithoutAccountService extends ReservationService {

    @Autowired
    private SubWithoutAccountRepository subWithoutAccountRepository;

    @Autowired
    private ParkingSpotService parkingSpotService;
    /**
     * Method to create a subscription without an account.
     * @author Mike Zhang
     * @param parkingSpotId the parking spot id of the subscription without an account
     * @param licenseNumber the license number of the subscription without an account
     * @return A SubWithoutAccount
     * @throws CustomException if to create the subscription without an account fail
     */
    @Transactional
    public SubWithoutAccount createSubWithoutAccount(String licenseNumber, int parkingSpotId) {

        if (!(parkingSpotId >= 2000 && parkingSpotId < 4000)){
            throw new CustomException(
                "The parking spot is not available for monthly customers.", HttpStatus.BAD_REQUEST);
        }
        if(licenseNumber == null || licenseNumber.length() == 0){
            throw new CustomException("licenseNumber cannot be empty", HttpStatus.BAD_REQUEST);
        }
        else if(!licenseNumber.matches("^[a-zA-Z0-9]*$") || licenseNumber.length() > 7){
            throw new CustomException("Incorrect licenseNumber format", HttpStatus.BAD_REQUEST);
        }
        ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(parkingSpotId);
        for(SubWithoutAccount subWithoutAccount : subWithoutAccountRepository.findSubWithoutAccountsByParkingSpot(parkingSpot)) {
            if (isActive(subWithoutAccount)) {
                throw new CustomException(
                    "The parking spot is currently reserved by another customer.", HttpStatus.BAD_REQUEST);
            }
        }
        for(SubWithoutAccount subWithoutAccount : subWithoutAccountRepository.findSubWithoutAccountsByLicenseNumber(licenseNumber)) {
            if (isActive(subWithoutAccount)) {
                throw new CustomException(
                    "The license number already has an active subscription.", HttpStatus.BAD_REQUEST);
            }
        }
            Date date = new Date((new java.util.Date().getTime()));
            SubWithoutAccount subWithoutAccount = new SubWithoutAccount(); 
            subWithoutAccount.setDate(date);
            subWithoutAccount.setLicenseNumber(licenseNumber);
            subWithoutAccount.setNbrMonths(1);
            subWithoutAccount.setParkingSpot(parkingSpotService.getParkingSpotById(parkingSpotId));
            subWithoutAccountRepository.save(subWithoutAccount);
            return subWithoutAccount;
        
    }

    /**
     * Method to get a subscription without an account by id.
     * @author Mike Zhang
     * @param reservationId the reservation id of the subscription without an account
     * @return A SubWithoutAccount
     * @throws CustomException if to get the subscription without an account fail
     */
    @Transactional
    public SubWithoutAccount getSubWithoutAccountById(int reservationId){
        SubWithoutAccount subWithoutAccount = subWithoutAccountRepository.findSubWithoutAccountById(reservationId);
        if (subWithoutAccount == null) {
            throw new CustomException("SubWithoutAccount not found", HttpStatus.NOT_FOUND);
        }
        return subWithoutAccount;
    }

    /**
     * Method to get subscriptions without an account by date.
     * @author Mike Zhang
     * @param date the date of the subscription without an account
     * @return A List of SubWithoutAccount
     * @throws CustomException if to get the subscription without an account fail
     */
	@Transactional
	public List<SubWithoutAccount> getSubWithoutAccountsByDate(Date date) {
		List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountRepository.findSubWithoutAccountsByDate(date);
		return subWithoutAccounts;
	}

    /**
     * Method to get subscriptions without an account by parking spot.
     * @author Mike Zhang
     * @param parkingSpotId the parking spot id of the subscription without an account
     * @return A List of SubWithoutAccount
     * @throws CustomException if to get the subscription without an account fail
     */
	@Transactional
	public List<SubWithoutAccount> getSubWithoutAccountsByParkingSpot(int parkingSpotId) {
        ParkingSpot spot = parkingSpotService.getParkingSpotById(parkingSpotId);
		List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountRepository.findSubWithoutAccountsByParkingSpot(spot);
        Collections.sort(subWithoutAccounts, Comparator.comparing(SubWithoutAccount::getDate));
		return subWithoutAccounts;
	}

    /**
     * Method to get subscriptions without an account by license number.
     * @author Mike Zhang
     * @param licenseNumber the license number of the subscription without an account
     * @return A List of SubWithoutAccount
     * @throws CustomException if to get the subscription without an account fail
     */
	@Transactional
	public List<SubWithoutAccount> getSubWithoutAccountsByLicenseNumber(String licenseNumber) {
		List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountRepository.findSubWithoutAccountsByLicenseNumber(licenseNumber);
        if (subWithoutAccounts == null) {
            return Collections.emptyList();
        }
        Collections.sort(subWithoutAccounts, Comparator.comparing(SubWithoutAccount::getDate));
		return subWithoutAccounts;
	}

    /**
     * Method to get all subscriptions without an account.
     * @author Mike Zhang
     * @return A List of SubWithoutAccount
     * @throws CustomException if to get the subscription without an account fail
     */
	@Transactional
	public List<SubWithoutAccount> getAllSubWithoutAccounts() {
		return toList(subWithoutAccountRepository.findAll());
	}

    @Transactional
    public SubWithoutAccount getActiveByLicenseNumber(String licenseNumber) {

        List<SubWithoutAccount> subWithoutAccounts = getSubWithoutAccountsByLicenseNumber(licenseNumber);
        if(subWithoutAccounts.size() <= 0) {
            throw new CustomException("There is no active subscription with this License number", HttpStatus.NOT_FOUND);
        }
        SubWithoutAccount latestSubWithoutAccount = subWithoutAccounts.get(subWithoutAccounts.size() - 1);
        if (!isActive(latestSubWithoutAccount)) {
            throw new CustomException("There is no active subscription with this License number", HttpStatus.NOT_FOUND);
        }

        return latestSubWithoutAccount;
    }

    /**
     * Method to update a subscription without an account by license number
     * @author Mike Zhang
     * @param licenseNumber the license number of the subscription without an account
     * @return the updated SubWithoutAccount
     * @throws CustomException if to update the subscription without an account fail
     */
    @Transactional
    public SubWithoutAccount updateSubWithoutAccount(String licenseNumber){
        if(licenseNumber == null || licenseNumber.length() == 0){
            throw new CustomException("licenseNumber cannot be empty", HttpStatus.BAD_REQUEST);
        }
        else if(!licenseNumber.matches("^[a-zA-Z0-9]*$") || licenseNumber.length() > 7){
            throw new CustomException("Incorrect licenseNumber format", HttpStatus.BAD_REQUEST);
        }
        SubWithoutAccount subWithoutAccount = getActiveByLicenseNumber(licenseNumber);
        if (subWithoutAccount == null) {
            throw new CustomException("There is no active subscription with this License number", HttpStatus.NOT_FOUND);
        }
        subWithoutAccount.setNbrMonths(subWithoutAccount.getNbrMonths() + 1);
            subWithoutAccountRepository.save(subWithoutAccount);
            return subWithoutAccount;
        

    }

    /**
     * Method to delete a subscription without an account by reservation id
     * @author Mike Zhang
     * @param reservationId the reservation id of the subscription without an account
     * @return the deleted SubWithoutAccount
     * @throws CustomException if to delete the subscription without an account fail
     */
     public SubWithoutAccount deleteSubWithoutAccount(int reservationId){
        if (reservationId < 0){
            throw new CustomException("ReservationId cannot be negative.", HttpStatus.BAD_REQUEST);
        }

        SubWithoutAccount subWithoutAccount = subWithoutAccountRepository.findSubWithoutAccountById(reservationId);
        if(subWithoutAccount == null){
            throw new CustomException("Reservation does not exist.", HttpStatus.BAD_REQUEST);
        }
        subWithoutAccountRepository.delete(subWithoutAccount);
        return subWithoutAccount;

    }

    /**
	 * Delete all subscriptions without an account.
     * @author Mike
	 * @return A List of SubWithoutAccount
	 */
	@Transactional
	public List<SubWithoutAccount> deleteAllSubWithoutAccounts() {
		Iterable<SubWithoutAccount> subWithoutAccounts = subWithoutAccountRepository.findAll();
		subWithoutAccountRepository.deleteAll();
		return toList(subWithoutAccounts);
	}

    /**
     * Checks whether the last day of the subscription is after the current day.
     * @author Mike
     * @param subWithoutAccount the input subscription without an account
     * @return true if the subscription is still active.
     */
    private boolean isActive(SubWithoutAccount subWithoutAccount) {
        Date date = Date.valueOf(LocalDate.now());
        Date lastSubDate = Date.valueOf(subWithoutAccount.getDate().toLocalDate().plusMonths(subWithoutAccount.getNbrMonths()).minusDays(1));
        return lastSubDate.after(date);
    }
    /**
	 * Helper method that converts iterable to list
	 * @param <T> input
	 * @param iterable iterable
	 * @return A List
	 */
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
    
}
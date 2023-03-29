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

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotsystem.dao.ReservationRepository;
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
     * Create a Reservation
     * @author Mike Zhang
     * @params reservationId
     * @params date
     * return a reservation created
     */
    @Transactional
    public SubWithoutAccount createSubWithoutAccount(String licenseNumber, int parkingSpotId) {

        if (!(parkingSpotId >= 2000 && parkingSpotId < 3000)){
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
    @Transactional
    public SubWithoutAccount getSubWithoutAccountById(int reservationId){
        SubWithoutAccount subWithoutAccount = subWithoutAccountRepository.findSubWithoutAccountById(reservationId);
        if (subWithoutAccount == null) {
            throw new CustomException("SubWithoutAccount not found", HttpStatus.NOT_FOUND);
        }
        return subWithoutAccount;
    }
	
/**
	 * Find reservations by date
	 * @author Mike Zhang
	 * @param date
	 * @return a list of reservations
	 */
	@Transactional
	public List<SubWithoutAccount> getSubWithoutAccountsByDate(Date date) {
		List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountRepository.findSubWithoutAccountsByDate(date);
		return subWithoutAccounts;
	}

    /**
	 * Find reservations by parkingSpot
	 * @author Mike Zhang
	 * @param parkingSpot
	 * @return a list of reservations
	 */
	@Transactional
	public List<SubWithoutAccount> getSubWithoutAccountsByParkingSpot(int parkingSpotId) {
        ParkingSpot spot = parkingSpotService.getParkingSpotById(parkingSpotId);
		List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountRepository.findSubWithoutAccountsByParkingSpot(spot);
        Collections.sort(subWithoutAccounts, Comparator.comparing(SubWithoutAccount::getDate));
		return subWithoutAccounts;
	}

    /**
	 * Find reservations by licenseNumber
	 * @author Mike Zhang
	 * @param parkingSpot
	 * @return a list of reservations
	 */
	@Transactional
	public List<SubWithoutAccount> getSubWithoutAccountsByLicenseNumber(String licenseNumber) {
		List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountRepository.findSubWithoutAccountsByLicenseNumber(licenseNumber);
        Collections.sort(subWithoutAccounts, Comparator.comparing(SubWithoutAccount::getDate));
		return subWithoutAccounts;
	}
    /**
	 * Find all reservations
	 * @author Mike
	 * @return List of all accounts
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

    @Transactional
    public SubWithoutAccount getActiveByParkingSpot(int parkingSpotId) {

        List<SubWithoutAccount> subWithoutAccounts = getSubWithoutAccountsByParkingSpot(parkingSpotId);
        if(subWithoutAccounts.size() <= 0) {
            throw new CustomException("There is no active subscription", HttpStatus.NOT_FOUND);
        }
        SubWithoutAccount latestSubWithoutAccount = subWithoutAccounts.get(subWithoutAccounts.size() - 1);
        if (!isActive(latestSubWithoutAccount)) {
            throw new CustomException("There is no active subscription", HttpStatus.NOT_FOUND);
        }

        return latestSubWithoutAccount;
    }

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
     * Delete a reservation
     * @author Mike
     * @param reservationId
     * @return reservation deleted
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
	 * Delete all the reservations
	 * @return
	 */
	@Transactional
	public List<SubWithoutAccount> deleteAllSubWithoutAccounts() {
		Iterable<SubWithoutAccount> subWithoutAccounts = subWithoutAccountRepository.findAll();
		subWithoutAccountRepository.deleteAll();
		return toList(subWithoutAccounts);
	}

    /**
     * Checks whether the last day of the subscription is after the current day.
     * 
     * @param sub
     * @return true if the subscription is still active.
     */
    private boolean isActive(SubWithoutAccount subWithoutAccount) {

        Date date = Date.valueOf(LocalDate.now());
        Date lastSubDate = Date.valueOf(subWithoutAccount.getDate().toLocalDate().plusMonths(subWithoutAccount.getNbrMonths()).minusDays(1));
        return lastSubDate.after(date);
    }
    /**
	 * helper method that converts iterable to list
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
    
}
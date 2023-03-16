package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.SubWithoutAccountRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;

@Service
public class SubWithoutAccountService extends ReservationService {

    @Autowired
    private SubWithoutAccountRepository subWithoutAccountRepository;
    /**
     * Create a Reservation
     * @author Mike Zhang
     * @params reservationId
     * @params date
     * return a reservation created
     */
    @Transactional
    public SubWithoutAccount createSubWithoutAccount(int reservationId, Date date, String licenseNumber, int nbrMonths, ParkingSpot parkingspot) {
        if (reservationId < 0){
            throw new IllegalArgumentException("ReservationId cannot be negative.");
        }
        else if(reservationRepository.findReservationById(reservationId) != null){
            throw new IllegalArgumentException("ReservationId is in use.");
        }
        else if(date == null){
            throw new IllegalArgumentException("date cannot be empty.");
        }
        else if(licenseNumber == null || licenseNumber.length() == 0){
            throw new IllegalArgumentException("licenseNumber cannot be empty");
        }
        else if(licenseNumber.matches("^[a-zA-Z0-9]*$") || licenseNumber.length() > 7){
            throw new IllegalArgumentException("Incorrect licenseNumber format");
        }
        else {
            SubWithoutAccount subWithoutAccount = new SubWithoutAccount();
            subWithoutAccount.setId(reservationId); 
            subWithoutAccount.setDate(date);
            subWithoutAccount.setLicenseNumber(licenseNumber);
            subWithoutAccount.setNbrMonths(nbrMonths);
            subWithoutAccount.setParkingSpot(parkingspot);
            subWithoutAccountRepository.save(subWithoutAccount);
            return subWithoutAccount;
        }
    }
    @Transactional
    public SubWithoutAccount getSubWithoutAccountById(int reservationId){
        SubWithoutAccount subWithoutAccount = subWithoutAccountRepository.findSubWithoutAccountById(reservationId);
        return subWithoutAccount;
    }
	
/**
	 * Find reservations by date
	 * @author Mike Zhang
	 * @param date
	 * @return a list of reservations
	 */
	@Transactional
	public List<SubWithoutAccount> getSubWithoutAccountByDate(Date date) {
		List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountRepository.findSubWithoutAccountbyDate(date);
		return subWithoutAccounts;
	}

    /**
	 * Find reservations by parkingSpot
	 * @author Mike Zhang
	 * @param parkingSpot
	 * @return a list of reservations
	 */
	@Transactional
	public List<SubWithoutAccount> getSubWithoutAccountsByLicenseNumber(ParkingSpot parkingSpot) {
		List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountRepository.findSubWithoutAccountsByParkingSpot(parkingSpot);
		return subWithoutAccounts;
	}

    /**
	 * Find reservations by licenseNumber
	 * @author Mike Zhang
	 * @param parkingSpot
	 * @return a list of reservations
	 */
	@Transactional
	public List<SubWithoutAccount> getSubWithoutAccountssByLicenseNumber(String licenseNumber) {
		List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountRepository.findSubWithoutAccountsByLicenseNumber(licenseNumber);
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

    /**
     * Delete a reservation
     * @author Mike
     * @param reservationId
     * @return reservation deleted
     */

     public SubWithoutAccount deleteSubWithoutAccount(int reservationId){
        if (reservationId < 0){
            throw new IllegalArgumentException("ReservationId cannot be negative.");
        }
        else if(reservationRepository.findReservationById(reservationId) == null){
            throw new IllegalArgumentException("reservationId does not exist.");
        }

        SubWithoutAccount subWithoutAccount = subWithoutAccountRepository.findSubWithoutAccountById(reservationId);
        if(subWithoutAccount == null){
            throw new IllegalArgumentException("Reservation does not exist.");
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
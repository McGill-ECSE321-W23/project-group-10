
package ca.mcgill.ecse321.parkinglotsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.service.SubWithoutAccountService;
import ca.mcgill.ecse321.parkinglotsystem.service.AuthenticationService;
import ca.mcgill.ecse321.parkinglotsystem.dto.*;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/sub-without-account")
public class SubWithoutAccountController {
    @Autowired
    private SubWithoutAccountService subWithoutAccountService;
    @Autowired
    private AuthenticationService authService;

    /** 
     * method to get all the existing SubwithoutAccounts
     * @author Mike
     * @return list of all the existing SubwithoutAccounts as Dtos
     */
    @GetMapping(value = {"", "/"})
    public List<SubWithoutAccountDto> getAllSubWithoutAccounts() {
        List<SubWithoutAccountDto> subWithoutAccountDtos = new ArrayList<SubWithoutAccountDto>();
        List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountService.getAllSubWithoutAccounts();
        for (SubWithoutAccount swO : subWithoutAccounts){
            subWithoutAccountDtos.add(convertToDto(swO));
        }
        return subWithoutAccountDtos;

    }
    /** 
     * method to create an instance of subWithoutAccount
     * @author Mike
     * @param licenseNumber - the license number linked to the SubWithoutAccount
     * @param parkingSpotId - the id of the parking spot linked to the SubWithoutAccount
     * @return the subWithoutAccount created as Dtos
     */
    @PostMapping(value = {"", "/"})
    public SubWithoutAccountDto createSubWithoutAccount(@RequestParam(name = "licenseNumber") String licenseNumber, @RequestParam(name = "parkingSpotId") int parkingSpotId){
        SubWithoutAccount subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(licenseNumber, parkingSpotId);
        return convertToDto(subWithoutAccount);
    }

    /** 
     * method to get a list of Subscription without account with a parkingspot
     * @author Mike
     * @param parkingSpotId - the id of the parking spot linked to the SubWithoutAccounts
     * @return list of SubWithoutAccounts found with the given parking spot id as Dtos
     */
    @GetMapping(value = { "/all-by-parking-spot/{parkingSpot}", "/all-by-parking-spot/{parkingSpot}/"})
    public List<SubWithoutAccountDto> getSubWithoutAccountsByParkingSpot(@PathVariable("parkingSpotId") int parkingSpotId){
        List<SubWithoutAccountDto> subWithoutAccountDtos = new ArrayList<SubWithoutAccountDto>();
        List<SubWithoutAccount> reservations = subWithoutAccountService.getSubWithoutAccountsByParkingSpot(parkingSpotId);
        for (Reservation r : reservations){
           subWithoutAccountDtos.add((SubWithoutAccountDto) convertToDto(r));
        }
        return subWithoutAccountDtos;
    }

    /** 
     * method to get a list of Subscription without account with a date
     * @author Mike
     * @param date - the date linked to the SubWithoutAccounts
     * @return list of the SubWithoutAccounts found with the given date as Dtos
     */
    @GetMapping(value = { "/all-by-date/{date}", "/all-by-date/{date}/"})
    public List<SubWithoutAccountDto> getSubWithoutAccountsByDate(@PathVariable Date date){
        List<SubWithoutAccountDto> subWithoutAccountDtos = new ArrayList<SubWithoutAccountDto>();
        List<SubWithoutAccount> reservations = subWithoutAccountService.getSubWithoutAccountsByDate(date);
        for (Reservation r : reservations){
           subWithoutAccountDtos.add((SubWithoutAccountDto) convertToDto(r));
        }
        return subWithoutAccountDtos;
    }

    /** 
     * method to get a subscription without account with its id
     * @author Mike
     * @param id - the id of the SubWithoutAccount
     * @return the SubWithoutAccount found with the given id as Dtos
     */
    @GetMapping(value = { "/{id}", "/{id}/"})
    public SubWithoutAccountDto getSubWithoutAccountById(@PathVariable int id){
        
        SubWithoutAccount subWithoutAccount = subWithoutAccountService.getSubWithoutAccountById(id);
        
        return convertToDto(subWithoutAccount);
    }

    /** 
     * method to get a list of Subscription without account with a licenseNumber
     * @author Mike
     * @param licenseNumber - the licenseNumber linked to the SubWithoutAccounts
     * @return list of SubWithoutAccounts found with the given licenseNumber as Dtos
     */
    @GetMapping(value = { "/all-by-license-number/{licenseNumber}", "/all-by-license-number/{licenseNumber}/"})
    public List<SubWithoutAccountDto> getSubWithoutAccountsByLicenseNumber(@PathVariable String licenseNumber){
        List<SubWithoutAccountDto> subWithoutAccountDtos = new ArrayList<SubWithoutAccountDto>();
        List<SubWithoutAccount> reservations = subWithoutAccountService.getSubWithoutAccountsByLicenseNumber(licenseNumber);
        for (Reservation r : reservations){
           subWithoutAccountDtos.add((SubWithoutAccountDto) convertToDto(r));
        }
        return subWithoutAccountDtos;
    }

    @PutMapping(value = { "/{licenseNumber}", "/{licenseNumber}/" })
	public SubWithoutAccountDto updateSubWithoutAccountDto(@PathVariable String licenseNumber) {
		SubWithoutAccount subWithoutAccount = subWithoutAccountService.updateSubWithoutAccount(licenseNumber);
		return convertToDto(subWithoutAccount);
	}

    /** 
     * method to delete SubWithoutAccount with its id
     * @author Mike
     * @param id - the id linked to the SubWithoutAccounts
     * @param token - the token to authenticate manager status
     * @return the SubWithoutAccount deleted as Dto
     */
    @DeleteMapping(value = {"/{id}", "/{id}/"})
    public SubWithoutAccountDto deleteSubWithoutAccountDto(@PathVariable("id") int id, @RequestHeader String token){
        authService.authenticateManager(token);
        SubWithoutAccount sR = subWithoutAccountService.deleteSubWithoutAccount(id);
        return convertToDto(sR);
    }

    /** 
     * method to convert Reservation to Dto
     * @author Mike
     * @param reservation 
     * @return reservation dto
     */
    private ReservationDto convertToDto(Reservation reservation) {
	    ParkingSpotDto pDto = convertToDto(reservation.getParkingSpot());
        return new ReservationDto(reservation.getId(), reservation.getDate(), pDto);
    }

    /** 
     * method to convert SubWithoutAccount to Dto
     * @author Mike
     * @param subWithoutAccount 
     * @return subWithoutAccount dto
     */
    private SubWithoutAccountDto convertToDto(SubWithoutAccount subWithoutAccount) {
        ParkingSpotDto pDto = convertToDto(subWithoutAccount.getParkingSpot());
        
        return new SubWithoutAccountDto(subWithoutAccount.getId(), subWithoutAccount.getDate(), subWithoutAccount.getLicenseNumber(), subWithoutAccount.getNbrMonths(), pDto);
    }

    /** 
     * method to convert parkingSpot to Dto
     * @author Mike
     * @param spot - ParkingSpot 
     * @return parkingSpot dto
     */
    private ParkingSpotDto convertToDto(ParkingSpot spot){
        return new ParkingSpotDto(spot.getId(), convertToDto(spot.getType()));
    }
    
    /** 
     * method to convert ParkingSpotType to Dto
     * @author Mike
     * @param type - parkingSpotType 
     * @return parkingSpotType dto
     */
    private ParkingSpotTypeDto convertToDto(ParkingSpotType type){
        return new ParkingSpotTypeDto(type.getName(), type.getFee());
    }
}

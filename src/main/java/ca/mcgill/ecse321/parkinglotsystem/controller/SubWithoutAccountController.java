package ca.mcgill.ecse321.parkinglotsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotsystem.service.SubWithoutAccountService;
import ca.mcgill.ecse321.parkinglotsystem.service.ParkingSpotService;
import ca.mcgill.ecse321.parkinglotsystem.dto.*;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpotType;
import ca.mcgill.ecse321.parkinglotsystem.model.Reservation;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/sub-without-account/")
public class SubWithoutAccountController {
    @Autowired
    private SubWithoutAccountService subWithoutAccountService;
    @Autowired
    private ParkingSpotService ParkingSpotService;

    @GetMapping(value = {"/all", "/all/"})
    public List<SubWithoutAccountDto> getAllSubWithoutAccounts() {
        List<SubWithoutAccountDto> subWithoutAccountDtos = new ArrayList<SubWithoutAccountDto>();
        List<SubWithoutAccount> subWithoutAccounts = subWithoutAccountService.getAllSubWithoutAccounts();
        for (SubWithoutAccount swO : subWithoutAccounts){
            subWithoutAccountDtos.add(convertToDto(swO));
        }
        return subWithoutAccountDtos;

    }

    @PostMapping(value = {"", "/" })
    public SubWithoutAccountDto createSubWithoutAccount(@RequestParam(name = "id") int id, @RequestParam(name = "date") Date date, @RequestParam(name = "licenseNumber") String licenseNumber, @RequestParam(name = "nbrMonths") int nbrMonths, @RequestParam(name = "parkingSpotId") int parkingSpotId){
        SubWithoutAccount subWithoutAccount = subWithoutAccountService.createSubWithoutAccount(id, date, licenseNumber, nbrMonths, parkingSpotId);
        return convertToDto(subWithoutAccount);
    }

    @GetMapping(value = { "/all-by-parking-spot/{parkingSpot}", "/all-by-parking-spot/{parkingSpot}/"})
    public List<SubWithoutAccountDto> getSubWithoutAccountsByParkingSpot(@PathVariable("parkingSpotId") int parkingSpotId){
        List<SubWithoutAccountDto> subWithoutAccountDtos = new ArrayList<SubWithoutAccountDto>();
        List<Reservation> reservations = subWithoutAccountService.getReservationsByParkingSpot(parkingSpotId);
        for (Reservation r : reservations){
           subWithoutAccountDtos.add((SubWithoutAccountDto) convertToDto(r));
        }
        return subWithoutAccountDtos;
    }

    @GetMapping(value = { "/all-by-date/{date}", "/all-by-date/{date}/"})
    public List<SubWithoutAccountDto> getSubWithoutAccountsByDate(@PathVariable Date date){
        List<SubWithoutAccountDto> subWithoutAccountDtos = new ArrayList<SubWithoutAccountDto>();
        List<Reservation> reservations = subWithoutAccountService.getReservationsByDate(date);
        for (Reservation r : reservations){
           subWithoutAccountDtos.add((SubWithoutAccountDto) convertToDto(r));
        }
        return subWithoutAccountDtos;
    }

    @GetMapping(value = { "/by-id/{id}", "/by-id/{id}/"})
    public SubWithoutAccountDto getSubWithoutAccountById(@PathVariable int id){
        
        SubWithoutAccount subWithoutAccount = subWithoutAccountService.getSubWithoutAccountById(id);
        
        return convertToDto(subWithoutAccount);
    }

    @GetMapping(value = { "/all-by-license-number/{licenseNumber}", "/all-by-license-number/{licenseNumber}/"})
    public List<SubWithoutAccountDto> getSubWithoutAccountsByLicenseNumber(@PathVariable String licenseNumber){
        List<SubWithoutAccountDto> subWithoutAccountDtos = new ArrayList<SubWithoutAccountDto>();
        List<SubWithoutAccount> reservations = subWithoutAccountService.getSubWithoutAccountsByLicenseNumber(licenseNumber);
        for (Reservation r : reservations){
           subWithoutAccountDtos.add((SubWithoutAccountDto) convertToDto(r));
        }
        return subWithoutAccountDtos;
    }

    @PutMapping(value = {"/update/{id}/{newDate}/{newLicenseNumber}/{newParkingTime}/{newParkingSpot}", 
    "/update/{newId}/{newDate}/{newLicenseNumber}/{newParkingTime}/{newParkingSpot}/" })
	public SubWithoutAccountDto updateSubWithoutAccountDto(@PathVariable("id") int id, @PathVariable("newDate") Date newDate, @PathVariable("newLicenseNumber") String newLicenseNumber, 
    @PathVariable("newParkingTime") int newParkingTime, @PathVariable("newSpotId") int newSpotId) {
		SubWithoutAccount subWithoutAccount = subWithoutAccountService.updateSubWithoutAccount(id, newDate, newLicenseNumber, newParkingTime, newSpotId);
		return convertToDto(subWithoutAccount);
	}

    @PutMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public SubWithoutAccountDto deleteSubWithoutAccountDto(@PathVariable("id") int id){
        SubWithoutAccount sR = subWithoutAccountService.deleteSubWithoutAccount(id);
        return convertToDto(sR);
    }

    private ReservationDto convertToDto(Reservation reservation) {
	    ParkingSpotDto pDto = convertToDto(reservation.getParkingSpot());
        return new ReservationDto(reservation.getId(), reservation.getDate(), pDto);
    }

    private SubWithoutAccountDto convertToDto(SubWithoutAccount subWithoutAccount) {
        ParkingSpotDto pDto = convertToDto(subWithoutAccount.getParkingSpot());
        
        return new SubWithoutAccountDto(subWithoutAccount.getId(), subWithoutAccount.getDate(), subWithoutAccount.getLicenseNumber(), subWithoutAccount.getNbrMonths(), pDto);
    }

    private ParkingSpotDto convertToDto(ParkingSpot spot){
        return new ParkingSpotDto(spot.getId(), convertToDto(spot.getType()));
    }
    
    private ParkingSpotTypeDto convertToDto(ParkingSpotType type){
        return new ParkingSpotTypeDto(type.getName(), type.getFee());
    }
}

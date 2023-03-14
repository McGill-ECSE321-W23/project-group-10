package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;

public interface SubWithoutAccountRepository extends CrudRepository<SubWithoutAccount, Integer> {

    //find a subscription without account by id
    SubWithoutAccount findSubWithoutAccountById(int id);

    //find subscriptions without account by parking spot
    List<SubWithoutAccount> findSubWithoutAccountByParkingSpot(ParkingSpot parkingSpot);

    //find subscriptions without account by license number
    List<SubWithoutAccount> findSubWithoutAccountByLicenseNumber(String licenseNumber);

}
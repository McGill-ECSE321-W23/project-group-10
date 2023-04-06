package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithAccount;

public interface SubWithAccountRepository extends CrudRepository<SubWithAccount, Integer> {

    //find a subscription with account by id
    SubWithAccount findSubWithAccountById(int id);

    //find subscriptions with account by monthly customer
    List<SubWithAccount> findSubWithAccountByCustomer(MonthlyCustomer customer);

    //find subscriptions with account by parking spot
    List<SubWithAccount> findSubWithAccountByParkingSpot(ParkingSpot parkingSpot);

}
package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithAccount;

public interface SubWithAccountRepository extends CrudRepository<SubWithAccount, Integer> {

    SubWithAccount findSubWithAccountById(int id);

    List<SubWithAccount> findSubWithAccountByCustomer(MonthlyCustomer customer);

    List<SubWithAccount> findSubWithAccountByParkingSpot(ParkingSpot parkingSpot);

}
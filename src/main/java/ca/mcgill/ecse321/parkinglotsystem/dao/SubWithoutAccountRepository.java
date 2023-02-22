package ca.mcgill.ecse321.parkinglotsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.MonthlyCustomer;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotsystem.model.SubWithoutAccount;

public interface SubWithoutAccountRepository extends CrudRepository<SubWithoutAccount, Integer>{

    SubWithoutAccount findSubWithoutAccountById(String id);

    List<SubWithoutAccount> findSubWithoutAccountByMonthlyCustomer(MonthlyCustomer mCustomer);

    List<SubWithoutAccount> findSubWithoutAccountByParkingSpot(ParkingSpot parkingSpot);

}
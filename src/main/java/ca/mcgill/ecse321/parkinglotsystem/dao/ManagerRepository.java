package ca.mcgill.ecse321.parkinglotsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.Manager;

import java.util.List;

public interface ManagerRepository extends CrudRepository<Manager, String>{

	Manager findManagerByEmail(String email);
	List<Manager> findManagerByName(String name);
	List<Manager> findManagerByPhone(String phone);
	List<Manager> findManagerByPassword(String password);

}
package ca.mcgill.ecse321.parkinglotsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.Model.Manager;

public interface ManagerRepository extends CrudRepository<Manager, String>{

	Manager findManagerByEmail(String id);

}
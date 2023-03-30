package ca.mcgill.ecse321.parkinglotsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotsystem.model.Manager;

import java.util.List;

public interface ManagerRepository extends CrudRepository<Manager, String> {

    //find manager by email
    Manager findManagerByEmail(String email);

    //find manager by name
    List<Manager> findManagerByName(String name);

    //find manager by phone number
    List<Manager> findManagerByPhone(String phone);

    //find manager by password
    List<Manager> findManagerByPassword(String password);

    //find manager by token
    List<Manager> findManagerByToken(String token);

}
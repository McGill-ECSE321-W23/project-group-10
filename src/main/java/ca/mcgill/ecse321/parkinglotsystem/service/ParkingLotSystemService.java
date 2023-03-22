// Author :Chenxin Xun
// 2023-03-19

package ca.mcgill.ecse321.parkinglotsystem.service;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotsystem.dao.ParkingLotSystemRepository;
import ca.mcgill.ecse321.parkinglotsystem.model.ParkingLotSystem;
import ca.mcgill.ecse321.parkinglotsystem.service.exceptions.CustomException;
import static ca.mcgill.ecse321.parkinglotsystem.service.utilities.HelperMethods.toList;

@Service
public class ParkingLotSystemService {

    @Autowired
    private ParkingLotSystemRepository parkingLotSystemRepository;

    @Transactional
    public ParkingLotSystem createParkingLotSystem(int id, Time openTime, Time closTime){
        if (parkingLotSystemRepository.countParkingLotSystemById(id) > 0){
            throw new CustomException("The ParkingLostSystem Id already exists", HttpStatus.BAD_REQUEST);
        }
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.setId(id);
        parkingLotSystem.setCloseTime(closTime);
        parkingLotSystem.setOpenTime(openTime);
        parkingLotSystemRepository.save(parkingLotSystem);

        return parkingLotSystem;
    }

    @Transactional
    public ParkingLotSystem getById(int id){
        if (!(parkingLotSystemRepository.countParkingLotSystemById(id) > 0)){
            throw new CustomException("The ParkingLostSystem Id does not exist", HttpStatus.BAD_REQUEST);
        }
        return parkingLotSystemRepository.findParkingLotSystemById(id);
    }


    @Transactional
    public List<ParkingLotSystem> getAllByOpenTime(Time openTime){
        if(!(parkingLotSystemRepository.countParkingLotSystemByOpenTime(openTime) > 0)){
            throw new CustomException("The ParkingLostSystem openTime does not exist", HttpStatus.BAD_REQUEST);
        }
        List<ParkingLotSystem> systems = parkingLotSystemRepository.findParkingLotSystemByOpenTime(openTime);
        return systems;
    }

    @Transactional
    public List<ParkingLotSystem> getAllByCloseTime(Time closeTime){
        if(!(parkingLotSystemRepository.countParkingLotSystemByOpenTime(closeTime) > 0)){
            throw new CustomException("The ParkingLostSystem closeTime does not exist", HttpStatus.BAD_REQUEST);
        }
        List<ParkingLotSystem> systems = parkingLotSystemRepository.findParkingLotSystemByCloseTime(closeTime);
        return systems;
    }

    @Transactional
    public List<ParkingLotSystem> getAll(){
        return toList(parkingLotSystemRepository.findAll());
    }

    @Transactional
    public ParkingLotSystem updateParkingLotSystem(int id, Time openTime, Time closeTime){
        if (!(parkingLotSystemRepository.countParkingLotSystemById(id) > 0)){
            throw new CustomException("The ParkingLostSystem Id does not exist", HttpStatus.BAD_REQUEST);
        }
        ParkingLotSystem aSystem = parkingLotSystemRepository.findParkingLotSystemById(id);
        aSystem.setOpenTime(openTime);
        aSystem.setCloseTime(closeTime);
        parkingLotSystemRepository.save(aSystem);
        return aSystem;
    }
    
}

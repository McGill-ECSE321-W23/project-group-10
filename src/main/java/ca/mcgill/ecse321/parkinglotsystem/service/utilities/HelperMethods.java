package ca.mcgill.ecse321.parkinglotsystem.service.utilities;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.parkinglotsystem.dto.*;
import ca.mcgill.ecse321.parkinglotsystem.model.*;

public class HelperMethods {

    /**
     * Converts an Iterable to a List.
     * @param <T> Element type
     * @param iterable
     * @return A List containing the elements of the iterable
     */
    public static <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

    /**
     * Helper method to convert parking spot type to a DTO
     * @param parkingSpotType
     * @return Dto
     */
    public static ParkingSpotTypeDto convertParkingSpotTypeToDto(ParkingSpotType parkingSpotType) {

        if (parkingSpotType == null) {
            throw new IllegalArgumentException("There is no such parking spot type! ");
        }
        ParkingSpotTypeDto parkingSpotTypeDto = new ParkingSpotTypeDto();
        parkingSpotTypeDto.setName(parkingSpotType.getName());
        parkingSpotTypeDto.setFee(parkingSpotType.getFee());
        return parkingSpotTypeDto;
    }

    /**
     * Helper method to convert parking spot type to a DTO
     * @param parkingSpot  
     * @return Dto
     */
    public static ParkingSpotDto convertParkingSpotToDto(ParkingSpot parkingSpot) {

        if (parkingSpot == null) {
            throw new IllegalArgumentException("There is no such parking spot! ");
        }
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setId(parkingSpot.getId());
        parkingSpotDto.setType(convertParkingSpotTypeToDto(parkingSpot.getType()));
        return parkingSpotDto;
    }
}
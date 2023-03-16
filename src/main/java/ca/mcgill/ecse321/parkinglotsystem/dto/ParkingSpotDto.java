package ca.mcgill.ecse321.parkinglotsystem.dto;

import java.util.List;


public class ParkingSpotDto {
    private int id;
    private ParkingSpotTypeDto type;

    public ParkingSpotDto() {

    }

    public ParkingSpotDto(int id, ParkingSpotTypeDto parkingSpotTypeDto){
        this.id = id;
        this.type = parkingSpotTypeDto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpotTypeDto getType() {
        return type;
    }
    public void setType(ParkingSpotTypeDto type) {
        this.type = type;
    }
}

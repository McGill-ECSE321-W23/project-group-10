package ca.mcgill.ecse321.parkinglotsystem.dto;

import java.util.Collections;
import java.util.List;

public class ParkingSpotTypeDto {
    
    private String name;
    private double fee;

    public ParkingSpotTypeDto() {

    }

    public ParkingSpotTypeDto(String name, double fee){
        this.name = name;
        this.fee = fee;
    }

        public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getFee() {
        return fee;
    }
    public void setFee(double fee) {
        this.fee = fee;
    }

}

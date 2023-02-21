package ca.mcgill.ecse321.parkinglotsystem.model;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ParkingLotSystem {
    private int id;
    private Time openTime;
    private Time closeTime;

    @Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Time getOpenTime() {
        return openTime;
    }
    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }
    public Time getCloseTime() {
        return closeTime;
    }
    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }
}

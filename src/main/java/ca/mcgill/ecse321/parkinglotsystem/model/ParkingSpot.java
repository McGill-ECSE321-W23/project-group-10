package ca.mcgill.ecse321.parkinglotsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ParkingSpot {
    private int id;
    private ParkingSpotType type;

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @ManyToOne(optional = false)
    public ParkingSpotType getType() {
        return type;
    }
    public void setType(ParkingSpotType type) {
        this.type = type;
    }
}

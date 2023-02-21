package ca.mcgill.ecse321.parkinglotsystem.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Service {
    private String description;
    private int price;

    @Id
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}

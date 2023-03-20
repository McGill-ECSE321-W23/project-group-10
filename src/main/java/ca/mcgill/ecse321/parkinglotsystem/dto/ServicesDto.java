package ca.mcgill.ecse321.parkinglotsystem.dto;

import java.sql.Date;

public class ServicesDto {

    private String description;
    private int price;

    public ServicesDto() {

    }

    public ServicesDto(String description, int price) {

        this.description = description;
        this.price = price;

    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {

        return price;

    }


    public void setDescription(String description) {

        this.description = description;

    }

    public void setPrice(int price) {

        this.price = price;

    }

}

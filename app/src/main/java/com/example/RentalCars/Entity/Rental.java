package com.example.RentalCars.Entity;

import java.util.Calendar;
import java.util.Date;

public class Rental {
    private String rentedCarId;
    private Date startDate;
    private Date endDate;
    private float totalPrice;
    private String renterId;
    private String carOwnerId;
    private String renterFullName;

    public Rental(String rentedCarId, Date startDate, Date endDate, float totalPrice, String renterId,String carOwnerId) {
        this.rentedCarId = rentedCarId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.renterId = renterId;
        this.carOwnerId = carOwnerId;
    }
    public Rental(){}

    public String getRenterId() {
        return renterId;
    }


    public String getRentedCarId() {
        return rentedCarId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setRenterFullName(String renterFullName) {
        this.renterFullName = renterFullName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public String getCarOwnerId() {
        return carOwnerId;
    }

    public String getRenterFullName() {
        return renterFullName;
    }
}

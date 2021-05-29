package com.example.RentalCars.Entity;

import java.util.Calendar;
import java.util.Date;

public class Rental {
    private String rentedCarId;
    private Calendar startDate;
    private Calendar endDate;
    private float totalPrice;
    private String renterId;

    public Rental(String rentedCarId, Calendar startDate, Calendar endDate, float totalPrice, String renterId) {
        this.rentedCarId = rentedCarId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.renterId = renterId;
    }

    public String getRenterId() {
        return renterId;
    }


    public String getRentedCarId() {
        return rentedCarId;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

}

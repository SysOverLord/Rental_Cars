package com.example.RentalCars.Entity;

import java.util.Date;

public class Rental {
    Car rentedCar;
    Date startDate;
    Date endDate;
    float totalPrice;
    String renterId;

    public Rental(Car rentedCar, Date startDate, Date endDate, float totalPrice, String renterId) {
        this.rentedCar = rentedCar;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.renterId = renterId;
    }

    public String getRenterId() {
        return renterId;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Car getRentedCar() {
        return rentedCar;
    }

    public void setRentedCar(Car rentedCar) {
        this.rentedCar = rentedCar;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}

package com.example.RentalCars.Entity;

public class Car {

    private String model;
    private float dailyPrice;
    private String desc;
    private String brand;
    private String color;
    private String carId;
    private String ownerId;


    public Car(java.lang.String model, float dailyPrice, java.lang.String desc, java.lang.String brand, java.lang.String color, java.lang.String carId, java.lang.String ownerId) {
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.desc = desc;
        this.brand = brand;
        this.color = color;
        this.carId = carId;
        this.ownerId = ownerId;
    }
    public void Car(){
        // Default constructor required for calls to DataSnapshot.getValue(Car.class)
    }

    public String getCarId() {
        return carId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(float dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

package com.example.RentalCars.Entity;

public class Car {

    private String brand;
    private String model;
    private String color;
    private float dailyPrice;
    private String desc;
    private String carId;
    private String ownerId;

    public Car(String brand, String model, String color, float dailyPrice, String desc, String carId, String ownerId) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.dailyPrice = dailyPrice;
        this.desc = desc;
        this.carId = carId;
        this.ownerId = ownerId;
    }

    public Car(){
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


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

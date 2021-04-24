package com.example.RentalCars.Entity;

public class Car {
    private String model;
    private int dailyPrice;
    private String desc;
    private String brand;
    private String color;
    private String carId;
    private Person owner;
    public Car(String model, int dailyPrice, String desc, String brand, String color, String carId, Person owner) {
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.desc = desc;
        this.brand = brand;
        this.color = color;
        this.carId = carId;
        this.owner = owner;
    }
    public void Car(){
        // Default constructor required for calls to DataSnapshot.getValue(Car.class)
    }

    public String getCarId() {
        return carId;
    }

    public Person getOwner() {
        return owner;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(int dailyPrice) {
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

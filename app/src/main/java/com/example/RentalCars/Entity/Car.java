package com.example.RentalCars.Entity;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Car {
    private String model;
    private int dailyPrice;
    private String desc;
    private String brand;
    private String color;
    private String carId;
    public void Car(String model, int dailyPrice, String desc, String brand, String color){
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.desc = desc;
        this.brand = brand;
        this.color = color;
        this.carId = UUID.randomUUID().toString();

    }
    public void Car(){
        // Default constructor required for calls to DataSnapshot.getValue(Car.class)
        this.carId = UUID.randomUUID().toString();
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

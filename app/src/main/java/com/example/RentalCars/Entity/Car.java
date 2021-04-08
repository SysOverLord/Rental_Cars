package com.example.RentalCars.Entity;

import java.util.ArrayList;
import java.util.Random;

public class Car {
    private String model;
    private int dailyPrice;
    private String desc;
    private String brand;
    private String color;
    private int carId;
    public void Car(String model, int dailyPrice, String desc, String brand, String color, ArrayList<Integer> carIds){
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.desc = desc;
        this.brand = brand;
        this.color = color;
        boolean flag;
        Random rnd = new Random();
        do{
            flag = false;
            carId = rnd.nextInt();
            if(carIds.contains(carId))
                flag = true;
        }while(flag);
        carIds.add(carId);
    }
    public void Car(ArrayList<Integer> carIds){
        boolean flag;
        Random rnd = new Random();
        do{
            flag = false;
            carId = rnd.nextInt();
            if(carIds.contains(carId))
                flag = true;
        }while(flag);
        carIds.add(carId);
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

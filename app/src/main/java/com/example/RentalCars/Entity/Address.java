package com.example.RentalCars.Entity;

public class Address {
    String city;
    String town;
    int no;

    public Address(String city, String town, int no) {
        this.city = city;
        this.town = town;
        this.no = no;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}

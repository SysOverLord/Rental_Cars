package com.example.RentalCars.Entity;

public class Address {
    private String city;
    private String district;
    private String street;
    private String no;

    public Address(String city, String district, String street,String no) {
        this.city = city;
        this.district = district;
        this.street = street;
        this.no = no;
    }
    public Address(){

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}

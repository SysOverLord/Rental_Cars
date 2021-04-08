package com.example.RentalCars.Entity;

import java.util.ArrayList;

public class Customer extends User{
    ArrayList <Rental> rentals;

    public Customer(){
        rentals = new ArrayList<Rental>();
    }

    public ArrayList<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(ArrayList<Rental> rentals) {
        this.rentals = rentals;
    }
}

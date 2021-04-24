package com.example.RentalCars.Entity;

import java.util.ArrayList;

public class Customer extends User{
    String firstName;
    String lastName;
    Address address;
    CreditCard creditCard;
    ArrayList <Rental> rentals;



    public ArrayList<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(ArrayList<Rental> rentals) {
        this.rentals = rentals;
    }
}

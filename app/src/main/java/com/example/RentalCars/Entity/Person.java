package com.example.RentalCars.Entity;

import java.util.ArrayList;

public class Person extends User{
    String firstName;
    String lastName;
    Address address;
    CreditCard creditCard;
    ArrayList <Rental> rentals;

    public Person(String firstName, String lastName, Address address, CreditCard creditCard, ArrayList<Rental> rentals) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.creditCard = creditCard;
        this.rentals = new ArrayList<Rental>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public ArrayList<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(ArrayList<Rental> rentals) {
        this.rentals = rentals;
    }
}

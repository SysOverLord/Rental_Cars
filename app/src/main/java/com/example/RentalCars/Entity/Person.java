package com.example.RentalCars.Entity;

import java.util.ArrayList;
import java.util.Date;

public class Person extends User{
    private String firstName;
    private String lastName;
    private Address address;
    private CreditCard creditCard;
    private Date registerDate;

    public Person(String username, String email, String password, String firstName, String lastName, Address address, CreditCard creditCard, Date registerDate) {
        super(username, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.creditCard = creditCard;
        this.registerDate = registerDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public Date getRegisterDate() {
        return registerDate;
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

}

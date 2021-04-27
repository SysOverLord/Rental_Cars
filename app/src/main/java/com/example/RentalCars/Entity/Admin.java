package com.example.RentalCars.Entity;

public class Admin extends User {
    boolean isOnline;

    public Admin(String username, String email, String password,String userId ,boolean isOnline) {
        super(username, email, userId, password);
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return isOnline;
    }
}

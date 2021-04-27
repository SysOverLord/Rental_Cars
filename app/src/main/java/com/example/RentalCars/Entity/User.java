package com.example.RentalCars.Entity;

public class User {
    private String username;
    private String email;
    private String password;
    private String userId;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String password, String userId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password){ this.password = password;}



}

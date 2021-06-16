package com.example.RentalCars.Entity;

import java.util.Date;
import java.util.Random;

public class CreditCard {
    private float limit;
    private Date expireDate;
    private String cardNo;
    private String cvvNo;

    public CreditCard(Date expireDate, String cvvNo,String cardNo) {
        this.limit = new Random().nextInt(4800) + 200;
        this.expireDate = expireDate;
        this.cvvNo = cvvNo;
        this.cardNo = cardNo;
    }
    public CreditCard(){

    }

    public String getCvvNo() {
        return cvvNo;
    }

    public float getLimit() {
        return limit;
    }

    public void setLimit(float limit) { this.limit = limit; }

    public Date getExpireDate() {
        return expireDate;
    }


    public String getCardNo() {
        return cardNo;
    }

}

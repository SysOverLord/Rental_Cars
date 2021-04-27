package com.example.RentalCars.Entity;

import java.util.Date;

public class CreditCard {
    private float limit;
    private Date expireDate;
    private String cardNo;
    private String cvvNo;

    public CreditCard(float limit, Date expireDate, String cvvNo,String cardNo) {
        this.limit = limit;
        this.expireDate = expireDate;
        this.cvvNo = cvvNo;
        this.cardNo = cardNo;
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

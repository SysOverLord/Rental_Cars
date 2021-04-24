package com.example.RentalCars.Entity;

import java.util.Date;

public class CreditCard {
    int limit;
    Date expireDate;
    String cardNo;

    public CreditCard(int limit, Date expireDate, String cardNo) {
        this.limit = limit;
        this.expireDate = expireDate;
        this.cardNo = cardNo;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}

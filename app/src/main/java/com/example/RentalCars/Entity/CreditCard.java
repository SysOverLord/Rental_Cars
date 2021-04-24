package com.example.RentalCars.Entity;

import java.util.Date;

public class CreditCard {
    int limit;
    Date date;
    String cardNo;

    public CreditCard(int limit, Date date, String cardNo) {
        this.limit = limit;
        this.date = date;
        this.cardNo = cardNo;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}

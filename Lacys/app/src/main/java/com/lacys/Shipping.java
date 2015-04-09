package com.lacys;

import java.util.Calendar;

/**
 * Created by Christina on 4/7/2015.
 */
public class Shipping
{
    Calendar arrivalDate;
    Double cost;
    CheckOut checkOut;

    //constructor
    //called by ShoppingCartScreen
    public Shipping(Calendar arrivalDate, Double cost) {
        this.arrivalDate = arrivalDate;
        this.cost = cost;
    }

    public Calendar getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Calendar arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public CheckOut getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(CheckOut checkOut) {
        this.checkOut = checkOut;
    }
}

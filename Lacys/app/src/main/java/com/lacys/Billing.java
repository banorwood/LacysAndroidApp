package com.lacys;

import java.util.Calendar;

/**
 * Created by Christina on 4/8/2015.
 */
public class Billing {

    private Calendar purchaseDate;
    private String cardType;
    private long cardNum;
    private Calendar expDate;
    private int securityCode;
    private CheckOut checkOut;

    //checkout for this object is either created by billing screen or it is a reference to
    //the checkout in the shipping object. The other attributes are added by the payment screen.
    public Billing(CheckOut checkOut) {
        this.checkOut = checkOut;
    }

    public Calendar getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Calendar purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public long getCardNum() {
        return cardNum;
    }

    public void setCardNum(long cardNum) {
        this.cardNum = cardNum;
    }

    public Calendar getExpDate() {
        return expDate;
    }

    public void setExpDate(Calendar expDate) {
        this.expDate = expDate;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public CheckOut getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(CheckOut checkOut) {
        this.checkOut = checkOut;
    }
}

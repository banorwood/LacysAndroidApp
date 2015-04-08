package com.lacys;

import java.util.Calendar;

/**
 * Created by Christina on 4/8/2015.
 */
public class Billing {

    Calendar purchaseDate;
    int cardType;
    int cardNum;
    int expDate;
    int securityCode;
    CheckOut checkOut;

    public Billing(CheckOut checkOut, Calendar purchaseDate, int cardType, int cardNum, int expDate, int securityCode ) {
        this.checkOut = checkOut;
        this.purchaseDate = purchaseDate;
        this.cardType = cardType;
        this.cardNum = cardNum;
        this.expDate = expDate;
        this.securityCode = securityCode;
    }

    public Calendar getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Calendar purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public int getExpDate() {
        return expDate;
    }

    public void setExpDate(int expDate) {
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

package com.lacys;

/**
 * Created by Christina on 4/7/2015.
 */

//At first I considered making this an abstract base class but this way the shipping and billing
//objects can both hold a reference to the same CheckOut object if the user decides to make the
//billing address the same as the shipping address.

public class CheckOut {

    String firstName;
    String lastName;
    String addressLine1;
    String addressLine2;
    String city;
    Integer zipCode;
    String state;

    public CheckOut(String firstName, String lastName, String addressLine1,
                       String addressLine2, String city, Integer zipCode, String state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }
}

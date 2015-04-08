package com.lacys;

/**
 * Created by Derick on 4/1/2015.
 */
public class System {
    private static int userID;
    private static System system;

    //As a user goes through the checkout process, these objects are created
    //and updated. After the user has ordered, these objects are written to the
    //database.
    private Shipping shippingForNewOrder;
    private Billing billingForNewOrder;


    private System() {
        userID = 0;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int uid) {
        userID = uid;
    }

    public static System getInstance()
    {
        if(system == null)
            system = new System();
        return system;
    }

    public Shipping getShippingForNewOrder() {
        return shippingForNewOrder;
    }

    public void setShippingForNewOrder(Shipping shippingForNewOrder) {
        this.shippingForNewOrder = shippingForNewOrder;
    }

    public Billing getBillingForNewOrder() {
        return billingForNewOrder;
    }

    public void setBillingForNewOrder(Billing billingForNewOrder) {
        this.billingForNewOrder = billingForNewOrder;
    }
}

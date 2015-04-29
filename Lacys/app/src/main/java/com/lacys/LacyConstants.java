package com.lacys;


public class LacyConstants {
    // Database Name
    public static final String DATABASE_NAME = "lacys";
    //version number to upgrade database version
    public static final int DATABASE_VERSION = 12;

    //All the tables in database
    public static final String TABLE_ACCOUNT = "account";
    public static final String TABLE_BILLING = "billing";
    public static final String TABLE_CHECKOUT = "checkout";
    public static final String TABLE_CLOTHINGORDER = "clothingorder";
    public static final String TABLE_ORDERS = "orders";
    public static final String TABLE_PRODUCT = "product";
    public static final String TABLE_PRODUCTORDER = "productorder";
    public static final String TABLE_REVIEW = "review";
    public static final String TABLE_SHIPPING = "shipping";


    // Account table Columns
    public static final String TABLE_ACCOUNT_ID = TABLE_ACCOUNT + "_id";
    public static final String TABLE_ACCOUNT_EMAIL = TABLE_ACCOUNT + "_email";
    public static final String TABLE_ACCOUNT_PASSWORD = TABLE_ACCOUNT + "_password";
    public static final String TABLE_ACCOUNT_FIRSTNAME = TABLE_ACCOUNT + "_firstName";
    public static final String TABLE_ACCOUNT_LASTNAME = TABLE_ACCOUNT + "_lastName";
    public static final String TABLE_ACCOUNT_BILLING_ID = TABLE_ACCOUNT + "_billingID";
    public static final String TABLE_ACCOUNT_SHIPPING_ID = TABLE_ACCOUNT + "_shippingID";
    public static final String TABLE_ACCOUNT_CURRENT_ORDER_ID = TABLE_ORDERS + "_currentID";


    // Billing table Columns
    public static final String TABLE_BILLING_ID = TABLE_BILLING + "_id";
    public static final String TABLE_BILLING_PURCHASEDATE = TABLE_BILLING + "_purchaseDate";
    public static final String TABLE_BILLING_CARDTYPE = TABLE_BILLING + "_cardType";
    public static final String TABLE_BILLING_CARDNUMBER = TABLE_BILLING + "_cardNumber";
    public static final String TABLE_BILLING_CARDEXPIRATION = TABLE_BILLING + "_cardExpiration";
    public static final String TABLE_BILLING_CARDCODE = TABLE_BILLING + "_cardCode";

    // Checkout table Columns
    public static final String TABLE_CHECKOUT_ID = TABLE_CHECKOUT + "_id";
    public static final String TABLE_CHECKOUT_ORDER_ID = TABLE_CHECKOUT + "_orderID";
    public static final String TABLE_CHECKOUT_FIRSTNAME = TABLE_CHECKOUT + "_firstName";
    public static final String TABLE_CHECKOUT_LASTNAME = TABLE_CHECKOUT + "_lastName";
    public static final String TABLE_CHECKOUT_ADDRESSLINE_1 = TABLE_CHECKOUT + "_addressLine1";
    public static final String TABLE_CHECKOUT_ADDRESSLINE_2 = TABLE_CHECKOUT + "_addressLine2";
    public static final String TABLE_CHECKOUT_CITY = TABLE_CHECKOUT + "_city";
    public static final String TABLE_CHECKOUT_ZIPCODE = TABLE_CHECKOUT + "_zipCode";
    public static final String TABLE_CHECKOUT_STATE = TABLE_CHECKOUT + "_state";
    public static final String TABLE_CHECKOUT_BILLING_ID = TABLE_CHECKOUT + "_billingID";
    public static final String TABLE_CHECKOUT_SHIPPING_ID = TABLE_CHECKOUT + "_shippingID";

    // Clothing order table Columns
    public static final String TABLE_CLOTHINGORDER_PRODUCTORDER_ID = TABLE_PRODUCTORDER + "_id";
    public static final String TABLE_CLOTHINGORDER_COLOR = TABLE_CLOTHINGORDER + "_color";
    public static final String TABLE_CLOTHINGORDER_SIZE = TABLE_CLOTHINGORDER + "_size";

    // Orders table Columns
    public static final String TABLE_ORDERS_ID = TABLE_ORDERS + "_id";
    public static final String TABLE_ORDERS_ACCOUNT_ID = TABLE_ORDERS + "_accountID";
    public static final String TABLE_ORDERS_SHIPPING_ID = TABLE_ORDERS + "_shippingID";
    public static final String TABLE_ORDERS_BILLING_ID = TABLE_ORDERS + "_billingID";
    public static final String TABLE_ORDERS_TOTAL = TABLE_ORDERS + "_total";

    // Product table Columns
    public static final String TABLE_PRODUCT_ID = TABLE_PRODUCT + "_id";
    public static final String TABLE_PRODUCT_NAME = TABLE_PRODUCT + "_name";
    public static final String TABLE_PRODUCT_DESCRIPTION = TABLE_PRODUCT + "_desc";
    public static final String TABLE_PRODUCT_PRICE = TABLE_PRODUCT + "_price";
    public static final String TABLE_PRODUCT_DISCOUNT = TABLE_PRODUCT + "_discount";
    public static final String TABLE_PRODUCT_AVGRATING = TABLE_PRODUCT + "_avgRating";
    public static final String TABLE_PRODUCT_ISCLOTHING = TABLE_PRODUCT + "_isClothing";
    public static final String TABLE_PRODUCT_IMGINDEX = TABLE_PRODUCT + "_imgIndex";
    public static final String TABLE_PRODUCT_CATEGORY = TABLE_PRODUCT + "_category";

    // Product order table Columns
    public static final String TABLE_PRODUCTORDER_ID = TABLE_PRODUCTORDER + "_id";
    public static final String TABLE_PRODUCTORDER_ORDER_ID = TABLE_ORDERS_ID;
    public static final String TABLE_PRODUCTORDER_PRODUCT_ID = TABLE_PRODUCT_ID;
    public static final String TABLE_PRODUCTORDER_QUANTITY = "quantity";
    public static final String TABLE_PRODUCTORDER_COLOR = "color";
    public static final String TABLE_PRODUCTORDER_SIZE = "size";
    public static final String TABLE_PRODUCTORDER_ALREADYCHECKEDOUT = "alreadyCheckedOut";

    // Review table Columns
    public static final String TABLE_REVIEW_ID = TABLE_REVIEW + "_id";
    public static final String TABLE_REVIEW_NAME = TABLE_REVIEW + "_name";
    public static final String TABLE_REVIEW_RATING = TABLE_REVIEW + "_rating";
    public static final String TABLE_REVIEW_MESSAGE = TABLE_REVIEW + "_message";
    public static final String TABLE_REVIEW_PRODUCT_ID = TABLE_REVIEW + "_productID";

    // Shipping table Columns
    public static final String TABLE_SHIPPING_ID = TABLE_SHIPPING + "_id";
    public static final String TABLE_SHIPPING_ARRIVALDATE = TABLE_SHIPPING + "_arrivalDate";
    public static final String TABLE_SHIPPING_COST = TABLE_SHIPPING + "_cost";


}

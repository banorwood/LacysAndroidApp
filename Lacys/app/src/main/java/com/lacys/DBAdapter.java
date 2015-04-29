package com.lacys;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

public class DBAdapter{
    private static DBHelper DBHelper = null;
    private static Context contxt;

    public DBAdapter(Context context){
        contxt = context;
    }

    //Initialize/Open database
    public static void init(){
        if (getDBHelper() == null){
            DBHelper = new DBHelper(contxt);
        }
    }

    //Return DBHelper object
    public static DBHelper getDBHelper(){
        return DBHelper;
    }

    //Close database
    public static void close(){
        DBHelper.close();
    }

    public static synchronized SQLiteDatabase open() throws SQLException{
        return DBHelper.getWritableDatabase();
    }

    //Inner class to create database
    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, LacyConstants.DATABASE_NAME, null, LacyConstants.DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            //Query to Create Account Table
            String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + LacyConstants.TABLE_ACCOUNT + " (" + LacyConstants.TABLE_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_ACCOUNT_EMAIL + " VARCHAR, " + LacyConstants.TABLE_ACCOUNT_PASSWORD + " VARCHAR, " + LacyConstants.TABLE_ACCOUNT_FIRSTNAME +
                    " VARCHAR, " + LacyConstants.TABLE_ACCOUNT_LASTNAME + " VARCHAR, " + LacyConstants.TABLE_ACCOUNT_BILLING_ID + " INTEGER, " + LacyConstants.TABLE_ACCOUNT_CURRENT_ORDER_ID + " INTEGER DEFAULT 0, " +
                    LacyConstants.TABLE_ACCOUNT_SHIPPING_ID + " INTEGER);";

            //Query to Create Billing Table
            String CREATE_TABLE_BILLING = "CREATE TABLE " + LacyConstants.TABLE_BILLING + " (" + LacyConstants.TABLE_BILLING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_BILLING_PURCHASEDATE + " LONG, " + LacyConstants.TABLE_BILLING_CARDTYPE + " VARCHAR, " + LacyConstants.TABLE_BILLING_CARDNUMBER + " VARCHAR, " +
                    LacyConstants.TABLE_BILLING_CARDEXPIRATION + " LONG, " + LacyConstants.TABLE_BILLING_CARDCODE + " INTEGER);";

            //Query to Create Checkout Table
            String CREATE_TABLE_CHECKOUT = "CREATE TABLE " + LacyConstants.TABLE_CHECKOUT + " (" + LacyConstants.TABLE_CHECKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_CHECKOUT_ORDER_ID + " INTEGER, " + LacyConstants.TABLE_CHECKOUT_FIRSTNAME + " VARCHAR, " + LacyConstants.TABLE_CHECKOUT_LASTNAME + " VARCHAR, " +
                    LacyConstants.TABLE_CHECKOUT_ADDRESSLINE_1 + " TEXT, " + LacyConstants.TABLE_CHECKOUT_ADDRESSLINE_2 + " TEXT," + LacyConstants.TABLE_CHECKOUT_CITY + " VARCHAR, " +
                    LacyConstants.TABLE_CHECKOUT_ZIPCODE + " INTEGER, " + LacyConstants.TABLE_CHECKOUT_STATE + " VARCHAR, " + LacyConstants.TABLE_CHECKOUT_BILLING_ID + " INTEGER, " +
                    LacyConstants.TABLE_CHECKOUT_SHIPPING_ID + " INTEGER);";

            //Query to Create ClothingOrder Table
            String CREATE_TABLE_CLOTHINGORDER = "CREATE TABLE " + LacyConstants.TABLE_CLOTHINGORDER + " (" + LacyConstants.TABLE_CLOTHINGORDER_PRODUCTORDER_ID + " INTEGER," +
                    LacyConstants.TABLE_CLOTHINGORDER_COLOR + " VARCHAR, " + LacyConstants.TABLE_CLOTHINGORDER_SIZE + " VARCHAR);";

            //Query to Create Orders Table
            String CREATE_TABLE_ORDERS = "CREATE TABLE " + LacyConstants.TABLE_ORDERS + " (" + LacyConstants.TABLE_ORDERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_ORDERS_ACCOUNT_ID + " INTEGER, " + LacyConstants.TABLE_ORDERS_SHIPPING_ID + " INTEGER, " + LacyConstants.TABLE_ORDERS_BILLING_ID + ", " +
                    LacyConstants.TABLE_ORDERS_TOTAL + " DOUBLE NOT NULL DEFAULT 0.0);";

            //Query to Create Product Table
            String CREATE_TABLE_PRODUCT = "CREATE TABLE " + LacyConstants.TABLE_PRODUCT + " (" + LacyConstants.TABLE_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_PRODUCT_NAME + " VARCHAR, " + LacyConstants.TABLE_PRODUCT_DESCRIPTION + " TEXT, " + LacyConstants.TABLE_PRODUCT_PRICE +
                    " DOUBLE NOT NULL DEFAULT 0.0, " + LacyConstants.TABLE_PRODUCT_DISCOUNT + " DOUBLE, " + LacyConstants.TABLE_PRODUCT_AVGRATING + " DOUBLE NOT NULL DEFAULT 0.0, " +
                    LacyConstants.TABLE_PRODUCT_ISCLOTHING + "  BOOL NOT NULL DEFAULT false, " + LacyConstants.TABLE_PRODUCT_IMGINDEX + " INTEGER NOT NULL DEFAULT 0, " + LacyConstants.TABLE_PRODUCT_CATEGORY + " VARCHAR);";

            //Query to Create Product Order Table
            String CREATE_TABLE_PRODUCTORDER = "CREATE TABLE " + LacyConstants.TABLE_PRODUCTORDER + " (" + LacyConstants.TABLE_PRODUCTORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + " INTEGER," + LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID + " INTEGER, " +
                    LacyConstants.TABLE_PRODUCTORDER_ALREADYCHECKEDOUT + " BOOL NOT NULL DEFAULT false, " + LacyConstants.TABLE_PRODUCTORDER_QUANTITY + " INTEGER DEFAULT 1," +
                    LacyConstants.TABLE_PRODUCTORDER_COLOR + " VARCHAR, " + LacyConstants.TABLE_PRODUCTORDER_SIZE + " VARCHAR, FOREIGN KEY(" +
                    LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + ") REFERENCES " + LacyConstants.TABLE_ORDERS + "(" + LacyConstants.TABLE_ORDERS_ID + "), FOREIGN KEY(" +
                    LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID + ") REFERENCES " + LacyConstants.TABLE_PRODUCT + "(" + LacyConstants.TABLE_PRODUCT_ID + "));";

            //Query to Create Review Table
            String CREATE_TABLE_REVIEW = "CREATE TABLE " + LacyConstants.TABLE_REVIEW + " (" + LacyConstants.TABLE_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_REVIEW_NAME + " VARCHAR, " + LacyConstants.TABLE_REVIEW_RATING + " DOUBLE NOT NULL DEFAULT 0.0, " + LacyConstants.TABLE_REVIEW_MESSAGE +
                    " TEXT, " + LacyConstants.TABLE_REVIEW_PRODUCT_ID + " INTEGER);";

            //Query to Create Shipping Table
            String CREATE_TABLE_SHIPPING = "CREATE TABLE " + LacyConstants.TABLE_SHIPPING + " (" + LacyConstants.TABLE_SHIPPING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_SHIPPING_ARRIVALDATE + " LONG, " + LacyConstants.TABLE_SHIPPING_COST + " DOUBLE);";

            try {
                //Execute the queries to the database
                db.execSQL(CREATE_TABLE_ACCOUNT);
                db.execSQL(CREATE_TABLE_BILLING);
                db.execSQL(CREATE_TABLE_CHECKOUT);
                db.execSQL(CREATE_TABLE_CLOTHINGORDER);
                db.execSQL(CREATE_TABLE_ORDERS);
                db.execSQL(CREATE_TABLE_PRODUCT);
                db.execSQL(CREATE_TABLE_PRODUCTORDER);
                db.execSQL(CREATE_TABLE_REVIEW);
                db.execSQL(CREATE_TABLE_SHIPPING);

                String products[][] = {
                        //name, description, price, discount, rating, isClothing, imgIndex, category
                        {"Men's Shirt 1","Product description for Men's Shirt 1","10.0","0.2","0.0","true","1","Men Shirts"},
                        {"Men's Shirt 2","Product description for Men's Shirt 2","20.0","0.25","0.0","true","2","Men Shirts"},
                        {"Men's Shirt 3","Product description for Men's Shirt 3","30.0","0.2","0.0","true","3","Men Shirts"},
                        {"Men's Shirt 4","Product description for Men's Shirt 4","40.0","0.3","0.0","true","4","Men Shirts"},
                        {"Men's Shirt 5","Product description for Men's Shirt 5","50.0","0.4","0.0","true","5","Men Shirts"},
                        {"Men's Shirt 6","Product description for Men's Shirt 6","60.0","0.35","0.0","true","6","Men Shirts"},
                        {"Men's Pant 1","Product description for Men's Pant 1","10.0","0.2","0.0","true","7","Men Pants"},
                        {"Men's Pant 2","Product description for Men's Pant 2","20.0","0.3","0.0","true","8","Men Pants"},
                        {"Men's Pant 3","Product description for Men's Pant 3","30.0","0.4","0.0","true","9","Men Pants"},
                        {"Men's Pant 4","Product description for Men's Pant 4","40.0","0.2","0.0","true","10","Men Pants"},
                        {"Men's Pant 5","Product description for Men's Pant 5","50.0","0.35","0.0","true","11","Men Pants"},
                        {"Men's Pant 6","Product description for Men's Pant 6","60.0","0.3","0.0","true","12","Men Pants"},
                        {"Women's Skirt 1","Product description for Women's Skirt 1","10.0","0.15","0.0","true","13","Women Skirts"},
                        {"Women's Skirt 2","Product description for Women's Skirt 2","12.0","0.2","0.0","true","14","Women Skirts"},
                        {"Women's Skirt 3","Product description for Women's Skirt 3","14.0","0.25","0.0","true","15","Women Skirts"},
                        {"Women's Skirt 4","Product description for Women's Skirt 4","16.0","0.2","0.0","true","16","Women Skirts"},
                        {"Women's Skirt 5","Product description for Women's Skirt 5","19.0","0.2","0.0","true","17","Women Skirts"},
                        {"Women's Shirt 1","Product description for Women's Shirt 1","10.0","0.35","0.0","true","0","Women Shirts"},
                        {"Women's Shirt 2","Product description for Women's Shirt 2","12.0","0.3","0.0","true","0","Women Shirts"},
                        {"Women's Shirt 3","Product description for Women's Shirt 3","14.0","0.2","0.0","true","0","Women Shirts"},
                        {"Women's Shirt 4","Product description for Women's Shirt 4","16.0","0.15","0.0","true","0","Women Shirts"},
                        {"Women's Shirt 5","Product description for Women's Shirt 5","19.0","0.2","0.0","true","0","Women Shirts"},
                        {"Women's Pant 1","Product description for Women's Pant 1","10.0","0.2","0.0","true","0","Women Pants"},
                        {"Women's Pant 2","Product description for Women's Pant 2","12.0","0.15","0.0","true","0","Women Pants"},
                        {"Women's Pant 3","Product description for Women's Pant 3","14.0","0.25","0.0","true","0","Women Pants"},
                        {"Women's Pant 4","Product description for Women's Pant 4","16.0","0.3","0.0","true","0","Women Pants"},
                        {"Women's Pant 5","Product description for Women's Pant 5","19.0","0.2","0.0","true","0","Women Pants"},
                        {"Tool Kit","Product description for Tool Kit","10.0","0.0","0.0","false","0","Home Essentials"},
                        {"Light Bulbs","Product description for Light Bulbs","12.0","0.0","0.0","false","0","Home Essentials"},
                        {"Towels","Product description for Towels","14.0","0.0","0.0","false","0","Home Essentials"},
                        {"Sofa Cushion","Product description for Sofa Cushion","16.0","0.0","0.0","false","0","Home Essentials"},
                        {"Table Cloth","Product description for Table Cloth","19.0","0.0","0.0","false","0","Home Essentials"},
                };
                //SQLiteDatabase db = open();
                ContentValues cVal = new ContentValues();
                for (String p[] : products) {
                    cVal.put(LacyConstants.TABLE_PRODUCT_NAME, p[0]);
                    cVal.put(LacyConstants.TABLE_PRODUCT_DESCRIPTION, p[1]);
                    cVal.put(LacyConstants.TABLE_PRODUCT_PRICE, p[2]);
                    cVal.put(LacyConstants.TABLE_PRODUCT_DISCOUNT, p[3]);
                    cVal.put(LacyConstants.TABLE_PRODUCT_AVGRATING, p[4]);
                    cVal.put(LacyConstants.TABLE_PRODUCT_ISCLOTHING, p[5]);
                    cVal.put(LacyConstants.TABLE_PRODUCT_IMGINDEX, p[6]);
                    cVal.put(LacyConstants.TABLE_PRODUCT_CATEGORY, p[7]);
                    db.insert(LacyConstants.TABLE_PRODUCT, null, cVal);
                }

            } catch (Exception exception) {}

        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed, all data will be gone
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_ACCOUNT);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_BILLING);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_CHECKOUT);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_CLOTHINGORDER);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_ORDERS);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_PRODUCT);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_PRODUCTORDER);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_REVIEW);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_SHIPPING);

            // Create tables again
            onCreate(db);
        }
        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed, all data will be gone
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_ACCOUNT);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_BILLING);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_CHECKOUT);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_CLOTHINGORDER);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_ORDERS);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_PRODUCT);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_PRODUCTORDER);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_REVIEW);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_SHIPPING);

            // Create tables again
            onCreate(db);
        }
    }


    public static Cursor getAccDetails(int userid)
    {
        SQLiteDatabase db = open();
        Cursor c = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_ACCOUNT + "  WHERE (" +
                LacyConstants.TABLE_ACCOUNT_ID + " = '" + userid + "')", null);
        if (c.moveToFirst()) {
            do {
                return c;
            }while (c.moveToNext());
        }
        return null;
    }

    public static boolean accountExists(String email)
    {
        SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_ACCOUNT + "  WHERE (" +
                LacyConstants.TABLE_ACCOUNT_EMAIL + " = '" + email + "')", null);
        //process the result and show the returned values in the log
        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }

        return false;
    }

    public static Cursor login(String email)
    {
        SQLiteDatabase db = open();
        // do the query and get a cursor
        Cursor cursor = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_ACCOUNT + "  WHERE (" +
                LacyConstants.TABLE_ACCOUNT_EMAIL + " = '" + email + "')", null);

        //process the result and show the returned values in the log
        if (cursor.moveToFirst()) {
            do {
                return cursor;
            } while (cursor.moveToNext());
        }
        return null;
    }

    public static long createAccount(String email, String password, String first, String last)
    {
        long rid;
        SQLiteDatabase db = open();
        ContentValues cVal = new ContentValues();
        cVal.put(LacyConstants.TABLE_ACCOUNT_EMAIL,email);
        cVal.put(LacyConstants.TABLE_ACCOUNT_PASSWORD,password);
        cVal.put(LacyConstants.TABLE_ACCOUNT_FIRSTNAME,first);
        cVal.put(LacyConstants.TABLE_ACCOUNT_LASTNAME,last);
        rid = db.insert(LacyConstants.TABLE_ACCOUNT, null, cVal);
        return rid;
    }

    private static long writePaymentInfo(Billing billing)
    {
        long rid;
        SQLiteDatabase db = open();
        ContentValues cVal = new ContentValues();
        cVal.put(LacyConstants.TABLE_BILLING_PURCHASEDATE, billing.getPurchaseDate().getTimeInMillis());
        cVal.put(LacyConstants.TABLE_BILLING_CARDTYPE, billing.getCardType());
        cVal.put(LacyConstants.TABLE_BILLING_CARDNUMBER, billing.getCardNum());
        cVal.put(LacyConstants.TABLE_BILLING_CARDEXPIRATION, billing.getExpDate().getTimeInMillis());
        cVal.put(LacyConstants.TABLE_BILLING_CARDCODE, billing.getSecurityCode());

        rid = db.insert(LacyConstants.TABLE_BILLING, null, cVal);
        return rid;
    }

    private static long writeShippingInfo(Shipping shipping)
    {
        long rid;
        SQLiteDatabase db = open();
        ContentValues cVal = new ContentValues();

        //I don't think we should have a shipping date because we already have
        //a purchase date in the payment table
        cVal.put(LacyConstants.TABLE_SHIPPING_ARRIVALDATE, shipping.getArrivalDate().getTimeInMillis() );
        cVal.put(LacyConstants.TABLE_SHIPPING_COST, shipping.getCost() );

        rid = db.insert(LacyConstants.TABLE_SHIPPING, null, cVal);
        return rid;
    }

    //null is passed for shippingId if this is only a billing address.
    //likewise null is passed for billing id if this is a billing address.
    //if billing and shipping are the same, both ids are passed
    public static long writeToCheckOutTable(CheckOut checkOut, Long shippingId, Long billingId, int orderId) {
        long rid;
        SQLiteDatabase db = open();
        ContentValues cVal = new ContentValues();
        cVal.put(LacyConstants.TABLE_CHECKOUT_FIRSTNAME, checkOut.getFirstName());
        cVal.put(LacyConstants.TABLE_CHECKOUT_LASTNAME, checkOut.getLastName());
        cVal.put(LacyConstants.TABLE_CHECKOUT_ORDER_ID, orderId);
        cVal.put(LacyConstants.TABLE_CHECKOUT_ADDRESSLINE_1, checkOut.getAddressLine1());
        cVal.put(LacyConstants.TABLE_CHECKOUT_ADDRESSLINE_2, checkOut.getAddressLine2());
        cVal.put(LacyConstants.TABLE_CHECKOUT_CITY, checkOut.getCity());
        cVal.put(LacyConstants.TABLE_CHECKOUT_ZIPCODE, checkOut.getZipCode());
        cVal.put(LacyConstants.TABLE_CHECKOUT_STATE, checkOut.getState());

        if (shippingId != null)
        {
            cVal.put(LacyConstants.TABLE_CHECKOUT_SHIPPING_ID, shippingId);
        }
        if (billingId != null)
        {
            cVal.put(LacyConstants.TABLE_CHECKOUT_BILLING_ID, billingId);
        }

        rid = db.insert(LacyConstants.TABLE_CHECKOUT, null, cVal);
        return rid;
    }

    //Writes to shipping, billing, and checkout tables using the billing object and shipping object
    //in System
    public static void writeAllCheckOutInfo()
    {
        //shippingId and billingId are optional foreign keys in the checkout table
        long billingId;
        long shippingId;
        int orderId = System.getInstance().getOrderID();
        Shipping shipping = System.getInstance().getShippingForNewOrder();
        Billing billing = System.getInstance().getBillingForNewOrder();

        shippingId = writeShippingInfo(shipping);
        billingId = writePaymentInfo(billing);

        //if they both share the same memory space, they are the same so only make
        //one row in the checkout table. Set the foreign keys for the shipping and billing id
        //in that row
        if (shipping.getCheckOut() == billing.getCheckOut())
        {
            writeToCheckOutTable(shipping.getCheckOut(), shippingId, billingId, orderId);
        }
        else //make two rows in CheckOut table
        {
            //write shipping address to CheckOut table and set the foreign key for the
            //shipping id
            writeToCheckOutTable(shipping.getCheckOut(), shippingId, null, orderId);

            //make a different row for the billing address in the checkout table and set the foreign
            //key for the billing id
            writeToCheckOutTable(billing.getCheckOut(), null, billingId, orderId);
        }
        setCartItemsCheckedOut(orderId);
        updateOrderData(orderId,billingId,shippingId);
    }

    public static ArrayList<String[]> getProducts(String category)
    {
        String[] products;
        ArrayList<String[]> data = new ArrayList<>();
        SQLiteDatabase db = open();
        // do the query and get a cursor
        Cursor cursor = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_PRODUCT + "  WHERE (" +
                LacyConstants.TABLE_PRODUCT_CATEGORY + " = '" + category + "')", null);
        //process the result and show the returned values in the log
        if (cursor.moveToFirst()) {
            do {
                products = new String[7];
                products[0] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_NAME));
                products[1] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_IMGINDEX));
                products[2] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_PRICE));
                products[3] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_DISCOUNT));
                products[4] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_DESCRIPTION));
                products[5] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_AVGRATING));
                products[6] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_ID));
                data.add(products);
            } while (cursor.moveToNext());
        }
        return data;
    }

    public static Cursor getShoppingCartData(int userID)
    {
        SQLiteDatabase db = open();

        Cursor c = db.rawQuery("SELECT po.rowid _id, * FROM " + LacyConstants.TABLE_PRODUCTORDER + " AS po LEFT JOIN " +
                LacyConstants.TABLE_ORDERS + " AS o ON (po." + LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + " = o." +
                LacyConstants.TABLE_ORDERS_ID + ") LEFT JOIN " + LacyConstants.TABLE_PRODUCT + " AS p ON (po." +
                LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID + " = p." + LacyConstants.TABLE_PRODUCT_ID + ") WHERE (o." +
                LacyConstants.TABLE_ORDERS_ACCOUNT_ID + " = '" + userID + "') AND (po." + LacyConstants.TABLE_PRODUCTORDER_ALREADYCHECKEDOUT + " = 'false')", null);
        if (c.moveToFirst()) {
            do {
                return c;
            }while (c.moveToNext());
        }

        return null;
    }

    public static long addOrderID(int userid)
    {
        long rid;
        SQLiteDatabase db = open();
        ContentValues cVal = new ContentValues();
        cVal.put(LacyConstants.TABLE_ORDERS_ACCOUNT_ID,userid);
        rid = db.insert(LacyConstants.TABLE_ORDERS, null, cVal);

        return rid;
    }

    public static long addShoppingCartData(int orderid, int productid, String color, String size)
    {
        long rid;
        SQLiteDatabase db = open();
        ContentValues cVal = new ContentValues();

        cVal.put(LacyConstants.TABLE_PRODUCTORDER_ORDER_ID, orderid);
        cVal.put(LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID, productid);
        cVal.put(LacyConstants.TABLE_PRODUCTORDER_COLOR, color);
        cVal.put(LacyConstants.TABLE_PRODUCTORDER_SIZE, size);

        rid = db.insert(LacyConstants.TABLE_PRODUCTORDER, null, cVal);
        return rid;
    }

    public static void removeShoppingCartItem(int porderid)
    {
        SQLiteDatabase db = open();
        ContentValues values = new ContentValues();

        values.put(LacyConstants.TABLE_PRODUCTORDER_ALREADYCHECKEDOUT, "true");
        db.update(LacyConstants.TABLE_PRODUCTORDER, values, LacyConstants.TABLE_PRODUCTORDER_ID + "=" + porderid, null);
    }

    public static void setCartItemsCheckedOut(int orderid)
    {
        SQLiteDatabase db = open();
        ContentValues values = new ContentValues();

        values.put(LacyConstants.TABLE_PRODUCTORDER_ALREADYCHECKEDOUT, "true");
        db.update(LacyConstants.TABLE_PRODUCTORDER, values, LacyConstants.TABLE_PRODUCTORDER_ID + "=" + orderid, null);
    }

    public static void updateOrderData(int orderid,long billingId, long shippingId)
    {
        SQLiteDatabase db = open();
        ContentValues values = new ContentValues();

        values.put(LacyConstants.TABLE_ORDERS_BILLING_ID, billingId);
        values.put(LacyConstants.TABLE_ORDERS_SHIPPING_ID, shippingId);
        db.update(LacyConstants.TABLE_ORDERS, values, LacyConstants.TABLE_ORDERS_ID + "=" + orderid, null);
    }

    public static void updateProductQuantity(int porderid, int qty)
    {
        SQLiteDatabase db = open();
        ContentValues values = new ContentValues();
        values.put(LacyConstants.TABLE_PRODUCTORDER_QUANTITY, qty);
        db.update(LacyConstants.TABLE_PRODUCTORDER, values, LacyConstants.TABLE_PRODUCTORDER_ID + "=" + porderid, null);
    }

    public static double getShoppingCartTotal(int userid, int orderid, int method)
    {
        SQLiteDatabase db = open();
        double total = 0.0;
        Cursor c;
        //Determine the method to get the shopping cart total.
        if (method == 0){
            c = db.rawQuery("SELECT SUM((" + LacyConstants.TABLE_PRODUCT_PRICE + " - (" + LacyConstants.TABLE_PRODUCT_PRICE + " * " +
                    LacyConstants.TABLE_PRODUCT_DISCOUNT + ")) * " + LacyConstants.TABLE_PRODUCTORDER_QUANTITY + " ) as TOTAL FROM " +
                    LacyConstants.TABLE_PRODUCTORDER + " AS po LEFT JOIN " + LacyConstants.TABLE_ORDERS + " AS o ON (po." +
                    LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + " = o." + LacyConstants.TABLE_ORDERS_ID + ") LEFT JOIN " +
                    LacyConstants.TABLE_PRODUCT + " AS p ON (po." + LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID + " = p." +
                    LacyConstants.TABLE_PRODUCT_ID + ") WHERE (po." + LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + " = '" +
                    orderid + "') AND (po." + LacyConstants.TABLE_PRODUCTORDER_ALREADYCHECKEDOUT + " = 'false')", null);
            if (c.moveToFirst()){
                total = c.getDouble(c.getColumnIndex("TOTAL"));
            }
        } else if (method == 1) {
            c = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_ORDERS + "  WHERE (" +
                    LacyConstants.TABLE_ORDERS_ACCOUNT_ID + " = '" + userid + "' AND  " +
                    LacyConstants.TABLE_ORDERS_ID + " = '" + orderid + "')", null);
            if (c.moveToFirst()) {
                double orderTotal = c.getDouble(c.getColumnIndex(LacyConstants.TABLE_ORDERS_TOTAL));
                if (orderTotal > 0) {
                    total = orderTotal;
                }
                else {
                    total = getShoppingCartTotal(userid, orderid, 0);
                }
            }
        }

        return total;
    }

    public static double getPrice(int porderid)
    {
        SQLiteDatabase db = open();
        double p = 0.0;
        Cursor c = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_PRODUCTORDER + " AS po LEFT JOIN " +
                LacyConstants.TABLE_ORDERS + " AS o ON (po." + LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + " = o." +
                LacyConstants.TABLE_ORDERS_ID + ") LEFT JOIN " + LacyConstants.TABLE_PRODUCT + " AS p ON (po." +
                LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID + " = p." + LacyConstants.TABLE_PRODUCT_ID + ") WHERE (po."+
                LacyConstants.TABLE_PRODUCTORDER_ID + " = '" + porderid + "') AND (po." + LacyConstants.TABLE_PRODUCTORDER_ALREADYCHECKEDOUT
                + " = 'false')",null);
        if (c.moveToFirst()) {
            do {
                double price = c.getDouble(c.getColumnIndex(LacyConstants.TABLE_PRODUCT_PRICE));
                double discount = c.getDouble(c.getColumnIndex(LacyConstants.TABLE_PRODUCT_DISCOUNT));
                p = (price - (price * discount));
            }while (c.moveToNext());
        }
        return p;
    }

    public static int getQuantity(int porderid)
    {
        SQLiteDatabase db = open();
        int p = 0;
        //Select statement to get where order id matches and checkedout = false
        Cursor c = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_PRODUCTORDER + " WHERE ("+
                LacyConstants.TABLE_PRODUCTORDER_ID + " = '" + porderid + "') AND (" +
                LacyConstants.TABLE_PRODUCTORDER_ALREADYCHECKEDOUT + " = 'false')",null);
        if (c.moveToFirst()) {
            do {
                int qty = c.getInt(c.getColumnIndex(LacyConstants.TABLE_PRODUCTORDER_QUANTITY));
                p = qty;
            }while (c.moveToNext());
        }
        return p;
    }

    public static Cursor getReviewData(int productID)
    {
        SQLiteDatabase db = open();

        //Select the reviews
        Cursor c = db.rawQuery("SELECT rowid _id, * FROM " + LacyConstants.TABLE_REVIEW + "  WHERE (" +
                LacyConstants.TABLE_REVIEW_PRODUCT_ID + " = '" + productID + "')", null);
        if (c.moveToFirst()) {
            do {
                return c;
            }while (c.moveToNext());
        }

        return null;
    }

    public static long addReviewData(String name, int productid, double rating, String message)
    {
        long rid;
        SQLiteDatabase db = open();
        ContentValues cVal = new ContentValues();
        cVal.put(LacyConstants.TABLE_REVIEW_NAME,name);
        cVal.put(LacyConstants.TABLE_REVIEW_RATING,rating);
        cVal.put(LacyConstants.TABLE_REVIEW_MESSAGE,message);
        cVal.put(LacyConstants.TABLE_REVIEW_PRODUCT_ID,productid);
        //Learned that the insert function returns -1 if an error occurs. So making sure no error occured before doing the next query
        rid = db.insert(LacyConstants.TABLE_REVIEW, null, cVal);
        return rid;
    }

    public static void updateReview(int productid)
    {
        SQLiteDatabase db = open();
        double rating = 0.0;
        //Select the last 5 reviews to be made
        Cursor c = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_REVIEW + "  WHERE (" +
                LacyConstants.TABLE_REVIEW_PRODUCT_ID + " = '" + productid + "')", null);
        if (c.moveToFirst()) {
            do {
                //Add all the ratings
                rating += c.getDouble(c.getColumnIndex(LacyConstants.TABLE_REVIEW_RATING));

            }while (c.moveToNext());
        }

        //Get the rating average
        rating = rating/c.getCount();

        ContentValues values = new ContentValues();

        String formattedRating = String.format("%.2f", rating);

        values.put(LacyConstants.TABLE_PRODUCT_AVGRATING, formattedRating);
        db.update(LacyConstants.TABLE_PRODUCT, values, LacyConstants.TABLE_PRODUCT_ID + "=" + productid, null);
    }

    public static void updateOrderID(int userid, int orderid)
    {
        SQLiteDatabase db = open();
        ContentValues values = new ContentValues();

        values.put(LacyConstants.TABLE_ACCOUNT_CURRENT_ORDER_ID, orderid);
        db.update(LacyConstants.TABLE_ACCOUNT, values, LacyConstants.TABLE_ACCOUNT_ID + "=" + userid, null);

    }

    public static void updateOrderTotal(int orderid, double total)
    {
        SQLiteDatabase db = open();
        ContentValues values = new ContentValues();
        values.put(LacyConstants.TABLE_ORDERS_TOTAL, total);
        db.update(LacyConstants.TABLE_ORDERS, values, LacyConstants.TABLE_ORDERS_ID + "=" + orderid, null);
    }

    public static int getOrderID(int userid)
    {
        SQLiteDatabase db = open();
        //Select current order id from account table
        Cursor c = db.rawQuery("SELECT " + LacyConstants.TABLE_ACCOUNT_CURRENT_ORDER_ID + " FROM " + LacyConstants.TABLE_ACCOUNT +
                "  WHERE (" + LacyConstants.TABLE_ACCOUNT_ID + " = '" + userid + "')", null);

        int orderid = 0;
        //User has a current order id
        if (c.moveToFirst()) {
            int currentOrderID,dbOrderID = 0,dbProductOrderID = 0;
            currentOrderID = c.getInt(c.getColumnIndex(LacyConstants.TABLE_ACCOUNT_CURRENT_ORDER_ID));
            //Checks if user has a current order id
            if (currentOrderID > 0) {
                //Select order id from the order table
                Cursor c2 = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_ORDERS + "  WHERE (" +
                        LacyConstants.TABLE_ORDERS_ACCOUNT_ID + " = '" + userid + "')", null);
                //Exists a row with the current order id in order table
                if (c2.getCount() > 0) {
                    //Select order id from the productorder table
                    Cursor c3 = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_PRODUCTORDER + "  WHERE (" +
                            LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + " = '" + currentOrderID + "' AND " +
                            LacyConstants.TABLE_PRODUCTORDER_ALREADYCHECKEDOUT + " = 'false')", null);
                    //Has order id and a product in cart with the id.
                    if (c3.getCount() > 0) {
                        orderid = currentOrderID;
                    }else{
                        //Has an order id, but no products in cart, keep the same id
                        orderid = currentOrderID;
                    }
                 //Has order id, but no items in cart so create a new order id
                }else {
                    //Add order to order table
                    long rid = addOrderID(userid);
                    //Make sure the created order id matches the current order id
                    if (rid == currentOrderID)
                        orderid = currentOrderID;
                }
              //User does not have a current order id. Meaning a new account/never added to cart
            }else{
                //User does not have an order id set
                c = db.rawQuery("SELECT " + LacyConstants.TABLE_ACCOUNT_CURRENT_ORDER_ID + " FROM " + LacyConstants.TABLE_ACCOUNT +
                        " ORDER BY " + LacyConstants.TABLE_ACCOUNT_CURRENT_ORDER_ID + " DESC", null);

                if (c.moveToFirst()) {
                    //Add order to order table
                    long rid = addOrderID(userid);
                    //Get the highest order id
                    int highestid = c.getInt(c.getColumnIndex(LacyConstants.TABLE_ACCOUNT_CURRENT_ORDER_ID));
                    //Add 1
                    highestid++;
                    //Make sure the created order id matches the highest order id
                    if (rid == highestid)
                        orderid = highestid;
                }
            }
        }
        updateOrderID(userid,orderid);
        return orderid;
    }

    public static Cursor getBillingData(int orderid)
    {
        SQLiteDatabase db = open();

        Cursor c = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_ORDERS +
                " AS o LEFT JOIN " + LacyConstants.TABLE_BILLING + " AS b ON (o." + LacyConstants.TABLE_ORDERS_BILLING_ID +
                " = b." + LacyConstants.TABLE_BILLING_ID + ") LEFT JOIN " + LacyConstants.TABLE_CHECKOUT + " AS c ON (o."
                + LacyConstants.TABLE_ORDERS_BILLING_ID + " = c." + LacyConstants.TABLE_CHECKOUT_BILLING_ID + ")" +
                " WHERE (" + LacyConstants.TABLE_ORDERS_ID + " = '" + orderid + "')", null);
        if (c.moveToFirst()) {
            do {
                return c;
            }while (c.moveToNext());
        }

        return null;
    }

    public static Cursor getShippingData(int orderid)
    {
        SQLiteDatabase db = open();

        Cursor c = db.rawQuery("SELECT * FROM " + LacyConstants.TABLE_ORDERS +
                " AS o LEFT JOIN " + LacyConstants.TABLE_SHIPPING + " AS s ON (o." + LacyConstants.TABLE_ORDERS_SHIPPING_ID +
                " = s." + LacyConstants.TABLE_SHIPPING_ID + ") LEFT JOIN " + LacyConstants.TABLE_CHECKOUT + " AS c ON (o."
                + LacyConstants.TABLE_ORDERS_SHIPPING_ID + " = c." + LacyConstants.TABLE_CHECKOUT_SHIPPING_ID + ")" +
                " WHERE (" + LacyConstants.TABLE_ORDERS_ID + " = '" + orderid + "')", null);
        if (c.moveToFirst()) {
            do {
                return c;
            }while (c.moveToNext());
        }

        return null;
    }

    public static ArrayList<String[]> getPurchasedProducts(int orderid)
    {
        String[] products;
        ArrayList<String[]> data = new ArrayList<>();
        SQLiteDatabase db = open();
        Cursor c = db.rawQuery("SELECT po." + LacyConstants.TABLE_PRODUCTORDER_QUANTITY + ", p." + LacyConstants.TABLE_PRODUCT_NAME +
                " FROM " + LacyConstants.TABLE_PRODUCTORDER + " AS po LEFT JOIN " + LacyConstants.TABLE_PRODUCT +
                " AS p ON (po." + LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID + " = p." + LacyConstants.TABLE_PRODUCT_ID +
                ") WHERE (po."+ LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + " = '"  + orderid + "') AND (po." +
                LacyConstants.TABLE_PRODUCTORDER_ALREADYCHECKEDOUT  + " = 'true')", null);
        if (c.moveToFirst()) {
            do {
                products = new String[2];
                products[0] = c.getString(c.getColumnIndex(LacyConstants.TABLE_PRODUCT_NAME));
                products[1] = c.getString(c.getColumnIndex(LacyConstants.TABLE_PRODUCTORDER_QUANTITY));
                data.add(products);
            }while (c.moveToNext());
        }

        return data;
    }

    public static Cursor getOrderData(int userID)
    {
        SQLiteDatabase db = open();
        Cursor c = db.rawQuery("SELECT po.rowid _id, * FROM " + LacyConstants.TABLE_PRODUCTORDER + " AS po LEFT JOIN " +
                LacyConstants.TABLE_ORDERS + " AS o ON (po." + LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + " = o." +
                LacyConstants.TABLE_ORDERS_ID + ") LEFT JOIN " + LacyConstants.TABLE_PRODUCT + " AS p ON (po." +
                LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID + " = p." + LacyConstants.TABLE_PRODUCT_ID + ")" +
                " LEFT JOIN " + LacyConstants.TABLE_ACCOUNT + " AS a ON (a." + LacyConstants.TABLE_ACCOUNT_ID +
                " = o." +  LacyConstants.TABLE_ORDERS_ACCOUNT_ID + ") WHERE (o." + LacyConstants.TABLE_ORDERS_ACCOUNT_ID +
                " = '" + userID + "') AND " + "(po." + LacyConstants.TABLE_PRODUCTORDER_ALREADYCHECKEDOUT + " = 'true')", null);
        if (c.moveToFirst()) {
            do {
                return c;
            }while (c.moveToNext());
        }

        return null;
    }

    public static void updateAccountOrderId(int userid)
    {
        SQLiteDatabase db = open();
        ContentValues values = new ContentValues();

        long orderid = addOrderID(userid);

        values.put(LacyConstants.TABLE_ACCOUNT_CURRENT_ORDER_ID, orderid);
        db.update(LacyConstants.TABLE_ACCOUNT, values, LacyConstants.TABLE_ACCOUNT_ID + "=" + userid, null);

        System system = System.getInstance();
        system.setOrderID((int)orderid);
    }
}



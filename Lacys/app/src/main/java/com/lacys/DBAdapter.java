package com.lacys;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBAdapter{
    private static final boolean DEBUG = true;
    private static final String LOG_TAG = "LacyDB";
    private static DBHelper DBHelper = null;
    private static Context contxt;

    public DBAdapter(Context context){
        contxt = context;
    }

    //Initialize/Open database
    public static void init(){
        if (getDBHelper() == null){
            if (DEBUG)
                Log.i(LOG_TAG, contxt.toString());
            DBHelper = new DBHelper(contxt);
        }
    }

    //Return DBHelper object
    public static DBHelper getDBHelper(){
        return DBHelper;
    }
    //Return DBHelper object
    public static boolean getDEBUG(){
        return DEBUG;
    }
	
    //Return LOGTAG object
    public static String getLogTag(){
        return LOG_TAG;
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
                    " VARCHAR, " + LacyConstants.TABLE_ACCOUNT_LASTNAME + " VARCHAR, " + LacyConstants.TABLE_ACCOUNT_BILLING_ID + " INTEGER, " + LacyConstants.TABLE_ACCOUNT_SHIPPING_ID + " INTEGER);";

            //Query to Create Account Checkout Table
            String CREATE_TABLE_ACCOUNTCHECKOUT = "CREATE TABLE " + LacyConstants.TABLE_ACCOUNTCHECKOUT + " (" + LacyConstants.TABLE_ACCOUNTCHECKOUT_ACCOUNT_ID + " INTEGER," +
                    LacyConstants.TABLE_ACCOUNTCHECKOUT_CHECKOUT_ID + " INTEGER, " + LacyConstants.TABLE_ACCOUNTCHECKOUT_TYPE + " VARCHAR);";

            //Query to Create Billing Table
            String CREATE_TABLE_BILLING = "CREATE TABLE " + LacyConstants.TABLE_BILLING + " (" + LacyConstants.TABLE_BILLING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_BILLING_PURCHASEDATE + " DATETIME, " + LacyConstants.TABLE_BILLING_CARDTYPE + " VARCHAR, " + LacyConstants.TABLE_BILLING_CARDNUMBER + " VARCHAR, " +
                    LacyConstants.TABLE_BILLING_CARDEXPIRATION + " DATETIME, " + LacyConstants.TABLE_BILLING_CARDCODE + " INTEGER);";

            //Query to Create Cart Table
            String CREATE_TABLE_CART = "CREATE TABLE " + LacyConstants.TABLE_CART + " (" + LacyConstants.TABLE_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_CART_ACCOUNT_ID + " INTEGER);";

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
            String CREATE_TABLE_PRODUCTORDER = "CREATE TABLE " + LacyConstants.TABLE_PRODUCTORDER + " (" + LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + " INTEGER," +
                    LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID + " INTEGER, " + LacyConstants.TABLE_PRODUCTORDER_CART_ID + " INTEGER, " + LacyConstants.TABLE_PRODUCTORDER_QUANTITY +
                    " INTEGER," + LacyConstants.TABLE_PRODUCTORDER_COLOR + " VARCHAR, " + LacyConstants.TABLE_PRODUCTORDER_SIZE + " VARCHAR, FOREIGN KEY(" +
                    LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + ") REFERENCES " + LacyConstants.TABLE_ORDERS + "(" + LacyConstants.TABLE_ORDERS_ID + "), FOREIGN KEY(" +
                    LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID + ") REFERENCES " + LacyConstants.TABLE_PRODUCT + "(" + LacyConstants.TABLE_PRODUCT_ID + "), FOREIGN KEY(" +
                    LacyConstants.TABLE_PRODUCTORDER_CART_ID + ") REFERENCES " + LacyConstants.TABLE_CART + "(" + LacyConstants.TABLE_CART_ID + "));";

            //Query to Create Review Table
            String CREATE_TABLE_REVIEW = "CREATE TABLE " + LacyConstants.TABLE_REVIEW + " (" + LacyConstants.TABLE_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_REVIEW_EMAIL + " VARCHAR, " + LacyConstants.TABLE_REVIEW_RATING + " DOUBLE NOT NULL DEFAULT 0.0, " + LacyConstants.TABLE_REVIEW_MESSAGE +
                    " TEXT, " + LacyConstants.TABLE_REVIEW_PRODUCT_ID + " INTEGER);";

            //Query to Create Shipping Table
            String CREATE_TABLE_SHIPPING = "CREATE TABLE " + LacyConstants.TABLE_SHIPPING + " (" + LacyConstants.TABLE_SHIPPING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_SHIPPING_DATE + " DATETIME, " + LacyConstants.TABLE_SHIPPING_ARRIVALDATE + " DATETIME, " + LacyConstants.TABLE_SHIPPING_COST + " DOUBLE);";

            try {
                //Execute the queries to the database
                db.execSQL(CREATE_TABLE_ACCOUNT);
                db.execSQL(CREATE_TABLE_ACCOUNTCHECKOUT);
                db.execSQL(CREATE_TABLE_BILLING);
                db.execSQL(CREATE_TABLE_CART);
                db.execSQL(CREATE_TABLE_CHECKOUT);
                db.execSQL(CREATE_TABLE_CLOTHINGORDER);
                db.execSQL(CREATE_TABLE_ORDERS);
                db.execSQL(CREATE_TABLE_PRODUCT);
                db.execSQL(CREATE_TABLE_PRODUCTORDER);
                db.execSQL(CREATE_TABLE_REVIEW);
                db.execSQL(CREATE_TABLE_SHIPPING);

				String products[][] = {
                        //name, description, price, discount, rating, isClothing, imgIndex, category
						{"Men's Shirt 1","Product description to be done at a later date....","10.0","0.2","4.0","true","3","Men Shirts"},
						{"Men's Shirt 2","Product description to be done at a later date....","20.0","0.25","5.0","true","1","Men Shirts"},
						{"Men's Shirt 3","Product description to be done at a later date....","30.0","0.2","3.0","true","3","Men Shirts"},
						{"Men's Shirt 4","Product description to be done at a later date....","40.0","0.3","2.0","true","2","Men Shirts"},
						{"Men's Shirt 5","Product description to be done at a later date....","50.0","0.4","4.5","true","1","Men Shirts"},
						{"Men's Shirt 6","Product description to be done at a later date....","60.0","0.35","5.0","true","1","Men Shirts"},
						{"Men's Pant 1","Product description to be done at a later date....","10.0","0.0","4.0","true","0","Men Pants"},
						{"Men's Pant 2","Product description to be done at a later date....","20.0","0.0","5.0","true","0","Men Pants"},
						{"Men's Pant 3","Product description to be done at a later date....","30.0","0.0","3.0","true","0","Men Pants"},
						{"Men's Pant 4","Product description to be done at a later date....","40.0","0.0","2.0","true","0","Men Pants"},
						{"Men's Pant 5","Product description to be done at a later date....","50.0","0.0","4.5","true","0","Men Pants"},
						{"Men's Pant 6","Product description to be done at a later date....","60.0","0.0","5.0","true","0","Men Pants"},
						{"Men's Pant 7","Product description to be done at a later date....","70.0","0.0","1.0","true","0","Men Pants"},
						{"Women's Skirt 1","Product description to be done at a later date....","10.0","0.0","1.0","true","0","Women Skirts"},
						{"Women's Skirt 2","Product description to be done at a later date....","12.0","0.0","1.0","true","0","Women Skirts"},
						{"Women's Skirt 3","Product description to be done at a later date....","14.0","0.0","1.0","true","0","Women Skirts"},
						{"Women's Skirt 4","Product description to be done at a later date....","16.0","0.0","1.0","true","0","Women Skirts"},
						{"Women's Skirt 5","Product description to be done at a later date....","19.0","0.0","1.0","true","0","Women Skirts"},
						{"Women's Shirts 1","Product description to be done at a later date....","10.0","0.0","1.0","true","0","Women Shirts"},
						{"Women's Shirts 2","Product description to be done at a later date....","12.0","0.0","1.0","true","0","Women Shirts"},
						{"Women's Shirts 3","Product description to be done at a later date....","14.0","0.0","1.0","true","0","Women Shirts"},
						{"Women's Shirts 4","Product description to be done at a later date....","16.0","0.0","1.0","true","0","Women Shirts"},
						{"Women's Shirts 5","Product description to be done at a later date....","19.0","0.0","1.0","true","0","Women Shirts"},
						{"Women's Pants 1","Product description to be done at a later date....","10.0","0.0","1.0","true","0","Women Pants"},
						{"Women's Pants 2","Product description to be done at a later date....","12.0","0.0","1.0","true","0","Women Pants"},
						{"Women's Pants 3","Product description to be done at a later date....","14.0","0.0","1.0","true","0","Women Pants"},
						{"Women's Pants 4","Product description to be done at a later date....","16.0","0.0","1.0","true","0","Women Pants"},
						{"Women's Pants 5","Product description to be done at a later date....","19.0","0.0","1.0","true","0","Women Pants"},
						{"Tool Kit","Product description to be done at a later date....","10.0","0.0","1.0","false","0","Home Essentials"},
						{"Light Bulbs","Product description to be done at a later date....","12.0","0.0","1.0","false","0","Home Essentials"},
						{"Towels","Product description to be done at a later date....","14.0","0.0","1.0","false","0","Home Essentials"},
						{"Sofa Cushion","Product description to be done at a later date....","16.0","0.0","1.0","false","0","Home Essentials"},
						{"Table Cloth","Product description to be done at a later date....","19.0","0.0","1.0","false","0","Home Essentials"},
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

            } catch (Exception exception) {
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed, all data will be gone
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_ACCOUNT);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_ACCOUNTCHECKOUT);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_BILLING);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_CART);
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
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_ACCOUNTCHECKOUT);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_BILLING);
            db.execSQL("DROP TABLE IF EXISTS " + LacyConstants.TABLE_CART);
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


    public static boolean accountExists(String email)
    {
        String[] projection = {LacyConstants.TABLE_ACCOUNT_EMAIL};
        //set up the selection criteria
        String selection = LacyConstants.TABLE_ACCOUNT_EMAIL + " = ?";
        String[] selectionArguments = {email};
        //set up remaining parameters
        String groupBy = null;
        String having = null;
        String sortOrder = null;

        SQLiteDatabase db = open();
        // do the query and get a cursor
        Cursor cursor = db.query(LacyConstants.TABLE_ACCOUNT,
                projection, selection, selectionArguments, groupBy, having, sortOrder);
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
		String[] projection = {LacyConstants.TABLE_ACCOUNT_ID
                , LacyConstants.TABLE_ACCOUNT_FIRSTNAME
                , LacyConstants.TABLE_ACCOUNT_LASTNAME
                , LacyConstants.TABLE_ACCOUNT_EMAIL
                , LacyConstants.TABLE_ACCOUNT_PASSWORD
        };
        //set up the selection criteria
        String selection = LacyConstants.TABLE_ACCOUNT_EMAIL + " = ?";
        String[] selectionArguments = {email};
        //set up remaining parameters
        String groupBy = null;
        String having = null;
        String sortOrder = null;
		
		SQLiteDatabase db = open();
        // do the query and get a cursor
        Cursor cursor = db.query(LacyConstants.TABLE_ACCOUNT,
                projection, selection, selectionArguments, groupBy, having, sortOrder);
        //process the result and show the returned values in the log
        if (cursor.moveToFirst()) {
			do {
				return cursor;
           } while (cursor.moveToNext());
        }
        return null;
    }

    public static Cursor createAccount(String email, String password, String first, String last)
    {
        SQLiteDatabase db = open();
        ContentValues cVal = new ContentValues();
        cVal.put(LacyConstants.TABLE_ACCOUNT_EMAIL,email);
        cVal.put(LacyConstants.TABLE_ACCOUNT_PASSWORD,password);
        cVal.put(LacyConstants.TABLE_ACCOUNT_FIRSTNAME,first);
        cVal.put(LacyConstants.TABLE_ACCOUNT_LASTNAME,last);
        db.insert(LacyConstants.TABLE_ACCOUNT, null, cVal);
        return null;
    }
	
	public static ArrayList<String[]> getProducts(String category)
    {
        String[] products;
        ArrayList<String[]> data = new ArrayList<>();
        //set up the selection criteria
        String selection = LacyConstants.TABLE_PRODUCT_CATEGORY + " = ?";
        String[] selectionArguments = {category};
        //set up remaining parameters
        String groupBy = null;
        String having = null;
        String sortOrder = null;
		
		SQLiteDatabase db = open();
        // do the query and get a cursor
        Cursor cursor = db.query(LacyConstants.TABLE_PRODUCT,
                null, selection, selectionArguments, groupBy, having, sortOrder);
        //process the result and show the returned values in the log
        if (cursor.moveToFirst()) {
			do {
                products = new String[6];
				products[0] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_NAME));
				products[1] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_IMGINDEX));
				products[2] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_PRICE));
				products[3] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_DISCOUNT));
				products[4] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_DESCRIPTION));
				products[5] = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_AVGRATING));
                data.add(products);
           } while (cursor.moveToNext());
        }
        return data;
    }
}

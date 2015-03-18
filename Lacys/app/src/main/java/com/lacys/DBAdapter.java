package com.lacys;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Random;

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
					LacyConstants.TABLE_CHECKOUT_ZIPCODE + " INTEGER, " + LacyConstants.TABLE_CHECKOUT_STATE + " VARCHAR, " + LacyConstants.TABLE_CHECKOUT_ACCOUNT_ID + " INTEGER, " + 
					LacyConstants.TABLE_CHECKOUT_BILLING_ID + " INTEGER, " + LacyConstants.TABLE_CHECKOUT_SHIPPING_ID + " INTEGER, " + "FOREIGN KEY(" + LacyConstants.TABLE_CHECKOUT_ACCOUNT_ID + 
					") " + "REFERENCES " + LacyConstants.TABLE_ACCOUNT + " (" + LacyConstants.TABLE_ACCOUNT_ID + "));";
			
            //Query to Create ClothingOrder Table
            String CREATE_TABLE_CLOTHINGORDER = "CREATE TABLE " + LacyConstants.TABLE_CLOTHINGORDER + " (" + LacyConstants.TABLE_CLOTHINGORDER_PRODUCTORDER_ID + " INTEGER," +
                    LacyConstants.TABLE_CLOTHINGORDER_COLOR + " VARCHAR, " + LacyConstants.TABLE_CLOTHINGORDER_SIZE + " VARCHAR);";

            //Query to Create Orders Table
            String CREATE_TABLE_ORDERS = "CREATE TABLE " + LacyConstants.TABLE_ORDERS + " (" + LacyConstants.TABLE_ORDERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_ORDERS_ACCOUNT_ID + " INTEGER, " + LacyConstants.TABLE_ORDERS_SHIPPING_ID + " INTEGER, " + LacyConstants.TABLE_ORDERS_BILLING_ID + " INTEGER);";

            //Query to Create Product Table
            String CREATE_TABLE_PRODUCT = "CREATE TABLE " + LacyConstants.TABLE_PRODUCT + " (" + LacyConstants.TABLE_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_PRODUCT_NAME + " VARCHAR, " + LacyConstants.TABLE_PRODUCT_DESCRIPTION + " TEXT, " + LacyConstants.TABLE_PRODUCT_PRICE +
                    " DOUBLE NOT NULL DEFAULT 0.0, " + LacyConstants.TABLE_PRODUCT_DISCOUNT + " DOUBLE, " + LacyConstants.TABLE_PRODUCT_AVGRATING + " DOUBLE NOT NULL DEFAULT 0.0, " +
                    LacyConstants.TABLE_PRODUCT_ISCLOTHING + "  BOOL NOT NULL DEFAULT false, " + LacyConstants.TABLE_PRODUCT_CATEGORY + " VARCHAR);";

            //Query to Create Product Order Table
            String CREATE_TABLE_PRODUCTORDER = "CREATE TABLE " + LacyConstants.TABLE_PRODUCTORDER + " (" + LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + " INTEGER," +
                    LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID + " INTEGER, " + LacyConstants.TABLE_PRODUCTORDER_QUANTITY + " INTEGER, FOREIGN KEY(" + LacyConstants.TABLE_PRODUCTORDER_ORDER_ID + ") REFERENCES " + LacyConstants.TABLE_ORDERS +
                    "(" + LacyConstants.TABLE_ORDERS_ID + "), FOREIGN KEY(" + LacyConstants.TABLE_PRODUCTORDER_PRODUCT_ID + ") REFERENCES " + LacyConstants.TABLE_PRODUCT +
                    "(" + LacyConstants.TABLE_PRODUCT_ID + "));";

            //Query to Create Review Table
            String CREATE_TABLE_REVIEW = "CREATE TABLE " + LacyConstants.TABLE_REVIEW + " (" + LacyConstants.TABLE_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    LacyConstants.TABLE_REVIEW_EMAIL + " VARCHAR, " + LacyConstants.TABLE_REVIEW_RATING + " DOUBLE NOT NULL DEFAULT 0.0, " + LacyConstants.TABLE_REVIEW_MESSAGE +
                    " TEXT, " + LacyConstants.TABLE_REVIEW_PRODUCT_ID + " INTEGER);";

            //Query to Create Shipping Table
            String CREATE_TABLE_SHIPPING = "CREATE TABLE " + LacyConstants.TABLE_SHIPPING + " (" + LacyConstants.TABLE_SHIPPING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
					LacyConstants.TABLE_SHIPPING_DATE + " DATETIME, " + LacyConstants.TABLE_SHIPPING_ARRIVALDATE + " DATETIME, " + LacyConstants.TABLE_SHIPPING_COST + " DOUBLE);";


            if (DEBUG)
                Log.i(LOG_TAG, "new create");
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
            } catch (Exception exception) {
                if (DEBUG)
                    Log.i(LOG_TAG, "Exception onCreate() exception");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            if (DEBUG)
                Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + "...");
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

            if (DEBUG)
                Log.w(LOG_TAG, "Downgrading database from version " + oldVersion + " to " + newVersion + "...");
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


    public static void addProducts()
    {
        //name, description, price, discount, rating, isClothing, category
        String products[][] = {
                {"Men Pants 1","....","10.0","0.0","4.0","true","Men Pants"},
                {"Men Pants 2","....","20.0","0.0","5.0","true","Men Pants"},
                {"Men Pants 3","....","30.0","0.0","3.0","true","Men Pants"},
                {"Men Pants 4","....","40.0","0.0","2.0","true","Men Pants"},
                {"Men Pants 5","....","50.0","0.0","4.5","true","Men Pants"},
                {"Men Pants 6","....","60.0","0.0","5.0","true","Men Pants"},
                {"Men Pants 7","....","70.0","0.0","1.0","true","Men Pants"},
        };
        if (DEBUG)
            Log.i(LOG_TAG, "In add products");
        SQLiteDatabase db = open();
        ContentValues cVal = new ContentValues();
        for (String p[] : products) {
            if (DEBUG)
                Log.i(LOG_TAG, "Adding..: NAME: " + p[0] + " DESC: " + p[1] + " PRICE: " + p[2] + " DISC: " + p[3] + " AVG: " + p[4] + " CLOTH: " + p[5] + " CATE: " + p[6]);
            cVal.put(LacyConstants.TABLE_PRODUCT_NAME, p[0]);
            cVal.put(LacyConstants.TABLE_PRODUCT_DESCRIPTION, p[1]);
            cVal.put(LacyConstants.TABLE_PRODUCT_PRICE, p[2]);
            cVal.put(LacyConstants.TABLE_PRODUCT_DISCOUNT, p[3]);
            cVal.put(LacyConstants.TABLE_PRODUCT_AVGRATING, p[4]);
            cVal.put(LacyConstants.TABLE_PRODUCT_ISCLOTHING, p[5]);
            cVal.put(LacyConstants.TABLE_PRODUCT_CATEGORY, p[6]);
            db.insert(LacyConstants.TABLE_PRODUCT, null, cVal);
        }
        //db.close(); // Closing database connection
    }

    public static Cursor login(String email, String password)
    {
		String[] projection = {LacyConstants.TABLE_ACCOUNT_ID
                , LacyConstants.TABLE_ACCOUNT_FIRSTNAME
                , LacyConstants.TABLE_ACCOUNT_LASTNAME
                , LacyConstants.TABLE_ACCOUNT_EMAIL};
        //set up the selection criteria
        String selection = LacyConstants.TABLE_ACCOUNT_EMAIL + " = ? AND " + LacyConstants.TABLE_ACCOUNT_PASSWORD + " = ?";
        String[] selectionArguments = {email,password};
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

    public static void createAccount(String email, String password)
    {
        if (DEBUG)
            Log.i(LOG_TAG, "Creating Account");
        SQLiteDatabase db = open();
        ContentValues cVal = new ContentValues();

        //generate random first/last name
        String fnames[] = {"Andrea","Mike","Joey","Luke","Lucy","Alex"};
        String lnames[] = {"Washington","Johnson","Woodrin","Robinson","Walker","Smith"};
        Random random = new Random();
        int rndIndex = random.nextInt(6);
        String rndfName = fnames[rndIndex];
        String rndlName = lnames[rndIndex];

        if (DEBUG)
            Log.i(LOG_TAG, "Adding..: EMAIL: " +email + " PASSWORD: "+password+ " FIRSTNAME: "+rndfName+ " LASTNAME: "+rndlName);
        cVal.put(LacyConstants.TABLE_ACCOUNT_EMAIL,email);
        cVal.put(LacyConstants.TABLE_ACCOUNT_PASSWORD,password);
        cVal.put(LacyConstants.TABLE_ACCOUNT_FIRSTNAME,rndfName);
        cVal.put(LacyConstants.TABLE_ACCOUNT_LASTNAME,rndlName);
        db.insert(LacyConstants.TABLE_ACCOUNT, null, cVal);

    }
}

package com.lacys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Christina on 3/24/2015.
 */

//Temporarily be using this class as the order display
// until I get ViewOrdersFragment working
public class ViewOrdersActivity extends ActionBarActivity {
    private static DBAdapter db;
    private static System system;
    private static SimpleCursorAdapter viewOrderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_orders_fragment);
        db = new DBAdapter(this);
        db.init();

        system = System.getInstance();
        final int userID = system.getUserID();
        if(userID != 0) {
            //Obtains the order based on user id
            Cursor cs = db.getOrderData(userID);
            //Simple Cursor Adapter to fill up the listview
            viewOrderAdapter = new SimpleCursorAdapter(this,
                R.layout.view_orders_adapter,
                cs,
                    //This doesn't really matter since I am overriding it in the setViewBinder.
                new String[]{LacyConstants.TABLE_ORDERS_ID,
                        LacyConstants.TABLE_ORDERS_TOTAL,
                        LacyConstants.TABLE_ACCOUNT_FIRSTNAME,
                        LacyConstants.TABLE_ORDERS_ID,
                        LacyConstants.TABLE_ORDERS_ID,
                        LacyConstants.TABLE_ORDERS_ID,
                        LacyConstants.TABLE_ORDERS_ID,
                        LacyConstants.TABLE_ORDERS_ID,
                },
                new int[]{R.id.order_placed_date,
                        R.id.order_total,
                        R.id.ship_to_name,
                        R.id.order_arrival_date,
                        R.id.order_products_purchased,
                        R.id.order_shipping_address,
                        R.id.order_billing_address,
                        R.id.order_payment_card,
            });
            viewOrderAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int column) {
                        final int orderid = cursor.getInt(cursor.getColumnIndex(LacyConstants.TABLE_ORDERS_ID));
                        //View id matches order placed date
                        if (view.getId() == R.id.order_placed_date) {
                            TextView tv = (TextView) view;
                            Cursor c = db.getBillingData(orderid);
                            long placedDate = c.getLong(c.getColumnIndex(LacyConstants.TABLE_BILLING_PURCHASEDATE));
                            Date dateObj = new Date(placedDate);
                            DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
                            String formattedDate = df.format(dateObj);
                            tv.setText(formattedDate);
                        }
                        //View id matches order total
                        else if (view.getId() == R.id.order_total) {
                            TextView tv = (TextView) view;
                            double orderTotal = cursor.getDouble(cursor.getColumnIndex(LacyConstants.TABLE_ORDERS_TOTAL));

                            String formatted = String.format("%.2f", orderTotal);
                            tv.setText("$"+formatted);
                         //View id matches shipper name
                        } else if (view.getId() == R.id.ship_to_name) {
                            TextView tv = (TextView) view;
                            String fName = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_ACCOUNT_FIRSTNAME));
                            String lName = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_ACCOUNT_LASTNAME));

                            tv.setText(fName + " " + lName);
                        //View id matches the arrival date
                        } else if (view.getId() == R.id.order_arrival_date) {
                            TextView tv = (TextView) view;
                            Cursor c = db.getShippingData(orderid);
                            long arrivalDate = c.getLong(c.getColumnIndex(LacyConstants.TABLE_SHIPPING_ARRIVALDATE));
                            Date dateObj = new Date(arrivalDate);
                            DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
                            String formattedDate = df.format(dateObj);
                            tv.setText(formattedDate);
                        //View id matches the products purchased
                        } else if (view.getId() == R.id.order_products_purchased) {
                            TextView tv = (TextView) view;
                            ArrayList<String[]> products = db.getPurchasedProducts(orderid);
                            String product = "";
                            //For each products purchased concatenate
                            for (String p[] : products) {
                                String pName = p[0];
                                String pQty = p[1];
                                product += pName + " (" + pQty + ")\n";
                            }
                            tv.setText(product);
                        //View id matches the shipping address
                        } else if (view.getId() == R.id.order_shipping_address) {
                            TextView tv = (TextView) view;
                            Cursor c = db.getShippingData(orderid);
                            String addressLine1 = c.getString(c.getColumnIndex(LacyConstants.TABLE_CHECKOUT_ADDRESSLINE_1));
                            String addressLine2 = c.getString(c.getColumnIndex(LacyConstants.TABLE_CHECKOUT_ADDRESSLINE_2));
                            String city = c.getString(c.getColumnIndex(LacyConstants.TABLE_CHECKOUT_CITY));
                            String zip = c.getString(c.getColumnIndex(LacyConstants.TABLE_CHECKOUT_ZIPCODE));
                            String state = c.getString(c.getColumnIndex(LacyConstants.TABLE_CHECKOUT_STATE));

                            String address = addressLine1 + "\n";
                            if (!addressLine2.isEmpty())
                                address += addressLine2 + "\n";
                            address += city + ", " + state + " " + zip;
                            tv.setText(address);
                        //View id matches the billing address
                        } else if (view.getId() == R.id.order_billing_address) {
                            TextView tv = (TextView) view;
                            Cursor c = db.getBillingData(orderid);
                            String addressLine1 = c.getString(c.getColumnIndex(LacyConstants.TABLE_CHECKOUT_ADDRESSLINE_1));
                            String addressLine2 = c.getString(c.getColumnIndex(LacyConstants.TABLE_CHECKOUT_ADDRESSLINE_2));
                            String city = c.getString(c.getColumnIndex(LacyConstants.TABLE_CHECKOUT_CITY));
                            String zip = c.getString(c.getColumnIndex(LacyConstants.TABLE_CHECKOUT_ZIPCODE));
                            String state = c.getString(c.getColumnIndex(LacyConstants.TABLE_CHECKOUT_STATE));

                            String address = addressLine1 + "\n";
                            if (!addressLine2.isEmpty())
                                address += addressLine2 + "\n";
                            address += city + ", " + state + " " + zip;
                            tv.setText(address);
                        //View id matches the payment card
                        } else if (view.getId() == R.id.order_payment_card) {
                            TextView tv = (TextView) view;
                            Cursor c = db.getBillingData(orderid);
                            String cardType = c.getString(c.getColumnIndex(LacyConstants.TABLE_BILLING_CARDTYPE));
                            String cardNumb = c.getString(c.getColumnIndex(LacyConstants.TABLE_BILLING_CARDNUMBER));
                            //Get only the last 4 digits of the card
                            String last4digits = cardNumb.substring(cardNumb.length() - 4);
                            tv.setText(cardType + " ending in " + last4digits);
                        }
                        return true;
                    }
                }
            );

            ListView ordersListView = (ListView) findViewById(R.id.orders_list_view);
            ordersListView.setAdapter(viewOrderAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.contact_us:
                startActivity(new Intent(ViewOrdersActivity.this, ContactUs.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.home:
                startActivity(new Intent(ViewOrdersActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        //Close database
        db.close();
    }
}

package com.lacys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Christina on 3/9/2015.
 */
public class ShoppingCartScreen extends ActionBarActivity {
    private DBAdapter db;
    private System system;
    private double totalPrice = 0;

    SimpleCursorAdapter shoppingCartAdapter;

    //these change as soon as a shipping speed is clicked so that total
    //can be automatically updated. estArrival and shippingCost are
    //also used to make the shipping object. Static because they are
    //called by method in anonymous inner class OnItemClickListener
    private static ListView shippingListView;
    public static Calendar estArrival;
    public static double shippingCost;
    private static Context cartContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shopping_cart_screen);

        cartContext = this.getApplicationContext();


        //total is updated and estArrival and shippingCost change when
        //a speed is selected.
        shippingListView = (ListView) findViewById(R.id.shipping_speed_list);
        shippingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingCartScreen.changeShippingSpeed(position);
            }
        });

        db = new DBAdapter(this);
        db.init();

        system = System.getInstance();

        int userID = system.getUserID();
        if (userID != 0) {
            //Obtains the items in cart based on the userid that is logged in
            Cursor cs = db.getShoppingCartData(userID);

            //Simple Cursor Adapter to fill up the listview
            shoppingCartAdapter = new SimpleCursorAdapter(this,
                    R.layout.shopping_cart_adapter,
                    cs,
                    new String[]{LacyConstants.TABLE_PRODUCT_NAME,
                            LacyConstants.TABLE_PRODUCT_PRICE,
                            LacyConstants.TABLE_PRODUCT_DISCOUNT,
                            LacyConstants.TABLE_PRODUCT_IMGINDEX,
                    },
                    new int[]{R.id.shopping_cart_item_name,
                            R.id.new_price_shopping_cart,
                            R.id.old_price_shopping_cart,
                            R.id.shopping_cart_item,
                    });


            //Update the listview contents with database values
            shoppingCartAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int column) {
                    //If the column index matches the column for product name
                    if (column == cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_NAME)) {
                        TextView tv = (TextView) view;
                        String name = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_NAME));
                        String size = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCTORDER_SIZE));
                        String color = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCTORDER_COLOR));

                        //Update the name to add the color and size of item
                        tv.setText(name + "\nColor: " + color + "\nSize: " + size);
                    }
                    //If the view id matches the old price id textview
                    else if (view.getId() == R.id.old_price_shopping_cart) {
                        TextView tv = (TextView) view;
                        double oldprice = cursor.getDouble(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_PRICE));
                        //Update the old price
                        tv.setText("$" + oldprice + "0");
                    }
                    //If the view id matches the new price id textview
                    else if (view.getId() == R.id.new_price_shopping_cart) {
                        TextView tv = (TextView) view;
                        double oldprice = cursor.getDouble(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_PRICE));
                        double newprice = cursor.getDouble(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_DISCOUNT));

                        //Calculate the price with the discount
                        newprice = oldprice - (oldprice * newprice);
                        //Increment the total price of the order
                        //totalPrice += newprice;
                        //Log.i("LacyDB","total = "+ totalPrice);
                        tv.setText("$" + newprice + "0");
                    }
                    //If the view id matches the shopping cat item image id
                    else if (view.getId() == R.id.shopping_cart_item) {
                        ImageView imv = (ImageView) view;

                        int productImgIndex = cursor.getInt(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_IMGINDEX));
                        //Update to show the correct image
                        if (productImgIndex < ImageAdapter.images.length)
                            imv.setImageResource(ImageAdapter.images[productImgIndex]);
                        else
                            imv.setImageResource(ImageAdapter.images[0]);
                    }
                    return true;
                }
            });


            // Get the ListView so we can work with it
            ListView shoppingCartItems = (ListView) findViewById(R.id.shopping_cart_item_list);

            // Connect the ListView with the Adapter that acts as a bridge between it and the array
            shoppingCartItems.setAdapter(shoppingCartAdapter);


            String[] shippingSpeeds = {"7-9 business days- FREE for orders over $50",
                    "3-5 business days- $7.00", "2 business days- $16.00"};

            ListAdapter shippingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shippingSpeeds);

            ListView shippingListView = (ListView) findViewById(R.id.shipping_speed_list);

            shippingListView.setAdapter(shippingAdapter);


            //TextView total = (TextView) findViewById(R.id.shopping_cart_total_price);
            //total.setText("$" + totalPrice + "0");
        }
    }

    public void onRemoveCartButtonClick(View view) {
        int userID = system.getUserID();
        if (userID != 0) {

            //db.removeShoppingCartItem(userID,1);

            //Simple Cursor Adapter to fill up the listview
            //shoppingCartAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Item removed from your shopping cart", Toast.LENGTH_SHORT).show();

        }
    }


    //making shipping object in ShoppingCartScreen rather than ShippingAddressScreen because
    //it is here that the user selects the shipping speed.
    public void onCheckOutClick(View view) {

        //check to make sure a shipping speed is selected

        if (ShoppingCartScreen.estArrival != null) {
            //make shipping object
            Shipping shipping = new Shipping(estArrival, shippingCost);

            //set shipping object in Singleton System
            System.getInstance().setShippingForNewOrder(shipping);

            //go to shipping screen
            startActivity(new Intent(this, ShippingAddressScreen.class));
        } else {
            Toast.makeText(getApplicationContext(), "Please select a shipping speed.", Toast.LENGTH_SHORT).show();
        }
    }

    private static void displayEstArrivalInToast(Calendar calendar) {
        //months in Calendar start at zero
        int month = calendar.get(Calendar.MONTH) + 1;
        String formattedDate = month + "-" +
                calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.YEAR);

        Toast.makeText(ShoppingCartScreen.cartContext, "Estimated arrival: " + formattedDate, Toast.LENGTH_SHORT).show();
    }

    public static void changeShippingSpeed(int position) {
        int daysToArrive = 0;

        ShoppingCartScreen.estArrival = Calendar.getInstance();

        if (position == 0) {
            daysToArrive = 8;
            ShoppingCartScreen.shippingCost = 0.0;
            ShoppingCartScreen.estArrival.add(Calendar.DAY_OF_MONTH, daysToArrive);
            ShoppingCartScreen.displayEstArrivalInToast(estArrival);
        } else if (position == 1) {
            daysToArrive = 4;
            ShoppingCartScreen.shippingCost = 7.0;
            ShoppingCartScreen.estArrival.add(Calendar.DAY_OF_MONTH, daysToArrive);
            ShoppingCartScreen.displayEstArrivalInToast(estArrival);
        } else if (position == 2) {
            daysToArrive = 2;
            ShoppingCartScreen.shippingCost = 16.0;
            ShoppingCartScreen.estArrival.add(Calendar.DAY_OF_MONTH, daysToArrive);
            ShoppingCartScreen.displayEstArrivalInToast(estArrival);
        }

    }
}

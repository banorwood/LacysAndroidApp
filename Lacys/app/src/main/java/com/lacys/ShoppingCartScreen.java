package com.lacys;

import android.content.Intent;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christina on 3/9/2015.
 */
public class ShoppingCartScreen extends ActionBarActivity {
    private DBAdapter db;
    private System system;
    private double totalPrice = 0;

    SimpleCursorAdapter shoppingCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shopping_cart_screen);

        db = new DBAdapter(this);
        db.init();

        system = System.getInstance();

        int userID = system.getUserID();
        if(userID != 0) {
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
        if(userID != 0) {

            //db.removeShoppingCartItem(userID,1);

            //Simple Cursor Adapter to fill up the listview
            //shoppingCartAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Item removed from your shopping cart", Toast.LENGTH_SHORT).show();

        }
    }

    public void goToShipping(View view) {
        startActivity(new Intent(this, ShippingAddressScreen.class));
    }
}

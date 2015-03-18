package com.lacys;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Christina on 3/9/2015.
 */
public class ShoppingCartScreen extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shopping_cart_screen);

        //temporary product names before they are pulled from database
        String[] productDescriptions = {"Temp product name 1\nColor: Blue\nSize: Small\n",
                "Temp product name 2 Color: Red\n" + "Size: Small\n",
                "Temp product name 3 Color: Orange\n" + "Size: Large\n"};


        //this was throwing a null pointer exception for some reason so that's why I used the strikethrough
        //image which looks kind of weird
        //TextView oldPrice = (TextView) findViewById(R.id.old_price_shopping_cart);
        //oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); //strike through the old price


        //Point the ListAdapter to the custom adapter
        ListAdapter shoppingCartAdapter = new ArrayAdapter<String>(this, R.layout.shopping_cart_adapter,
                R.id.shopping_cart_item_info, productDescriptions);


        // ListAdapter theAdapter = new MyAdapter(this, favoriteTVShows);

        // Get the ListView so we can work with it
        ListView shoppingCartItems = (ListView) findViewById(R.id.shopping_cart_item_list);

        // Connect the ListView with the Adapter that acts as a bridge between it and the array
        shoppingCartItems.setAdapter(shoppingCartAdapter);


        String [] shippingSpeeds = {"7-9 business days- FREE for orders over $50",
                "3-5 business days- $7.00", "2 business days- $16.00" };

        ListAdapter shippingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shippingSpeeds);

        ListView shippingListView = (ListView) findViewById(R.id.shipping_speed_list);

        shippingListView.setAdapter(shippingAdapter);





        // A View is the generic term and class for every widget you put on your screen.
        // Views occupy a rectangular area and are responsible for handling events
        // and drawing the widget.

        // The ListAdapter acts as a bridge between the data and each ListItem
        // You fill the ListView with a ListAdapter. You pass it a context represented by
        // this.

        // A Context provides access to resources you need. It provides the current Context, or
        // facts about the app and the events that have occurred with in it.
        // android.R.layout.simple_list_item_1 is one of the resources needed.
        // It is a predefined layout provided by Android that stands in as a default



    }
}

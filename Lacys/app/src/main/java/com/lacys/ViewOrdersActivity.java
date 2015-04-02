package com.lacys;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Christina on 3/24/2015.
 */

//Temporarily be using this class as the order display
// until I get ViewOrdersFragment working
public class ViewOrdersActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_orders_fragment);

        String[] shipToArray = {"Alice Manning", "Alice Manning", "Lisa Roberts"};


        ListAdapter ordersAdapter = new ArrayAdapter<String>(this, R.layout.view_orders_adapter,
                R.id.ship_to_name, shipToArray);

        ListView ordersListView = (ListView) findViewById(R.id.orders_list_view);

        ordersListView.setAdapter(ordersAdapter);

    }


}

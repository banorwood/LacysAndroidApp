package com.lacys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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
}

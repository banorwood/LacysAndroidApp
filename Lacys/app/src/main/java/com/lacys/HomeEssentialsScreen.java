package com.lacys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Christina on 1/29/2015.
 */
public class HomeEssentialsScreen extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //At first I made different subcategories for Home Essentials but I think that will lead
        //to a lot of extra work. We can uncomment this if we decide to do it anyways.
        /*
        setContentView(R.layout.subcategory_layout);

        String[] homeEssentialsCategories = {"Furniture", "Kitchen", "Bed & Bath"};

        ListAdapter theAdapter;
        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, homeEssentialsCategories);

        ListView theListView = (ListView) findViewById(R.id.subcategory_list_view);

        theListView.setAdapter(theAdapter);
        */







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
                startActivity(new Intent(HomeEssentialsScreen.this, ContactUs.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.home:
                startActivity(new Intent(HomeEssentialsScreen.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}



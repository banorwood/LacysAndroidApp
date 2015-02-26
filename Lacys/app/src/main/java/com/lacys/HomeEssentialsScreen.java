package com.lacys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Christina on 1/29/2015.
 */
public class HomeEssentialsScreen extends Activity {


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



}



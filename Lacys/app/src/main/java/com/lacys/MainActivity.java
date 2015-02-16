package com.lacys;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] mainProductCategories = {getString(R.string.home_essentials_category),
                getString(R.string.women_category), getString(R.string.men_category)};

        //String[] homeEssentialsCategories = {"Furniture", "Kitchen", "Bed & Bath"};

        ListAdapter theAdapter;
        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mainProductCategories);

        ListView theListView = (ListView) findViewById(R.id.main_categories_list_view );

        theListView.setAdapter(theAdapter);


        //click on different product categories to launch different screens
        theListView.setOnItemClickListener(new
           AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                   String mainCategoryPicked = String.valueOf(adapterView.getItemAtPosition(position) );

                   if (mainCategoryPicked.equals(getString(R.string.home_essentials_category)))
                   {
                       startActivity(new Intent(MainActivity.this , HomeEssentialsScreen.class));
                   }
                   else if (mainCategoryPicked.equals(getString(R.string.women_category)))
                   {
                       startActivity(new Intent(MainActivity.this , WomenScreen.class));
                   }
                   else if (mainCategoryPicked.equals(getString(R.string.men_category)))
                   {
                       startActivity(new Intent(MainActivity.this , MenScreen.class));
                   }

                   //Toast.makeText(MainActivity.this, mainCategoryPicked, Toast.LENGTH_SHORT).show();

               }
           });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //for now only starts sign in activity. later on may also make user sign out.
    public void onSignInOutButtonClick(View view) {
        startActivity(new Intent(this, SignInScreen.class));
    }
}

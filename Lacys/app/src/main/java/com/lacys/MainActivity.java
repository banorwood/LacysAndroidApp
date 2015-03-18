package com.lacys;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends ActionBarActivity {


    private HorizontalScrollView adDisplay;

    //animation is not working yet
    //private static boolean scrollRight = true; //if this is false, HorizontalScrollView will scroll left
    //private static final long REFRESH_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] mainProductCategories = {getString(R.string.home_essentials_category),
                getString(R.string.women_category), getString(R.string.men_category)};

        //set images for the ads on the home screen
        ImageView mainAd1 = (ImageView) findViewById(R.id.main_ad_1);
        ImageView mainAd2 = (ImageView) findViewById(R.id.main_ad_2);
        ImageView mainAd3 = (ImageView) findViewById(R.id.main_ad_3);

        mainAd1.setImageResource(R.drawable.mens_clothing_ad);
        mainAd2.setImageResource(R.drawable.modern_couch_ad);
        mainAd3.setImageResource(R.drawable.womens_clothing_ad);

        adDisplay = (HorizontalScrollView) findViewById(R.id.ad_display);


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
                       //bug fix. Moved this code from HomeEssentialsScreen to here so 2 HomeEssentialScreen activities
                       //are not started and user doesn't have to click back button twice
                       Intent sendCategoryResource = new Intent(MainActivity.this, MultipleProductDisplayScreen.class);
                       sendCategoryResource.putExtra("categoryClicked", getString(R.string.home_essentials_category));
                       startActivity(sendCategoryResource);

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

        /* Animation of HorizontalScrollView is not working yet

        Timer animationTimer = new Timer();
        //MainActivity.scrollRight = true;

        animationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // What you want to do goes here

                if (MainActivity.scrollRight == true)
                {
                    scrollTo(1000);
                    scrollRight = false;
                }
                else
                {
                    scrollTo(0);
                    scrollRight = true;
                }
            }
        }, 0, REFRESH_TIME);

        */

    }

    /*
    private void scrollTo(int x) {
        ObjectAnimator animator = ObjectAnimator.ofInt(adDisplay, "scrollX", x);
        animator.setDuration(800);
        animator.start();
    }
    */


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

    public void onWomensClothingAdClick(View view) {

        startActivity(new Intent(MainActivity.this , WomenScreen.class));
    }


    public void onHomeEssentialsAdClick(View view) {
        Intent sendCategoryResource = new Intent(MainActivity.this, MultipleProductDisplayScreen.class);
        sendCategoryResource.putExtra("categoryClicked", getString(R.string.home_essentials_category));
        startActivity(sendCategoryResource);
    }


    public void onMensClothingAdClick(View view) {
        startActivity(new Intent(MainActivity.this , MenScreen.class));
    }
}

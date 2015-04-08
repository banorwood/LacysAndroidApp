package com.lacys;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks  {


    private DBAdapter db;
    private System system;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * U9sed to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    private HorizontalScrollView adDisplay;

    //animation is not working yet
    //private static boolean scrollRight = true; //if this is false, HorizontalScrollView will scroll left
    //private static final long REFRESH_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBAdapter(this);
        db.init();

        //Get the current system instance
        system = System.getInstance();
        //system.setUserID(1);
        //Get the user id
        int userID = system.getUserID();
        TextView signInButton = (TextView)findViewById(R.id.sign_in_out_button);
        //Determine if the user is logged in or not.
        if(userID == 0)
            signInButton.setText("Sign In");
        else
            signInButton.setText("Sign Out");

        this.setUpNavDrawer();

        this.setUpAdDisplay();

        this.setUpCategories();

    }

    /*
    private void scrollTo(int x) {
        ObjectAnimator animator = ObjectAnimator.ofInt(adDisplay, "scrollX", x);
        animator.setDuration(800);
        animator.start();
    }
    */


    private void setUpAdDisplay()
    {
        //set images for the ads on the home screen
        ImageView mainAd1 = (ImageView) findViewById(R.id.main_ad_1);
        ImageView mainAd2 = (ImageView) findViewById(R.id.main_ad_2);
        ImageView mainAd3 = (ImageView) findViewById(R.id.main_ad_3);

        mainAd1.setImageResource(R.drawable.mens_clothing_ad);
        mainAd2.setImageResource(R.drawable.modern_couch_ad);
        mainAd3.setImageResource(R.drawable.womens_clothing_ad);

        adDisplay = (HorizontalScrollView) findViewById(R.id.ad_display);

        //this.animateAdDisplay()
    }




    /* Animation of HorizontalScrollView is not working yet
    private void animateAdDisplay()
    {

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

    }
    */

    private void setUpNavDrawer()
    {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close database
        db.close();
    }

    /*animation not working yet
    private void scrollTo(int x) {
        ObjectAnimator animator = ObjectAnimator.ofInt(adDisplay, "scrollX", x);
        animator.setDuration(800);
        animator.start();
    }*/

    private void setUpCategories()
    {

        String[] mainProductCategories = {getString(R.string.home_essentials_category),
                getString(R.string.women_category), getString(R.string.men_category)};

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

       }
   });
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (position == 1)
        {
            //will uncomment this when I make the home page a fragment within MainActivity
            // so that I can switch out fragments
            /*
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ViewOrdersFragment.newInstance())
                    .commit();

            */

            startActivity(new Intent(MainActivity.this , ViewOrdersActivity.class));

        }
        if (position == 2)
        {
            int userID = system.getUserID();
            if(userID != 0)
                startActivity(new Intent(MainActivity.this , ShoppingCartScreen.class));
            else {
                Intent i = new Intent(getApplicationContext(), SignInScreen.class);
                i.putExtra("return", "close");
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Please login in order to view cart.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }



    //for now only starts sign in activity. later on may also make user sign out.
    public void onSignInOutButtonClick(View view) {
        if (system.getUserID() == 0)
            startActivity(new Intent(this, SignInScreen.class));
        else {
            system.setUserID(0);
            Toast.makeText(this, "Successfully logged out.", Toast.LENGTH_SHORT).show();
            TextView signInButton = (TextView)findViewById(R.id.sign_in_out_button);
            signInButton.setText("Sign In");
        }
    }

    public void onWomensClothingAdClick(View view) {
        Intent sendCategoryResource = new Intent(MainActivity.this, WomenScreen.class);
        sendCategoryResource.putExtra("categoryClicked", getString(R.string.women_category));
        startActivity(sendCategoryResource);
    }


    public void onHomeEssentialsAdClick(View view) {
        Intent sendCategoryResource = new Intent(MainActivity.this, MultipleProductDisplayScreen.class);
        sendCategoryResource.putExtra("categoryClicked", getString(R.string.home_essentials_category));
        startActivity(sendCategoryResource);
    }


    public void onMensClothingAdClick(View view) {
        Intent sendCategoryResource = new Intent(MainActivity.this, MenScreen.class);
        sendCategoryResource.putExtra("categoryClicked", getString(R.string.men_category));
        startActivity(sendCategoryResource);
    }



}

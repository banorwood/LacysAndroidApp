package com.lacys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by Christina on 2/15/2015.
 */
public class MultipleProductDisplayScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_product_display);

        GridView gv = (GridView) findViewById(R.id.grid_view);


        gv.setAdapter(new ImageAdapter(getApplicationContext()));

        getApplicationContext();


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View iv, int position, long id) {
                //Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), SingleProductViewScreen.class);
                i.putExtra("id", position);
                startActivity(i);


            }
        });




        Intent activityThatCalled = getIntent();

        String categoryPicked = activityThatCalled.getExtras().getString("categoryClicked");

        //mens shirts and womens shirts have the same name in the listview (Shirts) so that is why
        //calling activity needs to be checked. This if/else structure will later be used to determine
        //which data to show from the database
        if (categoryPicked.equals(getString(R.string.womens_shirts_category))
                && getCallingActivity().getClassName().equals("com.lacys.WomenScreen"))
        {
            Toast.makeText(this, "Show women's shirts from database", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, getCallingActivity().getClassName(), Toast.LENGTH_SHORT).show();
        }
        else if (categoryPicked.equals(getString(R.string.womens_pants_category))
                && getCallingActivity().getClassName().equals("com.lacys.WomenScreen"))
        {
            Toast.makeText(this, "Show women's pants from database", Toast.LENGTH_SHORT).show();
        }
        //checking calling activity here because lacys may later add things like girls skirts and juniors skirts
        else if (categoryPicked.equals(getString(R.string.womens_skirts_category)) && getCallingActivity().getClassName().equals("com.lacys.WomenScreen"))
        {
            Toast.makeText(this, "Show women's skirts from database", Toast.LENGTH_SHORT).show();
        }

        //Mens
        else if (categoryPicked.equals(getString(R.string.mens_shirts_category))
                && getCallingActivity().getClassName().equals("com.lacys.MenScreen"))
        {
            Toast.makeText(this, "Show men's shirts from database", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, getCallingActivity().getClassName(), Toast.LENGTH_SHORT).show();
        }
        else if (categoryPicked.equals(getString(R.string.womens_pants_category))
                && getCallingActivity().getClassName().equals("com.lacys.MenScreen"))
        {
            Toast.makeText(this, "Show men's pants from database", Toast.LENGTH_SHORT).show();
        }

        //Home Essentials
        else if (categoryPicked.equals(getString(R.string.home_essentials_category)))
        {
            Toast.makeText(this, "Show home essentials from database", Toast.LENGTH_SHORT).show();
        }




    }
}

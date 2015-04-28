package com.lacys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by Christina on 2/15/2015.
 */
public class MultipleProductDisplayScreen extends Activity {

    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_product_display);


        db = new DBAdapter(this);
        db.init();

        GridView gv = (GridView) findViewById(R.id.grid_view);
        ImageAdapter ia = new ImageAdapter(getApplicationContext(), this, db);
        gv.setAdapter(ia);

        getApplicationContext();


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View iv, int position, long id) {
                //Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), SingleProductViewScreen.class);
                i.putExtra("id", position);
                i.putExtra("category", getCategory());
                startActivity(i);

            }
        });


        Intent activityThatCalled = getIntent();

        String categoryPicked = activityThatCalled.getExtras().getString("categoryClicked");

        //mens shirts and womens shirts have the same name in the listview (Shirts) so that is why
        //calling activity needs to be checked. This if/else structure will later be used to determine
        //which data to show from the database
        if (categoryPicked.equals(getString(R.string.womens_shirts_category))
                && getCallingActivity().getClassName().equals("com.lacys.WomenScreen")) {
            Toast.makeText(this, "Loading products from women's shirts category", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, getCallingActivity().getClassName(), Toast.LENGTH_SHORT).show();
        } else if (categoryPicked.equals(getString(R.string.womens_pants_category))
                && getCallingActivity().getClassName().equals("com.lacys.WomenScreen")) {
            Toast.makeText(this, "Loading products from women's pants category", Toast.LENGTH_SHORT).show();
        }
        //checking calling activity here because lacys may later add things like girls skirts and juniors skirts
        else if (categoryPicked.equals(getString(R.string.womens_skirts_category)) && getCallingActivity().getClassName().equals("com.lacys.WomenScreen")) {
            Toast.makeText(this, "Loading products from women's skirts category", Toast.LENGTH_SHORT).show();
        }

        //Mens
        else if (categoryPicked.equals(getString(R.string.mens_shirts_category))
                && getCallingActivity().getClassName().equals("com.lacys.MenScreen")) {
            Toast.makeText(this, "Loading products from men's shirts category", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, getCallingActivity().getClassName(), Toast.LENGTH_SHORT).show();
        } else if (categoryPicked.equals(getString(R.string.mens_pants_category))
                && getCallingActivity().getClassName().equals("com.lacys.MenScreen")) {
            Toast.makeText(this, "Loading products from men's pants category", Toast.LENGTH_SHORT).show();
        }

        //Home Essentials
        else if (categoryPicked.equals(getString(R.string.home_essentials_category))) {
            Toast.makeText(this, "Loading products from home essentials category", Toast.LENGTH_SHORT).show();
        }

    }

    public String[] getCategory() {
        Intent activityThatCalled = getIntent();
        String categoryPicked[] = {"", ""};
        try {
            categoryPicked[0] = activityThatCalled.getExtras().getString("categoryClicked");
            categoryPicked[1] = getCallingActivity().getClassName();
        } catch (Exception e) {
            categoryPicked[0] = "";
            categoryPicked[1] = "";
        }

        return categoryPicked;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close database
        db.close();
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
                startActivity(new Intent(MultipleProductDisplayScreen.this, ContactUs.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.home:
                startActivity(new Intent(MultipleProductDisplayScreen.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

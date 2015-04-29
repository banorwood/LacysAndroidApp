package com.lacys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

/**
 * Created by  2015/4/4.
 */
public class ProductReview extends ActionBarActivity {
    private static DBAdapter db;
    private static System system;
    private ListView lv;
    private int productID;
    private static SimpleCursorAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_reviews);

        db = new DBAdapter(this);
        db.init();

        system = System.getInstance();
        productID = (Integer) getIntent().getExtras().get("id");

        //Obtains the reviews based on product id
        Cursor cs = db.getReviewData(productID);
        //Simple Cursor Adapter to fill up the listview
        reviewAdapter = new SimpleCursorAdapter(this,
                R.layout.review_adapter,
                cs,
                new String[]{LacyConstants.TABLE_REVIEW_NAME,
                        LacyConstants.TABLE_REVIEW_RATING,
                        LacyConstants.TABLE_REVIEW_MESSAGE,
                },
                new int[]{R.id.review_name,
                        R.id.review_rating,
                        R.id.review_msg,
                });
        reviewAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int column) {
                    //View id matches rating bar
                    if (view.getId() == R.id.review_name) {
                        TextView rb = (TextView) view;
                        String name = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_REVIEW_NAME));
                        //Set the rating bar
                        rb.setText(name);
                    }
                    //View id matches rating bar
                    else if (view.getId() == R.id.review_rating) {
                        RatingBar rb = (RatingBar) view;
                        double rating = cursor.getDouble(cursor.getColumnIndex(LacyConstants.TABLE_REVIEW_RATING));
                        //Set the rating bar
                        rb.setRating((float)rating);
                    //View id matches the review msg
                    } else if (view.getId() == R.id.review_msg) {
                        TextView rb = (TextView) view;
                        String ratingMsg = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_REVIEW_MESSAGE));
                        //Set the text
                        rb.setText(ratingMsg);
                    }
                    return true;
                }
            }
        );

        //Update the product table to get the new average rating
        db.updateReview(productID);

        lv = (ListView)findViewById(R.id.review_list);
        lv.setAdapter(reviewAdapter);
    }


    public void LaunchWriteProductReview(View view)
    {
        int userID = system.getUserID();
        if(userID != 0) {
            Intent i = new Intent(this, WriteProductReview.class);
            i.putExtra("id", productID);
            startActivity(i);
            finish();
        }
        else
        {
            Intent i = new Intent(getApplicationContext(), SignInScreen.class);
            i.putExtra("return", "close");
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Please login in order to write a review.", Toast.LENGTH_SHORT).show();
        }
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
                startActivity(new Intent(ProductReview.this, ContactUs.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.home:
                startActivity(new Intent(ProductReview.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // back to single product view page
    public void BackToSingle(View view)
    {
        this.finish();
    }
}

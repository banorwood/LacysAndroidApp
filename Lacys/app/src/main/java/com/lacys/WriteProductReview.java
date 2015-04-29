package com.lacys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class WriteProductReview extends ActionBarActivity {

    private static DBAdapter db;
    private static System system;
    private static int productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_product_review);

        db = new DBAdapter(this);
        db.init();

        system = System.getInstance();
        productID = (Integer) getIntent().getExtras().get("id");
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
                startActivity(new Intent(WriteProductReview.this, ContactUs.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.home:
                startActivity(new Intent(WriteProductReview.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void PostReview(View view)
    {
        boolean valid = true;
        // check review text. if it's empty, show an alert dialog and display toast message
        TextView tv = (TextView)findViewById(R.id.write_product_review);
        String reviewMsg = tv.getText().toString();
        if(reviewMsg.length() == 0) {
            tv.setError("A review is required!");
            valid = false;
        }

        // check rating bar is selected or not
        RatingBar rb = (RatingBar)findViewById(R.id.ratingBar2);
        double rating = rb.getRating();
        if(rating < 0.0000001) // avoid float equal, use less than instead
        {
            Toast.makeText(getApplicationContext(), "You did not select a rating.", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        // back to single product review page
        if(valid) {

            int userID = system.getUserID();
            //Getting the account details from the user id logged in
            Cursor c = db.getAccDetails(userID);
            //The email address on the account
            String name = c.getString(c.getColumnIndex(LacyConstants.TABLE_ACCOUNT_FIRSTNAME));
            //Checks if we can insert the info in the database
            if (db.addReviewData(name,productID,rating,reviewMsg) != -1) {
                Toast.makeText(getApplicationContext(), "Thanks! Your review of our product is appreciated!", Toast.LENGTH_SHORT).show();
                Cancel(view);
            }else
                Toast.makeText(getApplicationContext(), "An error has occurred adding your review.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close database
        db.close();
    }

    public void Cancel(View view)
    {
        Intent i = new Intent(this, ProductReview.class);
        i.putExtra("id", productID);
        startActivity(i);
        finish();
    }
}

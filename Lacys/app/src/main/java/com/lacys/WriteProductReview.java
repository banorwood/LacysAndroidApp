package com.lacys;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


public class WriteProductReview extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_product_review);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_write_product_review, menu);-->
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

    private void Notify(CharSequence astr, CharSequence tstr)
    {
        // alert dialog
        new AlertDialog.Builder(this).setTitle("Alert").setMessage(astr).
                setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setIcon(R.drawable.ic_launcher).show();

        // toast message
        Toast.makeText(getApplicationContext(), tstr, Toast.LENGTH_SHORT).show();
    }

    public void PostReview(View view)
    {
        boolean valid = true;
        // check review text. if it's empty, show an alert dialog and display toast message
        EditText tv = (EditText)findViewById(R.id.textView7);
        if(tv.getText().length() == 0) {
            Notify("Please write a review", "Product review is empty");
            valid = false;
        }

        // check rating bar is selected or not
        RatingBar rb = (RatingBar)findViewById(R.id.ratingBar2);
        if(rb.getRating() < 0.0000001) // avoid float equal, use less than instead
        {
            Notify("Please select a star rating", "Star rating is not selected");
            valid = false;
        }

        // back to single product review page
        if(valid)
            this.finish();
    }

    public void Cancel(View view)
    {
        this.finish();
    }
}

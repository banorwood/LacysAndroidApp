package com.lacys;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by cbredbe3177 on 2/19/2015.
 */
public class SingleProductViewScreen extends ActionBarActivity
{
    private int position;
    private Spinner colorSpinner;
    private Spinner sizeSpinner;
    private int productID;
    private DBAdapter db;
    private System system;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product_view);
		
		//estArrival is checked before going to checkout screen to make sure
        //user has selected a shipping speed. estArrival is static so this is
        //to make sure the user doesn't hit the back button three times, select another product
        //and then estArrival already has a value even though the user didn't select
        //a shipping speed for that product
        ShoppingCartScreen.estArrival = null;

        db = new DBAdapter(this);
        db.init();
        system = System.getInstance();
        position = (Integer) getIntent().getExtras().get("id");
        String categories[] = (String[])getIntent().getExtras().get("category");
        String category = categories[0];
        String calledClass = categories[1];
        String fullCategory;
        if (category.equals("Shirts") && calledClass.equals("com.lacys.WomenScreen"))
            fullCategory = "Women " + category;
        else if (category.equals("Pants") && calledClass.equals("com.lacys.WomenScreen"))
            fullCategory = "Women " + category;
        else if (category.equals("Skirts") && calledClass.equals("com.lacys.WomenScreen"))
            fullCategory = "Women " + category;
        else if (category.equals("Shirts") && calledClass.equals("com.lacys.MenScreen"))
            fullCategory = "Men " + category;
        else if (category.equals("Pants") && calledClass.equals("com.lacys.MenScreen"))
            fullCategory = "Men " + category;
        else
            fullCategory = "Home Essentials";

        ArrayList<String[]> products = db.getProducts(fullCategory);
        if (position < products.size()) {
            String[] results = products.get(position);
            if ((results != null)) {
                String productName = results[0];
                int productImgIndex = Integer.parseInt(results[1]);
                double productPrice = Double.parseDouble(results[2]);
                double productDiscount = Double.parseDouble(results[3]);
                String productDescription = results[4];
                double productRating = Double.parseDouble(results[5]);
                productID = Integer.parseInt(results[6]);

                //Add The Text!!!
                TextView name = (TextView) findViewById(R.id.product_name);
                //Calulate the price with the discount added.
                productPrice = productPrice - (productPrice * productDiscount);

                String formatted = String.format("%.2f", productPrice);

                name.setText(productName + "\n$" + formatted);

                TextView details = (TextView) findViewById(R.id.product_details);
                details.setText(productDescription);

                RatingBar rating = (RatingBar) findViewById(R.id.ratingBar);
                rating.setRating((float)productRating);

                TextView ratingText = (TextView) findViewById(R.id.ratingBarText);
                ratingText.setText("" + productRating);

                // Add The Image!!!
                ImageView image = (ImageView)findViewById(R.id.image_view);
                if (productImgIndex < ImageAdapter.images.length)
                    image.setImageResource(ImageAdapter.images[productImgIndex]);
                else
                    image.setImageResource(R.drawable.error);

                Cursor cs = db.getReviewData(productID);
                //There are no reviews available
                if (cs == null) {
                    Button ratingButton = (Button) findViewById(R.id.rating_button);
                    TextView ratingTxt = (TextView) findViewById(R.id.avgRateText);
                    //Remove rating bar
                    rating.setVisibility(View.GONE);
                    //Remove rating number
                    ratingText.setVisibility(View.GONE);
                    ratingTxt.setText("No reviews available");
                    //Change button text
                    ratingButton.setText("Write a review!");
                    //Add listener
                    ratingButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            int userID = system.getUserID();
                            if(userID != 0) {
                                Intent i = new Intent(getApplicationContext(), WriteProductReview.class);
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
                    });
                }
            }
        }



        addItemsToColorSpinner();
        addItemsToSizeSpinner();
    }

    public void onAddToCartButtonClick(View view) {
        int userID = system.getUserID();
        if(userID != 0) {
            Intent i = new Intent(getApplicationContext(), ShoppingCartScreen.class);
            //i.putExtra("id", position);
            //i.putExtra("category", (String[])getIntent().getExtras().get("category"));

            Spinner spinnerC = (Spinner) findViewById(R.id.color_spinner);
            String color = spinnerC.getSelectedItem().toString();
            Spinner spinnerS = (Spinner) findViewById(R.id.size_spinner);
            String size = spinnerS.getSelectedItem().toString();

            int orderID = system.getOrderID();

            long po_id = db.addShoppingCartData(orderID, productID, color, size);
            if (po_id != -1) {
                startActivity(i);
                finish();
            }
            else
                Toast.makeText(getApplicationContext(), "Could not add product to cart.", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent(getApplicationContext(), SignInScreen.class);
            i.putExtra("return", "close");
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Please login in order to add to cart.", Toast.LENGTH_SHORT).show();
        }
    }


    private void addItemsToColorSpinner(){

        // Get a reference to the spinner
        colorSpinner = (Spinner) findViewById(R.id.color_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> colorSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.clothing_color_choices, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        colorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        colorSpinner.setAdapter(colorSpinnerAdapter);

    }

    private void addItemsToSizeSpinner()
    {
        sizeSpinner = (Spinner) findViewById(R.id.size_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.clothing_size_choices, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Apply the adapter to the spinner
        sizeSpinner.setAdapter(sizeSpinnerAdapter);

    }

	public void LaunchProductReview(View view)
    {
        Intent i = new Intent(this, ProductReview.class);
        i.putExtra("id", productID);
        startActivity(i);
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
                startActivity(new Intent(SingleProductViewScreen.this, ContactUs.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.home:
                startActivity(new Intent(SingleProductViewScreen.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close database
        db.close();
    }
}

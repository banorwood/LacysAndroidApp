package com.lacys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cbredbe3177 on 2/19/2015.
 */
public class SingleProductViewScreen extends Activity
{
    private int position;
    private Spinner colorSpinner;
    private Spinner sizeSpinner;
	private DBAdapter db;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product_view);

        db = new DBAdapter(this);
        db.init();
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

                //Add The Text!!!
                TextView name = (TextView) findViewById(R.id.product_name);
                //Calulate the price with the discount added.
                productPrice = productPrice - (productPrice * productDiscount);
                name.setText(productName + "\n$" + productPrice + "0");

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
            }
        }

        addItemsToColorSpinner();
        addItemsToSizeSpinner();
    }

    public void onAddToCartButtonClick(View view) {

        Intent i = new Intent(getApplicationContext(), ShoppingCartScreen.class);
        startActivity(i);
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
    public void LaunchWriteProductReview(View view)
    {
        Intent intent = new Intent(this, WriteProductReview.class);
        startActivity(intent);
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        //Close database
        db.close();
    }
}

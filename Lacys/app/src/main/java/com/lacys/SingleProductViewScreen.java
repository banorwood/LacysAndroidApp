package com.lacys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by cbredbe3177 on 2/19/2015.
 */
public class SingleProductViewScreen extends Activity
{
    private int position;
    private Spinner colorSpinner;
    private Spinner sizeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product_view);

        Intent i = getIntent();
        position = (Integer) i.getExtras().get("id");

        ImageView iv = (ImageView) findViewById(R.id.image_view);
        iv.setImageResource(ImageAdapter.images[position]);

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
}

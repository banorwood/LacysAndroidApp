package com.lacys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by cbredbe3177 on 2/19/2015.
 */
public class SingleProductViewScreen extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product_view);

        Intent i = getIntent();
        int position = (Integer) i.getExtras().get("id");

        ImageView iv = (ImageView) findViewById(R.id.image_view);
        iv.setImageResource(ImageAdapter.images[position]);

    }
}

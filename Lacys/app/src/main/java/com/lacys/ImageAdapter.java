package com.lacys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cbredbe3177 on 2/19/2015.
 */
public class ImageAdapter extends BaseAdapter {

    private final Context context;
    private MultipleProductDisplayScreen mpds;
    private DBAdapter db;
    private int count = 1; //Amount of products in each category to display
    public static int[] images = {
            R.drawable.error,
            R.drawable.menshirt1,
            R.drawable.menshirt2,
            R.drawable.menshirt3,
            R.drawable.menshirt4,
            R.drawable.menshirt5,
            R.drawable.menshirt6,
            R.drawable.menpants1,
            R.drawable.menpants2,
            R.drawable.menpants3,
            R.drawable.menpants4,
            R.drawable.menpants5,
            R.drawable.menpants6,
            R.drawable.womenskirt1,
            R.drawable.womenskirt2,
            R.drawable.womenskirt3,
            R.drawable.womenskirt4,
            R.drawable.womenskirt5,
    };
    //public static String[] priceInfo = {"$28.35", "$55.43", "$32.45", "$89.34"};

    public ImageAdapter(Context applicationContext, MultipleProductDisplayScreen appMPDS, DBAdapter database)
    {
        context = applicationContext;
        mpds = appMPDS;
        db = database;
    }

    @Override
    public int getCount() {
        //number of dataelements to be displayed
        //return images.length;
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View MyView = convertView;
        if (MyView == null)
        {
            String categories[] = mpds.getCategory();
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
            count = products.size();    //Update the count of items to display
            if (position < products.size()) {
                String[] results = products.get(position);
                if ((results != null)) {
                    String productName = results[0];
                    int productImgIndex = Integer.parseInt(results[1]);
                    double productPrice = Double.parseDouble(results[2]);
                    double productDiscount = Double.parseDouble(results[3]);

                    //Inflate the layout
                    LayoutInflater li = LayoutInflater.from(context);
                    MyView = li.inflate(R.layout.grid_item, null);
                    // Add The Text!!!
                    TextView name = (TextView) MyView.findViewById(R.id.grid_item_text);
                    name.setText(productName);

                    //Display a discount price only if there is a discount
                    if (productDiscount > 0 ) {
                        TextView price = (TextView) MyView.findViewById(R.id.grid_item_text_2);
                        price.setText("Was: $" + productPrice + "0");

                        TextView priceText = (TextView) MyView.findViewById(R.id.grid_item_text_4);

                        String formatted = String.format("%.0f", (productDiscount * 100));
                        priceText.setText("You save " + formatted + "%!");

                        productDiscount = productPrice - (productPrice * productDiscount);
                        TextView discount = (TextView) MyView.findViewById(R.id.grid_item_text_3);
                        discount.setText("NOW: $" + productDiscount + "0");
                    }
                    else{
                        TextView price = (TextView) MyView.findViewById(R.id.grid_item_text_2);
                        price.setText("Price: $" + productPrice + "0");

                        TextView priceText = (TextView) MyView.findViewById(R.id.grid_item_text_4);
                        TextView discount = (TextView) MyView.findViewById(R.id.grid_item_text_3);
                        priceText.setVisibility(View.GONE);
                        discount.setVisibility(View.GONE);
                    }

                    // Add The Image!!!
                    ImageView image = (ImageView) MyView.findViewById(R.id.grid_item_image);
                    if (productImgIndex < images.length)
                        image.setImageResource(images[productImgIndex]);
                    else
                        image.setImageResource(images[0]);
                }
            }
        }
        return MyView;

    }
}

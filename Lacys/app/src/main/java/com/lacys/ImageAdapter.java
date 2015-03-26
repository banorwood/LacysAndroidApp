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
    private int count = 40;
    public static int[] images = {R.drawable.test_image_1,R.drawable.menshirt1,R.drawable.menshirt2, R.drawable.menshirt3};
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
        Log.i("LacyDB","getView");
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
            count = products.size();
            if (position < (products.size()+1)) {
                String[] results = products.get(position);
                if ((results != null)) {
                    String productName = results[0];
                    int productImgIndex = Integer.parseInt(results[1]);

                    if (db.getDEBUG()) {
                        Log.i(db.getLogTag(), "productName: " + productName);
                        Log.i(db.getLogTag(), "productImgIndex: " + productImgIndex);
                    }
                    //Inflate the layout
                    LayoutInflater li = LayoutInflater.from(context);
                    MyView = li.inflate(R.layout.grid_item, null);

                    // Add The Text!!!
                    TextView tv = (TextView) MyView.findViewById(R.id.grid_item_text);
                    tv.setText(productName);

                    // Add The Image!!!
                    ImageView iv = (ImageView) MyView.findViewById(R.id.grid_item_image);
                    iv.setImageResource(images[productImgIndex]);
                /*
                GridView.LayoutParams theGridViewParams = new GridView.LayoutParams(160,160);
                iv.setLayoutParams(theGridViewParams);
                */
                }
                else
                    Log.i(db.getLogTag(), "Result Size: null");
            }
            else
                Log.i(db.getLogTag(), "Position: " + position + " Size: "+ products.size());
        }
        return MyView;

    }
}

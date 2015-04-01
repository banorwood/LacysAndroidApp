package com.lacys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cbredbe3177 on 2/19/2015.
 */
public class ImageAdapter extends BaseAdapter {

    private final Context context;
    public static int[] images = {R.drawable.test_image_1, R.drawable.test_image_2, R.drawable.test_image_3,
            R.drawable.test_image_4};
    public static String[] priceInfo = {"$28.35", "$55.43", "$32.45", "$89.34"};

    public ImageAdapter(Context applicationContext)
    {
        context = applicationContext;
    }

    @Override
    public int getCount() {
        //number of dataelements to be displayed
        return images.length;
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

        /*
        ImageView iv;
        TextView tv = new TextView(context);
        if (convertView != null)
        {
            iv = (ImageView) convertView;
        }
        else
        {
            iv = new ImageView(context);

            iv.setLayoutParams(theGridViewParams);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setPadding(4, 12, 4, 12);
        }
        iv.setImageResource(images[position]);

        return iv;

        */
        View MyView = convertView;

        if ( convertView == null )
        {
                                /*we define the view that will display on the grid*/


            //Inflate the layout
            LayoutInflater li = LayoutInflater.from(context);
            MyView = li.inflate(R.layout.grid_item, null);


            // Add The Text!!!
            TextView tv = (TextView)MyView.findViewById(R.id.grid_item_text);
            tv.setText("Item "+ position );

            // Add The Image!!!
            ImageView iv = (ImageView)MyView.findViewById(R.id.grid_item_image);
            iv.setImageResource(images[position]);
                /*
                GridView.LayoutParams theGridViewParams = new GridView.LayoutParams(160,160);
                iv.setLayoutParams(theGridViewParams);
                */
        }

        return MyView;

    }
}

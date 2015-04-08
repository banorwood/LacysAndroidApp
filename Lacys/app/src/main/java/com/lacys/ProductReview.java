package com.lacys;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

/**
 * Created by  2015/4/4.
 */
public class ProductReview extends ActionBarActivity {
    private ListView lv;
    private ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_reviews);

        lv = (ListView)findViewById(R.id.productview_list);

        //String[] reviews = {"review1", "review2",  "review3", "review4"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData());
        lv.setAdapter(adapter);
    }

    private ArrayList<String> getData()
    {
        list.add("Product review 1");
        list.add("Very good product");
        list.add("Nice, will buy again");
        list.add("This is a good app");
        return list;
    }

    // back to single product view page
    public void BackToSingle(View view)
    {
        this.finish();
    }
}

package com.lacys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Christina on 1/29/2015.
 */
public class MenScreen extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategory_layout);


        String[] menCategories = {getString(R.string.mens_shirts_category), getString(R.string.mens_pants_category)};

        ListAdapter theAdapter;
        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menCategories);

        ListView theListView = (ListView) findViewById(R.id.subcategory_list_view);

        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new
           AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                   String subCategoryPicked = String.valueOf(adapterView.getItemAtPosition(position));

                   //sending string resource of subcategory so that MultipleProductDisplayScreen knows
                   //which table to display products from
                   Intent sendCategoryResource = new Intent(MenScreen.this, MultipleProductDisplayScreen.class);

                   if (subCategoryPicked.equals(getString(R.string.mens_shirts_category)))
                       sendCategoryResource.putExtra("categoryClicked", getString(R.string.mens_shirts_category));
                   else if (subCategoryPicked.equals(getString((R.string.mens_pants_category))))
                       sendCategoryResource.putExtra("categoryClicked", getString(R.string.mens_pants_category));

                   //using startActivityForResult() rather than startActivity() so that
                   // getCallingActivity.getClassName() in called activity will work
                   //and program will not stop
                   final int result = 1;
                   startActivityForResult(sendCategoryResource, result);
               }

           });
    }
}

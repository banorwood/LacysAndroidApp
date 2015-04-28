package com.lacys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * Created by blakenorwood on 3/24/15.
 */
public class ShippingAddressScreen extends ActionBarActivity {

    EditText fNameEditText, lNameEditText, addressLine1EditText, addressLine2EditText, cityEditText,
            zipCodeEditText;
    private CheckBox checkBox;
    private Spinner stateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_address_screen);

        fNameEditText = (EditText) findViewById(R.id.fNameEditText);
        lNameEditText = (EditText) findViewById(R.id.lNameEditText);
        addressLine1EditText = (EditText) findViewById(R.id.addressLine1EditText);
        addressLine2EditText = (EditText) findViewById(R.id.addressLine2EditText);
        cityEditText = (EditText) findViewById(R.id.cityEditText);
        zipCodeEditText = (EditText) findViewById(R.id.zipCodeEditText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);

    }


    public void onContinueClick(View view) {
        //put values in text fields into shipping object
        String fName = fNameEditText.getText().toString();
        String lName = lNameEditText.getText().toString();
        String addressLine1 = addressLine1EditText.getText().toString();
        String addressLine2 = addressLine2EditText.getText().toString();
        String city = cityEditText.getText().toString();
        String zipCode = zipCodeEditText.getText().toString();
        String state = stateSpinner.getSelectedItem().toString();
        boolean fail = false;

        if (fName.length() == 0) {
            fNameEditText.setError("First name is required!");
            fail = true;
        }
        if (lName.length() == 0) {
            lNameEditText.setError("Last name is required!");
            fail = true;
        }
        if (addressLine1.length() == 0) {
            addressLine1EditText.setError("Address is required!");
            fail = true;
        }
        if (city.length() == 0) {
            cityEditText.setError("City is required!");
            fail = true;
        }
        if (zipCode.length() == 0) {
            zipCodeEditText.setError("Zip Code is required!");
            fail = true;
        }
        if (zipCode.length() > 5) {
            zipCodeEditText.setError("Zip Code is less than 5 #'s!");
            fail = true;
        }
        if (fail)
            return;

        int index = getIndex(stateSpinner, stateSpinner.getSelectedItem().toString());

        //if it didn't fail validation, put the values in the shipping object that
        //is in the System singleton and that was created by ShoppingCartScreen
        Shipping shipping = System.getInstance().getShippingForNewOrder();
        CheckOut addressForShipping = new CheckOut(fName, lName, addressLine1, addressLine2, city, Integer.parseInt(zipCode), state);
        shipping.setCheckOut(addressForShipping);
        //will write the shipping and billing objects that are in system to the database when
        //the user is completely done checking out.

        if (checkBox.isChecked()) {
            Intent intentBundle = new Intent(this, BillingAddressScreen.class);
            Bundle bundle = new Bundle();
            bundle.putString("fName", fName);
            bundle.putString("lName", lName);
            bundle.putString("addressLine1", addressLine1);
            bundle.putString("addressLine2", addressLine2);
            bundle.putString("city", city);
            bundle.putString("zip", zipCode);
            bundle.putInt("index", index);
            intentBundle.putExtras(bundle);
            startActivity(intentBundle);
        } else
            //launch screen for entering billing address
            startActivity(new Intent(this, BillingAddressScreen.class));


    }

    private int getIndex(Spinner spinner, String s) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(s)) {
                index = i;
                return index;
            }
        }
        return index;
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
                startActivity(new Intent(ShippingAddressScreen.this, ContactUs.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.home:
                startActivity(new Intent(ShippingAddressScreen.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

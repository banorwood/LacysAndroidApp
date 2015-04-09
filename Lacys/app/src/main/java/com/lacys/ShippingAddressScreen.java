package com.lacys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


/**
 * Created by blakenorwood on 3/24/15.
 */
public class ShippingAddressScreen extends ActionBarActivity {

    EditText fNameEditText, lNameEditText, addressLine1EditText, addressLine4EditText, cityEditText,
            zipCodeEditText;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_address_screen);

        fNameEditText = (EditText) findViewById(R.id.fNameEditText);
        lNameEditText = (EditText) findViewById(R.id.lNameEditText);
        addressLine1EditText = (EditText) findViewById(R.id.addressLine1EditText);
        addressLine4EditText = (EditText) findViewById(R.id.addressLine4EditText);
        cityEditText = (EditText) findViewById(R.id.cityEditText);
        zipCodeEditText = (EditText) findViewById(R.id.zipCodeEditText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

    }


    public void onContinueClick(View view) {
        //put values in text fields into shipping object
        String fName = fNameEditText.getText().toString();
        String lName = lNameEditText.getText().toString();
        String addressLine1 = addressLine1EditText.getText().toString();
        String addressLine4 = addressLine4EditText.getText().toString();
        String city = cityEditText.getText().toString();
        String zipCode = zipCodeEditText.getText().toString();
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
        if (fail)
            return;


        if (checkBox.isChecked()) {
            Intent intentBundle = new Intent(this, BillingAddressScreen.class);
            Bundle bundle = new Bundle();
            bundle.putString("fName", fName);
            bundle.putString("lName", lName);
            bundle.putString("addressLine1", addressLine1);
            bundle.putString("addressLine4", addressLine4);
            bundle.putString("city", city);
            bundle.putString("zip", zipCode);
            intentBundle.putExtras(bundle);
            startActivity(intentBundle);
        } else
            //launch screen for entering billing address
            startActivity(new Intent(this, BillingAddressScreen.class));


    }
}

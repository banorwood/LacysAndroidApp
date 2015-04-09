package com.lacys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by blakenorwood on 3/24/15.
 */
public class BillingAddressScreen extends ActionBarActivity {

    EditText fNameEditText, lNameEditText, addressLine1EditText, addressLine4EditText, cityEditText,
            zipCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.billing_address_screen);

        fNameEditText = (EditText) findViewById(R.id.fNameEditText);
        lNameEditText = (EditText) findViewById(R.id.lNameEditText);
        addressLine1EditText = (EditText) findViewById(R.id.addressLine1EditText);
        addressLine4EditText = (EditText) findViewById(R.id.addressLine4EditText);
        cityEditText = (EditText) findViewById(R.id.cityEditText);
        zipCodeEditText = (EditText) findViewById(R.id.zipCodeEditText);
    }

    public void goToPayment(View view) {
        startActivity(new Intent(this, PaymentScreen.class));
    }
}

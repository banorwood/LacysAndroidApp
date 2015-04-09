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

        Intent intentExtras = getIntent();
        Bundle bundle = intentExtras.getExtras();
        if (bundle != null) {
            fNameEditText.setText(bundle.getString("fName"));
            lNameEditText.setText(bundle.getString("lName"));
            addressLine1EditText.setText(bundle.getString("addressLine1"));
            addressLine4EditText.setText(bundle.getString("addressLine4"));
            cityEditText.setText(bundle.getString("city"));
            zipCodeEditText.setText(bundle.getString("zip"));
        }
    }

    public void goToPayment(View view) {
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

        startActivity(new Intent(this, PaymentScreen.class));
    }
}

package com.lacys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by blakenorwood on 3/24/15.
 */
public class BillingAddressScreen extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.billing_address_screen);
    }

    public void goToPayment(View view) {
        startActivity(new Intent(this, PaymentScreen.class));
    }
}

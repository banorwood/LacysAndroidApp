package com.lacys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by blakenorwood on 3/24/15.
 */
public class ShippingAddressScreen extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shipping_address_screen);
    }

    public void goToBilling(View view) {
        startActivity(new Intent(this, BillingAddressScreen.class));
    }
}

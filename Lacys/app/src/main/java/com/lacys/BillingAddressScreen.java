package com.lacys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by blakenorwood on 3/24/15.
 */
public class BillingAddressScreen extends ActionBarActivity {

    private EditText fNameEditText, lNameEditText, addressLine1EditText, addressLine2EditText, cityEditText,
            zipCodeEditText;

    private Spinner stateSpinner;

    //if address info was sent over from ShippingAddressScreen and the user makes no changes to
    //it, the same reference will be used for the checkouts in the shipping and billing objects.
    //There will be one row for this order in the Checkout table which will
    // also have references to both the shipping id and
    //billing id for this order.
    private TextWatcher textWatcher;

    private static boolean billingSameAsShipping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.billing_address_screen);

        fNameEditText = (EditText) findViewById(R.id.fNameEditText);
        lNameEditText = (EditText) findViewById(R.id.lNameEditText);
        addressLine1EditText = (EditText) findViewById(R.id.addressLine1EditText);
        addressLine2EditText = (EditText) findViewById(R.id.addressLine4EditText);
        cityEditText = (EditText) findViewById(R.id.cityEditText);
        zipCodeEditText = (EditText) findViewById(R.id.zipCodeEditText);
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner2);

        Intent intentExtras = getIntent();
        Bundle bundle = intentExtras.getExtras();

        //if checkbox "use this as my billing address" was selected on the previous screen
        if (bundle != null) {
            fNameEditText.setText(bundle.getString("fName"));
            lNameEditText.setText(bundle.getString("lName"));
            addressLine1EditText.setText(bundle.getString("addressLine1"));
            addressLine2EditText.setText(bundle.getString("addressLine2"));
            cityEditText.setText(bundle.getString("city"));
            zipCodeEditText.setText(bundle.getString("zip"));
            stateSpinner.setSelection(bundle.getInt("index"));

            billingSameAsShipping = true; //this status may later be changed by textWatcher
        } else {
            billingSameAsShipping = false;
        }


        //if user did select "make billing same as shipping", we must make sure we only use the
        //same address for shipping and billing if the user does not change anything on this page.
        //set a TextWatcher on all the textboxes and an OnItemSelectedListener on the state spinner.
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                BillingAddressScreen.billingSameAsShipping = false;
            }
        };

        //check to see if the user changes the state as well.
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                BillingAddressScreen.billingSameAsShipping = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        //attatch textWatcher to all edittexts on the screen
        this.fNameEditText.addTextChangedListener(textWatcher);
        this.lNameEditText.addTextChangedListener(textWatcher);
        this.addressLine1EditText.addTextChangedListener(textWatcher);
        this.addressLine2EditText.addTextChangedListener(textWatcher);
        this.cityEditText.addTextChangedListener(textWatcher);
        this.zipCodeEditText.addTextChangedListener(textWatcher);

    }

    public void goToPayment(View view) {
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


        //if the address didn't fail validation, create a Billing object to put in the
        // System singleton. Either get the CheckOut object from the shipping object
        //if billing is the same as shipping, or create a new CheckOut
        CheckOut addressForBilling;

        if (this.billingSameAsShipping == false) {
            addressForBilling = new CheckOut(fName, lName, addressLine1, addressLine2, city, Integer.parseInt(zipCode), state);
        } else {
            addressForBilling = System.getInstance().getShippingForNewOrder().getCheckOut();
        }

        Billing billing = new Billing(addressForBilling);

        System.getInstance().setBillingForNewOrder(billing);

        //will write the shipping and billing objects that are in system to the database when
        //the user is completely done checking out.

        startActivity(new Intent(this, PaymentScreen.class));
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
                startActivity(new Intent(BillingAddressScreen.this, ContactUs.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.home:
                startActivity(new Intent(BillingAddressScreen.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

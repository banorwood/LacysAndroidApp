package com.lacys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by blakenorwood on 4/1/15.
 */
public class PaymentScreen extends ActionBarActivity {
    private Spinner cardTypeSpinner;
    private EditText cardNumEditText;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private EditText secCodeEditText;
    private Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.payment_screen);
        cardTypeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        cardNumEditText = (EditText) findViewById(R.id.cardNumEditText);
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        secCodeEditText = (EditText) findViewById(R.id.secCodeEditText);
        finish = (Button) findViewById(R.id.finishBtn);
    }

    public void submitOrder(View view) {

        boolean fail = false;

        //get values from EditTexts and Spinners
        String cardType = this.cardTypeSpinner.getSelectedItem().toString();
        long cardNum = Long.parseLong(this.cardNumEditText.getText().toString());
        int month = (Integer) this.monthSpinner.getSelectedItemPosition();
        int year = Integer.parseInt(this.yearSpinner.getSelectedItem().toString());
        int secCode = Integer.parseInt(this.secCodeEditText.getText().toString());

        //get the billing object from system which was created by BillingAddressScreen
        Billing billing = System.getInstance().getBillingForNewOrder();

        //set values in the billing object.
        billing.setCardType(cardType);
        billing.setCardNum(cardNum);

        //for validating that credit card used is not expired
        //getInstance() returns calendar with current date.
        Calendar rightNow = Calendar.getInstance();
        billing.setPurchaseDate(rightNow);

        Calendar expCalendar = Calendar.getInstance();
        expCalendar.set(year, month, 1);

        if (rightNow.before(expCalendar)) {
            billing.setExpDate(expCalendar);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "That card is expired.", Toast.LENGTH_SHORT).show();
            fail = true;
        }

        billing.setSecurityCode(secCode);

        //need more validation

        if (!fail)
        {
            DBAdapter.writeAllCheckOutInfo();
            startActivity(new Intent(this, ViewOrdersActivity.class));
        }

    }


}

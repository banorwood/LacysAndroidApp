package com.lacys;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by blakenorwood on 4/1/15.
 */
public class PaymentScreen extends ActionBarActivity {
    private Spinner cardType;
    private EditText cardNum;
    private Spinner month;
    private Spinner year;
    private EditText secCode;
    private Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.payment_screen);
        cardType = (Spinner) findViewById(R.id.typeSpinner);
        cardNum = (EditText) findViewById(R.id.cardNumEditText);
        month = (Spinner) findViewById(R.id.monthSpinner);
        year = (Spinner) findViewById(R.id.yearSpinner);
        secCode = (EditText) findViewById(R.id.secCodeEditText);
        finish = (Button) findViewById(R.id.finishBtn);
    }

    public void submitOrder() {

    }


}

package com.lacys;

/**
 * Created by blakenorwood on 4/22/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ContactUs extends Activity implements OnClickListener, OnItemSelectedListener {
    /**
     * Called when the activity is first created.
     */
    EditText nameField, mailField, phoneField, subjectField;
    Spinner subject;
	
	private static DBAdapter db;
    private static System system;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);


        nameField = (EditText) findViewById(R.id.editTextName);
        mailField = (EditText) findViewById(R.id.editTextMail);
        phoneField = (EditText) findViewById(R.id.editTextPhone);
        subjectField = (EditText) findViewById(R.id.editTextMessage);
        subject = (Spinner) findViewById(R.id.spinner);
        subject.setOnItemSelectedListener(this);

		db = new DBAdapter(this);
        db.init();

        system = System.getInstance();

        int userID = system.getUserID();
        if(userID != 0) {
            //User logged in, pre-populate the form with some data
            Cursor c = db.getAccDetails(userID);

            String fname = c.getString(c.getColumnIndex(LacyConstants.TABLE_ACCOUNT_FIRSTNAME));
            String lname = c.getString(c.getColumnIndex(LacyConstants.TABLE_ACCOUNT_LASTNAME));
            String email = c.getString(c.getColumnIndex(LacyConstants.TABLE_ACCOUNT_EMAIL));

            nameField.setText(fname + " " + lname);
            mailField.setText(email);
            phoneField.requestFocus();
        }

        final Button buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (nameField.getText().toString().length() == 0) {
            nameField.setError("Enter your name");
        } else if (mailField.getText().toString().length() == 0) {
            mailField.setError("Enter your email");
        } else if (phoneField.getText().toString().length() != 10) {
            phoneField.setError("Please enter a valid phone number");
        } else if (subjectField.getText().toString().length() == 0) {
            subjectField.setError("Enter your message");
        } else if (subject.getSelectedItemPosition() == 0) {
            Toast.makeText(ContactUs.this, "Please select the Subject", Toast.LENGTH_SHORT).show();
        } else {
            String body =
                    "Name : " + nameField.getText().toString() + "<br>Mobile :" + phoneField.getText().toString() +
                            "<br>Email :" + mailField.getText().toString() + "<br>Message:" + subjectField.getText().toString();

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"thebreakfastclublacys@gmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, subject.getSelectedItem().toString());
            email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
            email.setType("message/rfc822");
            startActivityForResult(Intent.createChooser(email, "Test"), 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        new AlertDialog.Builder(ContactUs.this)
                .setMessage("Your requested has been Accepted\nThank You")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        //Close database
        db.close();
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
                startActivity(new Intent(ContactUs.this, ContactUs.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.home:
                startActivity(new Intent(ContactUs.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
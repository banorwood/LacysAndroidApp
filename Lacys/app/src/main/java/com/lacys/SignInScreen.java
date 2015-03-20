package com.lacys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInScreen extends ActionBarActivity {

    private EditText  username=null;
    private EditText  password=null;
    private TextView attempts;
    private Button login;
    private DBAdapter db;
    int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Create Database Adapter
        db = new DBAdapter(this);
        db.init();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_main);
        username = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPwd);
        attempts = (TextView)findViewById(R.id.textView5);
        attempts.setText(Integer.toString(counter));
        login = (Button)findViewById(R.id.button1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close database
        db.close();
    }

    public void login(View view){
        String user = username.getText().toString();
        String pass =  password.getText().toString();
        Cursor results = db.login(user,pass);
		if((results != null) && (results.getCount() > 0)){
			int accID = results.getInt(0);
			String fName = results.getString(1);
			String lName = results.getString(2);
			String email = results.getString(3);
			if (db.getDEBUG())
				Log.i(db.getLogTag(),  "LOGGING IN! AccID: " + accID + " FirstName: " + fName + " LastName: " + lName + " Email: " + email);
			Toast.makeText(getApplicationContext(), "LOGGING IN! AccID: " + accID + " FirstName: " + fName + " LastName: " + lName + " Email: " + email, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, MainActivity.class));
		}
        else{
            //Toast.makeText(getApplicationContext(), "Wrong Credentials! ", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Account did not exist! It was created...Please try again! ", Toast.LENGTH_SHORT).show();
            /*attempts.setBackgroundColor(Color.RED);
            counter--;
            attempts.setText(Integer.toString(counter));
            if(counter==0){
                login.setEnabled(false);
            }*/

            db.createAccount(user,pass);
        }
    }

    public void onCreateClick(View view) {
        startActivity(new Intent(this, CreateAccount.class));
    }

}
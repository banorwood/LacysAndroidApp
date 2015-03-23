package com.lacys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by blakenorwood on 3/18/15.
 */
public class CreateAccount extends ActionBarActivity{
    private EditText username=null;
    private EditText password=null;
    private EditText confirm=null;
    private EditText first=null;
    private EditText last=null;
    private Button create;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Create Database Adapter
        db = new DBAdapter(this);
        db.init();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_main);
        username = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPwd);
        confirm = (EditText)findViewById(R.id.editTextConfirm);
        first = (EditText)findViewById(R.id.editTextFname);
        last = (EditText)findViewById(R.id.editTextLname);
        create = (Button)findViewById(R.id.button);
    }
    public void create(View view) {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String con = confirm.getText().toString();
        String fst = first.getText().toString();
        String lst = last.getText().toString();
        int i = 0;

        if(user.length()==0) {
            username.setError("Email is required!");
            i = 1;
        }
        if (!isValidEmail(user)){
            username.setError("Please enter a proper email address!");
            i = 1;
        }
        if(pass.length()==0) {
            password.setError("Password is required!");
            i = 1;
        }
        if(pass.length()<=7) {
            password.setError("Password must be at least 6 characters!");
            i = 1;
        }
        if(con.length()==0) {
            confirm.setError("Password is required!");
            i = 1;
        }
        if(!con.equals(pass)) {
            confirm.setError("Passwords must match!");
            i = 1;
        }
        if(fst.length()==0) {
            first.setError("First Name is required!");
            i = 1;
        }
        if(lst.length()==0) {
            last.setError("Last Name is required!");
            i = 1;
        }

        if(i==1)
            return;

        db.createAccount(user, pass, fst, lst);

        Cursor results = db.login(user, pass);
        if ((results != null) && (results.getCount() > 0)) {
            int accID = results.getInt(0);
            String fName = results.getString(1);
            String lName = results.getString(2);
            String email = results.getString(3);
            if (db.getDEBUG())
                Log.i(db.getLogTag(), "LOGGING IN! AccID: " + accID + " FirstName: " + fName + " LastName: " + lName + " Email: " + email);
            Toast.makeText(getApplicationContext(), "LOGGING IN! AccID: " + accID + " FirstName: " + fName + " LastName: " + lName + " Email: " + email, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, MainActivity.class));

        } else {
            //Toast.makeText(getApplicationContext(), "Wrong Credentials! ", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Account did not exist! It was created...Please try again! ", Toast.LENGTH_SHORT).show();
            /*attempts.setBackgroundColor(Color.RED);
            counter--;
            attempts.setText(Integer.toString(counter));
            if(counter==0){
                login.setEnabled(false);
            }*/
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close database
        db.close();
    }


}

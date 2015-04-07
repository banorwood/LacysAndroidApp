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


        //Login screen redirects to create account screen if the account does not exist, so it will prepropulate
        // the username field with the username they attempted to login with.
        try
        {
            String loginUser = (String)getIntent().getExtras().get("user");
            if(!loginUser.equals("")) {
                username.setText(loginUser);
                password.requestFocus();
            }
        }
        catch(Exception e) { }
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
        if(pass.length()<6) {
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

        if(db.accountExists(user))
            Toast.makeText(getApplicationContext(), "Account " + user + " already exists!'", Toast.LENGTH_SHORT).show();
        else {
            if (db.createAccount(user, pass, fst, lst) != -1) {
                Cursor results = db.login(user);
                if ((results != null) && (results.getCount() > 0)) {
                    int accIDResult = results.getInt(0);
                    String emailResult = results.getString(3);
                    String pwdResult = results.getString(4);
                    if (pwdResult.equals(pass)) {
                        Toast.makeText(getApplicationContext(), "Your account " + emailResult + " has successfully been created! Your user id is " + accIDResult, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Password is incorrect.", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Account was not created. Please try again later. ", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getApplicationContext(), "An error occured trying to create the account. Please try again later.", Toast.LENGTH_SHORT).show();
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

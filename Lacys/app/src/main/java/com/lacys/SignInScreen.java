package com.lacys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInScreen extends ActionBarActivity {

    private EditText username = null;
    private EditText password = null;
    private Button login;
    private DBAdapter db;
    private System system;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Create Database Adapter
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_main);
        db = new DBAdapter(this);
        db.init();
        username = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPwd);
        login = (Button) findViewById(R.id.button1);

        system = System.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close database
        db.close();
    }

    public void login(View view) {
        if (system.getUserID() == 0) {
            int i = 0;
            String user = username.getText().toString();
            if (user.length() == 0) {
                username.setError("Email is required!");
                i = 1;
            }
            if (!isValidEmail(user)) {
                username.setError("Please enter a proper email address!");
                i = 1;
            }
            String pass = password.getText().toString();
            if (pass.length() == 0) {
                password.setError("Password is required!");
                i = 1;
            }
            if (pass.length() < 6) {
                password.setError("Password must be at least 6 characters!");
                i = 1;
            }
            if (i == 1)
                return;

            Cursor results = db.login(user);
            if ((results != null) && (results.getCount() > 0)) {
                int accIDResult = results.getInt(results.getColumnIndex(LacyConstants.TABLE_ACCOUNT_ID));
                String fNameResult = results.getString(results.getColumnIndex(LacyConstants.TABLE_ACCOUNT_FIRSTNAME));
                String emailResult = results.getString(results.getColumnIndex(LacyConstants.TABLE_ACCOUNT_EMAIL));
                String pwdResult = results.getString(results.getColumnIndex(LacyConstants.TABLE_ACCOUNT_PASSWORD));
                if (pwdResult.equals(pass)) {
                    Toast.makeText(getApplicationContext(), "Welcome back " + fNameResult + "! Your account email is " + emailResult, Toast.LENGTH_SHORT).show();
                    system.setUserID(accIDResult);

                    //Calls database to get the current order id
                    int orderID = db.getOrderID(accIDResult);
                    //Sets the order id in the system class.
                    system.setOrderID(orderID);
                    /*Since the add to cart will bring the user to login page if they aren't logged in, this will "close" the login page once
                       the user is logged in and goes back to the previous page they were on instead of switching to main activity like normal.
                    */
                    try {
                        String ret = (String) getIntent().getExtras().get("return");
                        finish();
                    } catch (Exception e) {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Password is incorrect.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Account does not exist! Please create one", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "You are already logged in.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void onCreateClick(View view) {
        startActivity(new Intent(this, CreateAccount.class));
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
                startActivity(new Intent(SignInScreen.this, ContactUs.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.home:
                startActivity(new Intent(SignInScreen.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
}


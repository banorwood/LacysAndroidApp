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
import android.widget.LinearLayout;

public class SignInScreen extends ActionBarActivity {

    private EditText  username=null;
    private EditText  password=null;
    private Button login;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Create Database Adapter
        db = new DBAdapter(this);
        db.init();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_main);
        username = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPwd);
        login = (Button)findViewById(R.id.button1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close database
        db.close();
    }

    public void login(View view){
        int i = 0;
        String user = username.getText().toString();
        if(user.length()==0) {
            username.setError("Email is required!");
            i = 1;
        }
        if (!isValidEmail(user)){
            username.setError("Please enter a proper email address!");
            i = 1;
        }
        String pass =  password.getText().toString();
        if(pass.length()==0) {
            password.setError("Password is required!");
            i = 1;
        }
        if(pass.length()<6) {
            password.setError("Password must be at least 6 characters!");
            i = 1;
        }
        if (i==1)
            return;

        Cursor results = db.login(user);
		if((results != null) && (results.getCount() > 0))
        {
			int accIDResult = results.getInt(0);
			String fNameResult = results.getString(1);
			String lNameResult = results.getString(2);
			String emailResult = results.getString(3);
			String pwdResult = results.getString(4);
            if(pwdResult.equals(pass)) {
                if (db.getDEBUG())
                    Log.i(db.getLogTag(), "LOGGING IN! AccID: " + accIDResult + " FirstName: " + fNameResult + " LastName: " + lNameResult + " Email: " + emailResult);
                Toast.makeText(getApplicationContext(), "Welcome back " + fNameResult + "! Your account email is " + emailResult, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, MainActivity.class));
            }
            else {
                Toast.makeText(getApplicationContext(), "Password is incorrect.", Toast.LENGTH_SHORT).show();
            }
		}
        else
        {
            //Toast.makeText(getApplicationContext(), "Wrong Credentials! ", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Account does not exist! Please create one", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this , CreateAccount.class));

            /*attempts.setBackgroundColor(Color.RED);
            counter--;
            attempts.setText(Integer.toString(counter));
            if(counter==0){
                login.setEnabled(false);
            }*/

            //db.createAccount(user,pass);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void onCreateClick(View view) {
        startActivity(new Intent(this, CreateAccount.class));
    }
	


}


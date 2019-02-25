package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    EditText userName;
    EditText userPassword;
    EditText userConformPassword;
    Button registerButton;
    public static  final String EXTRA_USERNAME = "EXTRA_USERNAME";
    public static  final String SHARED_PREFS = "userInformation";
    public static String KEY_USERNAME="";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userName = (EditText)findViewById(R.id.usernameText);
        userPassword = (EditText)findViewById(R.id.passwordText);
        userConformPassword=(EditText)findViewById(R.id.confirmPasswordText);
        registerButton = (Button)findViewById(R.id.registerButton);

        //Receiving username from the Login activity
        Intent rIntent = getIntent();
        String uText = rIntent.getStringExtra(Login.SEND_USERNAME);
        userName.setText(uText);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().toString().equals("") || userPassword.getText().toString().equals("")
                        || userConformPassword.getText().toString().equals("")){
                    Toast.makeText(Registration.this,"All Fields are Mandatory",Toast.LENGTH_LONG).show();
                }
                else if(!userPassword.getText().toString().equals(userConformPassword.getText().toString())){
                    Toast.makeText(Registration.this,"Password Mismatch",Toast.LENGTH_LONG).show();
                }
                else{
                        saveInfo();
                }
            }
        });
    }
    protected void saveInfo(){

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        KEY_USERNAME = userName.getText().toString();

        if(sharedPreferences.contains(KEY_USERNAME)){
            Toast.makeText(Registration.this,"Username already exists! try other names",Toast.LENGTH_SHORT).show();
        }
        else {
            //Inserting Key as password and Value as username into shared_preference
            editor.putString(KEY_USERNAME, userPassword.getText().toString());
            editor.commit();
            Toast.makeText(Registration.this, "Registration Successfull!", Toast.LENGTH_LONG).show();

            //Passing the username to the Login activity
            Intent intent = new Intent();
            intent.putExtra(EXTRA_USERNAME, userName.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}

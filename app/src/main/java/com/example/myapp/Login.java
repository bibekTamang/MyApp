package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView signUp;
    EditText userName;
    EditText passWord;
    Button logIn;
    Button clearButton;
    public static final int REQUEST_CODE = 1;
    public static final String SEND_USERNAME="username";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText)findViewById(R.id.userName);
        passWord = (EditText)findViewById(R.id.userPassword);
        signUp = (TextView)findViewById(R.id.signUpButton);
        logIn = (Button)findViewById(R.id.loginButton);
        clearButton = (Button)findViewById(R.id.clearButton);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openRegistration();
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userName.getText().toString().isEmpty() || passWord.getText().toString().isEmpty()) {

                    Toast.makeText(Login.this,"Both fields required!",Toast.LENGTH_SHORT).show();
                }
                else {
                    validateUser();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences = getSharedPreferences(Registration.SHARED_PREFS, Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Toast.makeText(Login.this, "Cleared", Toast.LENGTH_LONG).show();
            }
        });

    }
    void openRegistration(){
        Intent intent = new Intent(this,Registration.class);
        intent.putExtra(SEND_USERNAME,userName.getText().toString());
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            String receivedUsername = data.getStringExtra(Registration.EXTRA_USERNAME);
            userName.setText(receivedUsername);
        }
        if(requestCode==RESULT_CANCELED){
            userName.setText("");
        }
    }

    void validateUser(){

            sharedPreferences = getSharedPreferences(Registration.SHARED_PREFS, Context.MODE_PRIVATE);
            if(sharedPreferences.contains(userName.getText().toString())){

                String checkPassword = sharedPreferences.getString(userName.getText().toString(),"");
                if(checkPassword.equals(passWord.getText().toString())){
                    Toast.makeText(Login.this, "Logged in", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this,Home.class);
                    intent.putExtra(SEND_USERNAME,userName.getText().toString());
                    userName.setText("");
                    passWord.setText("");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Login.this,"Invalid username or password",Toast.LENGTH_LONG).show();
                    passWord.setText("");
                }
            }
            else {
                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                passWord.setText("");
            }
    }
}

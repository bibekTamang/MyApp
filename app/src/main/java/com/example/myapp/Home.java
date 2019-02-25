package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView text;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        text = (TextView)findViewById(R.id.homeTextView);
        logout = (Button)findViewById(R.id.logoutButton);
        Intent intent = getIntent();
        String str = intent.getStringExtra(Login.SEND_USERNAME);
        text.setText("Welcome "+str);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

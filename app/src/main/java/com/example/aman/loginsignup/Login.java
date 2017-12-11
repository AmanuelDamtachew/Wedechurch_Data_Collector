package com.example.aman.loginsignup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (TextView)findViewById(R.id.name);
        email= (TextView)findViewById(R.id.email);
        Bundle bundle = getIntent().getExtras();
        name.setText("Welcome "+bundle.getString("name"));
        email.setText("Email "+bundle.getString("email"));
    }
}

package com.example.vishalmahajan.denmhouse.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.vishalmahajan.denmhouse.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String MyPREFERENCES = "Session";
    SharedPreferences sharedpreferences;
    Button loginButton, registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        SessionManager sessionManager = new SessionManager(SplashActivity.this);
        sessionManager.checkLogin();

     //   sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

/*
    public void checkedLogedIn(){

        String userEmail = sharedpreferences.getString("email", "");
        String userPassword = sharedpreferences.getString("password", "");
        if(!userEmail.contains("") && !userPassword.contains("")){
            Intent intent = new Intent(SplashActivity.this, MainDashboardActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        finish();

    }
*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
               // checkedLogedIn();
                break;

            case R.id.registerButton:
                Intent intentRegisterActivity = new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(intentRegisterActivity);
                break;

        }
    }
}

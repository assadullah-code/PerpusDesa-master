package com.example.perpusdesa.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perpusdesa.R;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        preferences = getSharedPreferences("Login_pref",MODE_PRIVATE);
        editor = preferences.edit();

        boolean logged_in = preferences.getBoolean("logged_in", false);

        if (logged_in){
            intent = new Intent(SplashScreen.this, Login.class);
        } else {
            intent = new Intent(SplashScreen.this, Registration.class);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
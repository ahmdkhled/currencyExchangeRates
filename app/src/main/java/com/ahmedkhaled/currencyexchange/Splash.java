package com.ahmedkhaled.currencyexchange;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private final float SPLASH_TIME = 1.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // this code inside runnable will execute once the time is over
                // start the MainActivity
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //close the activity
                finish();
            }
        }, 1500);
    }
}

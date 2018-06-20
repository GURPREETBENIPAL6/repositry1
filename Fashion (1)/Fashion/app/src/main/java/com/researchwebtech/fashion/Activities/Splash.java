package com.researchwebtech.fashion.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.researchwebtech.fashion.R;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
// *******Checking User is First time login or not *****//
                String email = pref.getString("email", null);
                String password = pref.getString("pass", null);

                Log.e("pass", "" + password);
                Log.e("emial", "" + email);

                if (email == null && password == null) {
                    Intent i = new Intent(Splash.this, Login_Mode.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(Splash.this, Home.class);
                    startActivity(i);
                    finish();
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}


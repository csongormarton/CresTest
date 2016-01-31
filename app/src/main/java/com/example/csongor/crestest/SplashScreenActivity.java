package com.example.csongor.crestest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;

import com.example.csongor.crestest.Models.User;

import java.io.File;


public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    public static File directroy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.directroy = this.getDir("imageDir", Context.MODE_PRIVATE);

        /////////////////////////////
        DatabaseSeeder databaseSeeder = new DatabaseSeeder(this);
        databaseSeeder.seed();
        /////////////////////////////

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun)
        {
            ///////////////////
            User user = new User("Admin", "admin@valami.hu", "123456", true);
            user.save();
            User user2 = new User("User", "user@valami.hu", "123456", false);
            user2.save();

            ///////////////////
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent nextActivity = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(nextActivity);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

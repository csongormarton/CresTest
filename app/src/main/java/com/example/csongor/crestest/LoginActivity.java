package com.example.csongor.crestest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.csongor.crestest.Models.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        User user = new User(1, "Arni", "valami2@valami.hu", "123456", false);
//        Log.e("User", user.toString());
//        user.save();

        List<User> users = User.listAll(User.class);
        for(int i=0; i<users.size(); i++){
            Log.e("User", users.get(i).toString());
        }
    }

}

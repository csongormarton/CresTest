package com.example.csongor.crestest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csongor.crestest.Models.Answer;
import com.example.csongor.crestest.Models.Question;
import com.example.csongor.crestest.Models.User;

import java.util.List;


public class LoginActivity extends AppCompatActivity {

    String loginEmail, loginPassword;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Question question = new Question("Hany eves a kapitany", "picture", true);
        question.save();
        Answer answer1 = new Answer(question, "50", true);
        Answer answer2 = new Answer(question, "nem 50", false);
        answer1.save();
        answer2.save();

        List<Question> qestions = Question.listAll(Question.class);
        for(Question q : qestions){
            //Log.e("QUESTION: ", q.toString());
            List<Answer> answers = q.getAnswers();
            for (Answer a : answers){
                //Log.e("ANSWER : ", a.toString());
            }
        }
        ////////////////////////////////////////////

        Button btnLogin = (Button)this.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidDetails()) {
                    try {
                        user = User.find(User.class, "email = ? and password = ?", loginEmail, loginPassword).get(0);
                        goToNextActivity();

                    } catch (IndexOutOfBoundsException ex) {
                        Toast.makeText(LoginActivity.this, "Rossz felhasználónév, vagy jelszó!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        TextView tvRegistration = (TextView)this.findViewById(R.id.tv_registration);
        tvRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();
            }
        });

    }

    public boolean isValidDetails(){
        EditText etLoginEmail = (EditText)this.findViewById(R.id.et_login_email);
        EditText etLoginPassword = (EditText)this.findViewById(R.id.et_login_password);

        if(!Patterns.EMAIL_ADDRESS.matcher(etLoginEmail.getText().toString()).matches()) {
            etLoginEmail.setError(this.getString(R.string.string_bad_email));
            return false;
        }
        if(etLoginPassword.getText().length() == 0) {
            etLoginPassword.setError(this.getString(R.string.string_empty_password_field));
            return false;
        }

        loginEmail = etLoginEmail.getText().toString();
        loginPassword = etLoginPassword.getText().toString();

        return true;
    }

    public void goToNextActivity(){
        Intent intent;
        try {
            if(user.isAdmin()){
                intent = new Intent(LoginActivity.this, AdminActivity.class);
            }
            else {
                intent = new Intent(LoginActivity.this, TestActivity.class);
            }

            startActivity(intent);
            finish();
        }
        catch (NullPointerException ex){
            intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }
    }

}

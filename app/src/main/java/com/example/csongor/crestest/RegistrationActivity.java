package com.example.csongor.crestest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button btnRegistration = (Button)this.findViewById(R.id.btn_registration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidDetails()){

                }
            }
        });
    }

    public boolean isValidDetails(){
        EditText etRegistrationName = (EditText)this.findViewById(R.id.et_registration_name);
        EditText etRegistrationEmail = (EditText)this.findViewById(R.id.et_registration_email);
        EditText etRegistrationPassword1 = (EditText)this.findViewById(R.id.et_registration_password1);
        EditText etRegistrationPassword2 = (EditText)this.findViewById(R.id.et_registration_password2);

        return true;
    }
}

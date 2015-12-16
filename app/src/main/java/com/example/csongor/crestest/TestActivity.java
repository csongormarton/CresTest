package com.example.csongor.crestest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;

public class TestActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final CheckedTextView ctvAnswerA = (CheckedTextView)this.findViewById(R.id.ctv_test_answerA);
        final CheckedTextView ctvAnswerB = (CheckedTextView)this.findViewById(R.id.ctv_test_answerB);
        final CheckedTextView ctvAnswerC = (CheckedTextView)this.findViewById(R.id.ctv_test_answerC);

        ctvAnswerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctvAnswerA.isChecked())
                    ctvAnswerA.setChecked(false);
                else
                    ctvAnswerA.setChecked(true);
            }
        });

        ctvAnswerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctvAnswerB.isChecked())
                    ctvAnswerB.setChecked(false);
                else
                    ctvAnswerB.setChecked(true);
            }
        });

        ctvAnswerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctvAnswerC.isChecked())
                    ctvAnswerC.setChecked(false);
                else
                    ctvAnswerC.setChecked(true);
            }
        });
    }
}

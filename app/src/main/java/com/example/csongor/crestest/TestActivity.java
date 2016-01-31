package com.example.csongor.crestest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csongor.crestest.Models.Answer;
import com.example.csongor.crestest.Models.Question;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestActivity extends AppCompatActivity {

    private List<Question> questions;
    private String path = SplashScreenActivity.directroy.getAbsolutePath();

    private ImageView ivTestPicture;
    private TextView tvQuestion;
    private CheckedTextView ctvAnswerA;
    private CheckedTextView ctvAnswerB;
    private CheckedTextView ctvAnswerC;
    private Button btnBack;
    private Button btnNext;

    private int questionsNumber = 10;
    private int questionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ivTestPicture = (ImageView)this.findViewById(R.id.iv_test_picture);
        tvQuestion = (TextView)this.findViewById(R.id.tv_test_question);
        ctvAnswerA = (CheckedTextView)this.findViewById(R.id.ctv_test_answerA);
        ctvAnswerB = (CheckedTextView)this.findViewById(R.id.ctv_test_answerB);
        ctvAnswerC = (CheckedTextView)this.findViewById(R.id.ctv_test_answerC);
        btnBack = (Button)this.findViewById(R.id.btn_test_back);
        btnNext = (Button)this.findViewById(R.id.btn_test_next);


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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQuestionAndAnswers(questions.get(questionIndex));
                questionIndex--;
                if(questionIndex < 0){
                    questionIndex = 0;
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionIndex < questionsNumber) {
                    setQuestionAndAnswers(questions.get(questionIndex));
                    questionIndex++;
                }
                else{

                }
            }
        });

        questionIndex = 0;
        questions = loadQuestions();
        setQuestionAndAnswers(questions.get(questionIndex));
        questionIndex++;

    }

    private List<Question> loadQuestions(){
        List<Question> q = Question.find(Question.class, "is_active = ?", "1");
        long seed = System.nanoTime();
        Collections.shuffle(q, new Random(seed));

        return q;
    }

    private void setQuestionAndAnswers(Question question){
        btnNext.setText(R.string.string_btn_test_next);
        ivTestPicture.setImageBitmap(null);

        if(questionIndex == questionsNumber-1){
            btnNext.setText(R.string.string_btn_test_submint);
        }

        if(!question.getPicture().isEmpty()){
            ivTestPicture.setImageBitmap(loadImageFromStorage(path, question.getPicture()));
        }

        tvQuestion.setText(question.getQuestion());
        List<Answer> answers = question.getAnswers();
        ctvAnswerA.setText(answers.get(0).getAnswer());
        ctvAnswerB.setText(answers.get(1).getAnswer());
        ctvAnswerC.setText(answers.get(2).getAnswer());
    }

    public Bitmap loadImageFromStorage(String path, String filename){
        Bitmap bitmap = null;
        try {
            File f = new File(path, filename);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException ex){}

        return bitmap;
    }
}

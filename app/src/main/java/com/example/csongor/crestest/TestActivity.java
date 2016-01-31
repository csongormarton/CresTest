package com.example.csongor.crestest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.csongor.crestest.Models.Answer;
import com.example.csongor.crestest.Models.Question;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TestActivity extends AppCompatActivity {

    private List<Question> questions;
    private Map<Long, List<Integer>> checkedAnswers;
    private String path = SplashScreenActivity.directroy.getAbsolutePath();

    private ImageView ivTestPicture;
    private TextView tvQuestion;
    private CheckedTextView[] ctvAnswers;
    private Button btnBack;
    private Button btnNext;

    private int questionsNumber = 4;
    private int questionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ivTestPicture = (ImageView)this.findViewById(R.id.iv_test_picture);
        tvQuestion = (TextView)this.findViewById(R.id.tv_test_question);
//        ctvAnswerA = (CheckedTextView)this.findViewById(R.id.ctv_test_answerA);
//        ctvAnswerB = (CheckedTextView)this.findViewById(R.id.ctv_test_answerB);
//        ctvAnswerC = (CheckedTextView)this.findViewById(R.id.ctv_test_answerC);
        btnBack = (Button)this.findViewById(R.id.btn_test_back);
        btnNext = (Button)this.findViewById(R.id.btn_test_next);

        ctvAnswers = new CheckedTextView[3];
        ctvAnswers[0] = (CheckedTextView)this.findViewById(R.id.ctv_test_answerA);
        ctvAnswers[1] = (CheckedTextView)this.findViewById(R.id.ctv_test_answerB);
        ctvAnswers[2] = (CheckedTextView)this.findViewById(R.id.ctv_test_answerC);

        for(int i = 0;i < 3;i++){
            final int finalI = i;
            ctvAnswers[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ctvAnswers[finalI].isChecked())
                        ctvAnswers[finalI].setChecked(false);
                    else
                        ctvAnswers[finalI].setChecked(true);
                }
            });
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCheckedAnswer(questions.get(questionIndex).getId());
                questionIndex--;
                if(questionIndex < 0){
                    questionIndex = 0;
                }
                setQuestionAndAnswers(questions.get(questionIndex));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctvAnswers[0].isChecked() || ctvAnswers[1].isChecked() || ctvAnswers[2].isChecked()){
                    if(questionIndex < questionsNumber-1) {
                        saveCheckedAnswer(questions.get(questionIndex).getId());
                        questionIndex++;
                        setQuestionAndAnswers(questions.get(questionIndex));
                    }
                    else{

                        saveCheckedAnswer(questions.get(questionIndex).getId());
                        showEvaluationAlert();
                    }
                }
                else {
                    showErrorAlert();
                }
            }
        });

        checkedAnswers = new HashMap<>();
        questionIndex = 0;
        questions = loadQuestions();
        for(Question q : questions){
            checkedAnswers.put(q.getId(), new ArrayList<Integer>());
        }
        setQuestionAndAnswers(questions.get(questionIndex));

    }

    private List<Question> loadQuestions(){
        List<Question> q = Question.find(Question.class, "is_active = ?", "1");
        long seed = System.nanoTime();
        Collections.shuffle(q, new Random(seed));
        List<Question> returnList = new ArrayList<>();
        for(int i=0; i<questionsNumber; i++){
            returnList.add(q.get(i));
        }

        return returnList;
    }

    private void setQuestionAndAnswers(Question question){
        btnNext.setText(R.string.string_btn_test_next);
        ivTestPicture.setImageBitmap(null);
        for(int i = 0;i< 3;i++){
            this.ctvAnswers[i].setChecked(false);
        }

        List<Integer> checkedAnswers = this.checkedAnswers.get(question.getId());
        for(Integer ca : checkedAnswers){
            this.ctvAnswers[ca].setChecked(true);
        }

        if(questionIndex == questionsNumber-1){
            btnNext.setText(R.string.string_btn_test_submint);
        }

        if(!question.getPicture().isEmpty()){
            ivTestPicture.setImageBitmap(loadImageFromStorage(path, question.getPicture()));
        }

        tvQuestion.setText(question.getQuestion());
        List<Answer> answers = question.getAnswers();
        for(int i = 0;i < 3;i++){
            this.ctvAnswers[i].setText((answers.get(i).getAnswer()));
        }
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

    public void showEvaluationAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
        alertDialogBuilder.setTitle("Eredmény!");
        alertDialogBuilder
                .setMessage("Pontszám: " + evaluate())
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void showErrorAlert() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
        alertDialogBuilder.setTitle("Figyelem!");
        alertDialogBuilder
                .setMessage(R.string.string_no_checked)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void saveCheckedAnswer(long id){
        List<Integer> checkedAnswer = new ArrayList<>();
        for(int i = 0;i < 3;i++){
            if(this.ctvAnswers[i].isChecked()){
                checkedAnswer.add(i);

            }
        }
        this.checkedAnswers.put(id, checkedAnswer);
    }

    private int evaluate(){
        int score = 0;
        /*for(Map.Entry<Long, List<Integer>> entry : this.checkedAnswers.entrySet()){
            List<Answer> answers = getAnswerOfQuestion(entry.getKey());
            for(int i=0; i<answers.size(); i++){
                if(entry.getValue().contains(i) && answers.get(i).isCorrect()){
                    score++;
                }
            }
        }*/
        for(Question q : questions){
            List<Integer> ca = this.checkedAnswers.get(q.getId());
            int isCorrect = 0;
            int correctAnswersNumber = q.getCorrectAnswers().size();
            List<Boolean> booleans = getAnswerOfQuestion(q.getId());
            for (int i=0; i<ca.size(); i++){
                if(booleans.get(ca.get(i))){
                    isCorrect++;
                }
            }
            if(isCorrect == correctAnswersNumber){
                score++;
            }
        }
        return score;
    }

    private List<Boolean> getAnswerOfQuestion(long id){
        List<Boolean> answers = new ArrayList<>();
        for(Question q : questions){
            if(q.getId() == id){
                for(Answer a : q.getAnswers()){
                    answers.add(a.isCorrect());
                }
            }
        }

        return answers;
    }
}

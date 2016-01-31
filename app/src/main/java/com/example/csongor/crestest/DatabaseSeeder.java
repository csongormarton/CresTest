package com.example.csongor.crestest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.csongor.crestest.Models.Answer;
import com.example.csongor.crestest.Models.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class DatabaseSeeder {

    private String[] questions;
    private Context context;
    private Map<Long, String> pictures;

    public DatabaseSeeder(Context context) {
        pictures = new HashMap<Long, String>();
        this.context = context;
    }

    private String readJson(){
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open("questions.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException ex){}

        return json;
    }

    public void seed(){
        try {
            JSONObject jsonObject = new JSONObject(readJson());
            JSONArray jsonArray = jsonObject.getJSONArray("questions");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jo = jsonArray.getJSONObject(i);
                String question = jo.getString("question");
                Question q = new Question(question, "", true);
                q.save();

                if(!jo.isNull("picture")){
                    String picture = jo.getString("picture");
                    this.pictures.put(q.getId(), picture);
                }

                JSONArray ja = jo.getJSONArray("answers");
                int correctAnswer = jo.getInt("correct_answer");
                for(int j=0; j<ja.length(); j++){
                    String answer = ja.getString(j);
                    Answer a = new Answer(q, answer, correctAnswer == j+1);
                    a.save();
                }

            }
        }
        catch (JSONException ex){}

        DownloadPictureAsyncTask dpat = new DownloadPictureAsyncTask(SplashScreenActivity.directroy, this.pictures);
        dpat.execute();
    }

    public void logDatabase(){
        /*
        List<Question> questions = Question.listAll(Question.class);
        Log.e("Valami: ", questions.size() + "");
        Log.e("Pictures: ", this.pictures.size() + "");
        for(Question q : questions){
            //Log.e("QUESTION: ", q.toString());
            List<Answer> answers = q.getAnswers();
            for (Answer a : answers){
                //Log.e("ANSWER: ", a.toString());
            }
        }
        */
    }

}

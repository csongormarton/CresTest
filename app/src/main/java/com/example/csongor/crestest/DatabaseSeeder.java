package com.example.csongor.crestest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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
import java.util.Random;
import java.util.UUID;

public class DatabaseSeeder {

    private String[] questions;
    private Context context;


    public DatabaseSeeder(Context context) {
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
        Question q = new Question("Hany eves a kapitany", "", true);
        DownloadPictureAsyncTask dpat = new DownloadPictureAsyncTask(SplashScreenActivity.directroy, q);
        dpat.execute("http://www.sosjogsi.hu/images/article/kresz-teszt/241.jpg");

        /*try {
            JSONObject jsonObject = new JSONObject(readJson());
            JSONArray jsonArray = jsonObject.getJSONArray("questions");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jo = jsonArray.getJSONObject(i);
                String question = jo.getString("question");
                String picture = jo.getString("picture");


                JSONArray ja = jo.getJSONArray("answers");
                for(int j=0; j<ja.length(); j++){
                    String answer = ja.getString(j);
                }

            }
        }
        catch (JSONException ex){}*/
    }

}

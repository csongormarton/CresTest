package com.example.csongor.crestest;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.csongor.crestest.Models.Answer;
import com.example.csongor.crestest.Models.Question;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class DownloadPictureAsyncTask extends AsyncTask<String, Void, String> {
    private File directory;
    private Map<Long, String> pictures;


    public DownloadPictureAsyncTask(File directory, Map<Long, String> pictures){
        this.directory = directory;
        this.pictures = pictures;
    }

    @Override
    protected String doInBackground(String... params) {
        for (Map.Entry<Long, String> entry : pictures.entrySet()){
            String filename = savePicture(loadPicture(entry.getValue()));
            Question q = Question.findById(Question.class, entry.getKey());
            q.setPicture(filename);
            q.save();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        List<Question> questions = Question.listAll(Question.class);
        Log.e("Valami: ", questions.size() + "");
        Log.e("Pictures: ", this.pictures.size() + "");
        for(Question q : questions){
            Log.e("QUESTION: ", q.toString());
            List<Answer> answers = q.getAnswers();
            for (Answer a : answers){
                Log.e("ANSWER: ", a.toString());
            }
        }
    }

    public Bitmap loadPicture(String url){
        Bitmap bitmap = null;

        try{
            URL imageURL = new URL(url);
            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

        }
        catch (MalformedURLException ex){}
        catch (IOException ex){}

        return bitmap;
    }

    public String savePicture(Bitmap bitmap){
        String name = String.format("%s.%s", UUID.randomUUID().toString(), "jpg");
        File myPath = new File(directory, name);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        }
        catch (FileNotFoundException ex){}
        catch (IOException ex){}

        return name;
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

    public String getAbsolutePath(){
        return directory.getAbsolutePath();
    }
}

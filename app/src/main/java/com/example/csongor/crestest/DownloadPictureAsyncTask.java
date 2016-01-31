package com.example.csongor.crestest;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.csongor.crestest.Models.Question;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

class DownloadPictureAsyncTask extends AsyncTask<String, Void, String> {
    private File directory;
    private Question question;

    public DownloadPictureAsyncTask(File directory, Question question){
        this.directory = directory;
        this.question = question;
    }

    @Override
    protected String doInBackground(String... params) {
        String filename = savePicture(loadPicture(params[0]));

        return filename;
    }

    @Override
    protected void onPostExecute(String s) {
        question.setPicture(s);
        Log.e("QUESTION: ", question.toString());
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

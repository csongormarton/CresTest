package com.example.csongor.crestest.Models;

import com.orm.SugarRecord;

import java.util.List;

public class Question extends SugarRecord{

    String question;
    String picture;
    boolean isActive;

    public Question() {
    }

    public Question(String question, String picture, boolean isActive) {
        this.question = question;
        this.picture = picture;
        this.isActive = isActive;
    }

    public List<Answer> getAnswers(){
        return Answer.find(Answer.class, "question = ?", new String[]{getId().toString()});
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsInclude(boolean isInclude) {
        this.isActive = isInclude;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", picture='" + picture + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

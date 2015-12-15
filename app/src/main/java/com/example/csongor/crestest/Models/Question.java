package com.example.csongor.crestest.Models;

import com.orm.SugarRecord;

/**
 * Created by Medea on 2015-12-14.
 */
public class Question extends SugarRecord<Question>{

    int questionID;
    String question;
    String picture;
    boolean isInclude;

    public Question() {
    }

    public Question(int questionID, String question, String picture, boolean isInclude) {
        this.questionID = questionID;
        this.question = question;
        this.picture = picture;
        this.isInclude = isInclude;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
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

    public boolean isInclude() {
        return isInclude;
    }

    public void setIsInclude(boolean isInclude) {
        this.isInclude = isInclude;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionID=" + questionID +
                ", question='" + question + '\'' +
                ", picture='" + picture + '\'' +
                ", isInclude=" + isInclude +
                '}';
    }
}

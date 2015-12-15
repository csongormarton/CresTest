package com.example.csongor.crestest.Models;

import com.orm.SugarRecord;

/**
 * Created by Medea on 2015-12-14.
 */
public class Answer extends SugarRecord<Answer>{

    String answer;
    boolean isCorrect;
    int questionID;

    public Answer() {
    }

    public Answer(String answer, boolean isCorrect, int questionID) {
        this.answer = answer;
        this.isCorrect = isCorrect;
        this.questionID = questionID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                ", isCorrect=" + isCorrect +
                ", questionID=" + questionID +
                '}';
    }
}

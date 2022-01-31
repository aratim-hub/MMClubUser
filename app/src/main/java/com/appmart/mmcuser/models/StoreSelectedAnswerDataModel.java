package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class StoreSelectedAnswerDataModel {
    private int question_id;
    private boolean answer;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
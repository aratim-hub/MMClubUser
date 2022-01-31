package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MCQListData {
    private String question_id,question,option_a,option_b, correct_answer;

    public MCQListData(String question_id, String question, String option_a, String option_b, String correct_answer) {
        this.question_id = question_id;
        this.question = question;
        this.option_a = option_a;
        this.option_b =option_b;
        this.correct_answer = correct_answer;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption_a() {
        return option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }
}
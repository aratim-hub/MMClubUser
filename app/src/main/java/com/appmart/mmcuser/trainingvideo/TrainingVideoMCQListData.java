package com.appmart.mmcuser.trainingvideo;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class TrainingVideoMCQListData {
    private String question_id,question,option_a,option_b,option_c,option_d, correct_answer;

    public TrainingVideoMCQListData(String question_id, String question, String option_a, String option_b, String option_c, String option_d, String correct_answer) {
        this.question_id = question_id;
        this.question = question;
        this.option_a = option_a;
        this.option_b =option_b;
        this.option_c =option_c;
        this.option_d =option_d;
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

    public String getOption_c() {
        return option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }
}
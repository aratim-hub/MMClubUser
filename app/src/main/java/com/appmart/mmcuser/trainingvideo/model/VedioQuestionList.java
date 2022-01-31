package com.appmart.mmcuser.trainingvideo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VedioQuestionList {

    @SerializedName("question_id")
    @Expose
    private String question_id;
    @SerializedName("tv_id")
    @Expose
    private String tv_id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("option_a")
    @Expose
    private String option_a;
    @SerializedName("option_b")
    @Expose
    private String option_b;
    @SerializedName("option_c")
    @Expose
    private String option_c;
    @SerializedName("option_d")
    @Expose
    private String option_d;
    @SerializedName("correct_answer")
    @Expose
    private String correct_answer;
    @SerializedName("correct_option")
    @Expose
    private String correct_option;
    @SerializedName("my_answer")
    @Expose
    private String my_answer;
    @SerializedName("status")
    @Expose
    private String status;

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getTv_id() {
        return tv_id;
    }

    public void setTv_id(String tv_id) {
        this.tv_id = tv_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public void setOption_d(String option_d) {
        this.option_d = option_d;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getCorrect_option() {
        return correct_option;
    }

    public void setCorrect_option(String correct_option) {
        this.correct_option = correct_option;
    }

    public String getMy_answer() {
        return my_answer;
    }

    public void setMy_answer(String my_answer) {
        this.my_answer = my_answer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VedioQuestionList{" +
                "question_id='" + question_id + '\'' +
                ", tv_id='" + tv_id + '\'' +
                ", question='" + question + '\'' +
                ", option_a='" + option_a + '\'' +
                ", option_b='" + option_b + '\'' +
                ", option_c='" + option_c + '\'' +
                ", option_d='" + option_d + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", correct_option='" + correct_option + '\'' +
                ", my_answer='" + my_answer + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

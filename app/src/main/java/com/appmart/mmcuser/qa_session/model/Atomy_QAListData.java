package com.appmart.mmcuser.qa_session.model;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class Atomy_QAListData {
    private String qa_id;
    private String question;
    private String answer;
    private String category;
    private String youtube_video_url;
    private String youtube_video_id;

    public Atomy_QAListData(String qa_id, String question, String answer, String category, String youtube_video_url, String youtube_video_id) {
        this.qa_id = qa_id;
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.youtube_video_url = youtube_video_url;
        this.youtube_video_id = youtube_video_id;

    }

    public String getQa_id() {
        return qa_id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCategory() {
        return category;
    }

    public String getYoutube_video_url() {
        return youtube_video_url;
    }

    public String getYoutube_video_id() {
        return youtube_video_id;
    }
}
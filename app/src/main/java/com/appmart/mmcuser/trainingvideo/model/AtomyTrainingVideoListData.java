package com.appmart.mmcuser.trainingvideo.model;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class AtomyTrainingVideoListData {
    private String video_id;
    private String video_title;
    private String video_desc;
    private String video_url;
    private String video_url_id;
    private String video_category;
    private String video_questions;
    private String video_seen_status;

    public AtomyTrainingVideoListData(String video_id, String video_title, String video_desc, String video_url, String video_url_id, String video_category, String video_questions, String video_seen_status) {
        this.video_id = video_id;
        this.video_title = video_title;
        this.video_desc = video_desc;
        this.video_url = video_url;
        this.video_url_id = video_url_id;
        this.video_category = video_category;
        this.video_questions = video_questions;
        this.video_seen_status = video_seen_status;

    }

    public String getVideo_id() {
        return video_id;
    }

    public String getVideo_title() {
        return video_title;
    }

    public String getVideo_desc() {
        return video_desc;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getVideo_url_id() {
        return video_url_id;
    }

    public String getVideo_category() {
        return video_category;
    }

    public String getVideo_questions() {
        return video_questions;
    }

    public String getVideo_seen_status() {
        return video_seen_status;
    }
}
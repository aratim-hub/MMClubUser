package com.appmart.mmcuser.sponsor_training.model;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SponsorTrainingContentListData {
    private String bgc_id;
    private String title;
    private String content;

    public SponsorTrainingContentListData(String bgc_id, String title, String content) {
        this.bgc_id = bgc_id;
        this.title = title;
        this.content = content;
    }

    public String getBgc_id() {
        return bgc_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
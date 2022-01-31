package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class NewsListData {
    private String news_id;
    private String news_title;
    private String news_body;
    private String news_url;
    private String created_at;
    private String status;
    private String publish_by;

    public String getNews_id() {
        return news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public String getNews_body() {
        return news_body;
    }

    public String getNews_url() {
        return news_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getStatus() {
        return status;
    }

    public String getPublish_by() {
        return publish_by;
    }

    public NewsListData(String news_id, String news_title, String news_body, String news_url, String created_at, String status, String publish_by) {
        this.news_id = news_id;
        this.news_title = news_title;
        this.news_body = news_body;
        this.news_url = news_url;
        this.created_at = created_at;
        this.status = status;
        this.publish_by = publish_by;

    }


}
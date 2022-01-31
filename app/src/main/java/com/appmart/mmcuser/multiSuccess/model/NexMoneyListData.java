package com.appmart.mmcuser.multiSuccess.model;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class NexMoneyListData {
    private String msuccess_id;
    private String msuccess_title;
    private String msuccess_desc;
    private String msuccess_image;
    private String msuccess_image_url;
    private String msuccess_category;
    private String msuccess_video_url;
    private String youtube_video_id;


    public String getMsuccess_id() {
        return msuccess_id;
    }

    public String getMsuccess_title() {
        return msuccess_title;
    }

    public String getMsuccess_desc() {
        return msuccess_desc;
    }

    public String getMsuccess_image() {
        return msuccess_image;
    }

    public String getMsuccess_image_url() {
        return msuccess_image_url;
    }

    public String getMsuccess_category() {
        return msuccess_category;
    }

    public String getMsuccess_video_url() {
        return msuccess_video_url;
    }

    public String getYoutube_video_id() {
        return youtube_video_id;
    }

    public NexMoneyListData(String msuccess_id, String msuccess_title, String msuccess_desc, String msuccess_image, String msuccess_image_url, String msuccess_category, String msuccess_video_url, String youtube_video_id) {
        this.msuccess_id = msuccess_id;
        this.msuccess_title = msuccess_title;
        this.msuccess_desc = msuccess_desc;
        this.msuccess_image = msuccess_image;
        this.msuccess_image_url = msuccess_image_url;
        this.msuccess_category = msuccess_category;
        this.msuccess_video_url = msuccess_video_url;
        this.youtube_video_id = youtube_video_id;

    }


}
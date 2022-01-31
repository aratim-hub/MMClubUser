package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class AchievmentListData {
    private String image_id;
    private String image_title;
    private String image;
    private String image_url;
    private String created_at;

    public String getImage_id() {
        return image_id;
    }

    public String getImage_title() {
        return image_title;
    }

    public String getImage() {
        return image;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getCreated_at() {
        return created_at;
    }

      public AchievmentListData(String image_id, String image_title, String image, String image_url, String created_at) {
        this.image_id = image_id;
        this.image_title = image_title;
        this.image = image;
        this.image_url = image_url;
        this.created_at = created_at;

    }


}
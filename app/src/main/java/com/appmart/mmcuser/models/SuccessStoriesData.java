package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SuccessStoriesData {
    private String success_story_id;
    private String success_story_title;
    private String success_story_desc;
    private String success_story_url;
    private String success_story_url_id;
    private String created_at;

    public String getSuccess_story_id() {
        return success_story_id;
    }

    public String getSuccess_story_title() {
        return success_story_title;
    }

    public String getSuccess_story_desc() {
        return success_story_desc;
    }

    public String getSuccess_story_url() {
        return success_story_url;
    }

    public String getSuccess_story_url_id() {
        return success_story_url_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public SuccessStoriesData(String success_story_id, String success_story_title, String success_story_desc, String success_story_url, String success_story_url_id, String created_at) {
        this.success_story_id = success_story_id;
        this.success_story_title = success_story_title;
        this.success_story_desc = success_story_desc;
        this.success_story_url = success_story_url;
        this.success_story_url_id = success_story_url_id;
        this.created_at = created_at;

    }

}
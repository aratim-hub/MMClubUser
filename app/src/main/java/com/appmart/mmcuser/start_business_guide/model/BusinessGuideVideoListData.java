package com.appmart.mmcuser.start_business_guide.model;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class BusinessGuideVideoListData {
    private String video_id;
    private String video_title;
    private String video_url;
    private String video_url_id;

    public BusinessGuideVideoListData(String bgv_id, String video_title, String video_url, String video_url_id) {
        this.video_id = bgv_id;
        this.video_title = video_title;
        this.video_url = video_url;
        this.video_url_id = video_url_id;

    }

    public String getVideo_id() {
        return video_id;
    }

    public String getVideo_title() {
        return video_title;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getVideo_url_id() {
        return video_url_id;
    }
}
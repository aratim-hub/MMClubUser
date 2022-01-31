package com.appmart.mmcuser.sponsor_training.model;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SponsorTrainingVideoListData {
    private String bgv_id, video_title, video_url, video_url_id;

    public SponsorTrainingVideoListData(String bgv_id, String video_title, String video_url, String video_url_id) {
        this.bgv_id = bgv_id;
        this.video_title = video_title;
        this.video_url = video_url;
        this.video_url_id = video_url_id;
    }

    public String getBgv_id() {
        return bgv_id;
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
package com.appmart.mmcuser.marketing_material;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MarketingVideoListData {
    private String video_id;
    private String video_title;
    private String video_desc;
    private String video_url_id;

    public MarketingVideoListData(String video_id, String video_title, String video_desc, String video_url_id) {
        this.video_id = video_id;
        this.video_title = video_title;
        this.video_desc = video_desc;
        this.video_url_id = video_url_id;

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

    public String getVideo_url_id() {
        return video_url_id;
    }
}
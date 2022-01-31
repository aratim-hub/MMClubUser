package com.appmart.mmcuser.marketing_material;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MarketingContentListData {
    private String content_id;
    private String content;

    public MarketingContentListData(String content_id, String content) {
        this.content_id = content_id;
        this.content = content;

    }

    public String getContent_id() {
        return content_id;
    }

    public String getContent() {
        return content;
    }
}
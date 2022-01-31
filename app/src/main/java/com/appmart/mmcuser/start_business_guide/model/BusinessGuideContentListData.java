package com.appmart.mmcuser.start_business_guide.model;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class BusinessGuideContentListData {
    private String bgc_id;
    private String title;
    private String content;
    private int arrange_id;
    private int part_id;

    public BusinessGuideContentListData(String bgc_id, String title, String content, String arrange_id, String part_id) {
        this.bgc_id = bgc_id;
        this.title = title;
        this.content = content;
        this.arrange_id = Integer.parseInt(arrange_id);
        this.part_id = Integer.parseInt(part_id);
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

    public Integer getArrange_id() { return arrange_id; }

    public Integer getPart_id() { return part_id; }
}
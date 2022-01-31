package com.appmart.mmcuser.events;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class EventListData {
    private String event_id;
    private String event_img_url;
    private String event_description;
    private String event_date;
    private String event_time;
    private String event_link;

    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_img_url() {
        return event_img_url;
    }

    public String getEvent_description() {
        return event_description;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public String getEvent_link() {
        return event_link;
    }

    public EventListData(String event_id, String event_img_url, String event_description, String event_date, String event_time, String event_link) {
        this.event_id = event_id;
        this.event_img_url = event_img_url;
        this.event_description = event_description;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_link = event_link;
    }


}
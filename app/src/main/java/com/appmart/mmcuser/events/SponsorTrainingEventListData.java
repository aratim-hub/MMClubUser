package com.appmart.mmcuser.events;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SponsorTrainingEventListData {
    private String online_event_id, controller, controller_id, monitor,host,
            event_title, event_date, event_time,  event_zoom_link,
             event_status, event_for;

    public SponsorTrainingEventListData(String online_event_id, String controller_id,String controller, String monitor, String host,
                                        String event_title, String event_date, String event_time, String event_zoom_link,
                                        String event_status, String event_for) {

        this.online_event_id = online_event_id;
        this.controller = controller;
        this.controller_id = controller_id;
        this.monitor = monitor;
        this.host = host;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_zoom_link = event_zoom_link;
        this.event_status = event_status;
        this.event_for = event_for;
    }


    public String getOnline_event_id() {
        return online_event_id;
    }

    public String getController_id() {
        return controller_id;
    }

    public String getEvent_title() {
        return event_title;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

       public String getEvent_zoom_link() {
        return event_zoom_link;
    }

    public String getEvent_status() {
        return event_status;
    }

    public String getController() {
        return controller;
    }

    public String getMonitor() {
        return monitor;
    }

    public String getHost() {
        return host;
    }

    public String getEvent_for() {
        return event_for;
    }
}
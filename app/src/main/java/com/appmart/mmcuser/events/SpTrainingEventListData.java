package com.appmart.mmcuser.events;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SpTrainingEventListData {
    private String online_event_id, controller, controller_id, monitor,monitor_id,host,host_id,
            event_title, event_date, event_time,  event_zoom_link,
             event_status, event_for;

    public SpTrainingEventListData(String online_event_id, String controller, String controller_id, String monitor,String monitor_id,
                                   String host, String host_id,
                                   String event_title, String event_date, String event_time, String event_zoom_link,
                                   String event_status, String event_for) {

        this.online_event_id = online_event_id;
        this.controller = controller;
        this.controller_id = controller_id;
        this.monitor = monitor;
        this.monitor_id = monitor_id;
        this.host = host;
        this.host_id = host_id;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_zoom_link = event_zoom_link;
        this.event_status = event_status;
        this.event_for = event_for;
    }

    public String getController_id() {
        return controller_id;
    }

    public String getOnline_event_id() {
        return online_event_id;
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

    public String getMonitor_id() {
        return monitor_id;
    }

    public String getHost_id() {
        return host_id;
    }
}
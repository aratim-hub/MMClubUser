package com.appmart.mmcuser.changeSponsor;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class PlacedRequestData {
    private String req_id,sent_to,sent_to_mobile_number,current_sponsor,req_status,req_date;


    public PlacedRequestData(String req_id, String sent_to, String sent_to_mobile_number, String current_sponsor, String req_status, String req_date) {
        this.req_id = req_id;
        this.sent_to = sent_to;
        this.sent_to_mobile_number = sent_to_mobile_number;
        this.current_sponsor = current_sponsor;
        this.req_status = req_status;
        this.req_date = req_date;

    }

    public String getReq_id() {
        return req_id;
    }

    public String getSent_to() {
        return sent_to;
    }

    public String getSent_to_mobile_number() {
        return sent_to_mobile_number;
    }

    public String getCurrent_sponsor() {
        return current_sponsor;
    }

    public String getReq_status() {
        return req_status;
    }

    public String getReq_date() {
        return req_date;
    }
}
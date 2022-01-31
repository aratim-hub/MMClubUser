package com.appmart.mmcuser.changeSponsor;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class ReceivedRequestData {
    private String req_id,request_from,request_from_mobile_number,current_sponsor_mobile_number,current_sponsor,req_status,req_date;


    public ReceivedRequestData(String req_id, String request_from, String request_from_mobile_number, String current_sponsor, String current_sponsor_mobile_number, String req_status, String req_date) {
        this.req_id = req_id;
        this.request_from = request_from;
        this.request_from_mobile_number = request_from_mobile_number;
        this.current_sponsor = current_sponsor;
        this.current_sponsor_mobile_number = current_sponsor_mobile_number;
        this.req_status = req_status;
        this.req_date = req_date;

    }

    public String getReq_id() {
        return req_id;
    }

    public String getRequest_from() {
        return request_from;
    }

    public String getRequest_from_mobile_number() {
        return request_from_mobile_number;
    }

    public String getCurrent_sponsor_mobile_number() {
        return current_sponsor_mobile_number;
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
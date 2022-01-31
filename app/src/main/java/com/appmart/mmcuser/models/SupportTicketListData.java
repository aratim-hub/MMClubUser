  package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class SupportTicketListData {
    private String support_id;
    private String customer_id;
    private String ticket_msg;
    private String ticket_subject;


    public String getSupport_id() {
        return support_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getTicket_msg() {
        return ticket_msg;
    }

    public String getTicket_subject() {
        return ticket_subject;
    }

    public SupportTicketListData(String support_id, String customer_id,  String ticket_subject, String ticket_msg) {
        this.support_id = support_id;
        this.customer_id = customer_id;
        this.ticket_msg = ticket_msg;
        this.ticket_subject = ticket_subject;
    }
}
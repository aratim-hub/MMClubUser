package com.appmart.mmcuser.models;

public class ComplaintsReplyModel {

    String reply_id,support_id,admin_id,customer_id,reply_message,time;

    public String getReply_id() {
        return reply_id;
    }

    public String getSupport_id() {
        return support_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getReply_message() {
        return reply_message;
    }

    public String getTime() {
        return time;
    }

    public ComplaintsReplyModel(String reply_id, String support_id, String admin_id, String customer_id, String reply_message, String time) {
        this.reply_id = reply_id;
        this.support_id = support_id;
        this.admin_id = admin_id;
        this.customer_id = customer_id;
        this.reply_message = reply_message;
        this.time = time;
    }
}

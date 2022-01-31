package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MyTeamListData {
    private String downlink_userId;
    private String firstname;
    private String lastname;
    private String email;
    private String mobile_no;
    private String whatsapp_no;
    private String city_name;
    private String pincode;
    private String status;
    private String created_at;
    private String status_per;

    public String getDownlink_userId() {
        return downlink_userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getWhatsapp_no() {
        return whatsapp_no;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getPincode() {
        return pincode;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }


    public String getStatus_per() {
        return status_per;
    }



    public MyTeamListData(String id, String firstname, String lastname, String email, String mobile_no, String whatsapp_no, String city_name, String pincode, String status, String created_at, String status_per) {
        this.downlink_userId = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email =email;
        this.mobile_no = mobile_no;
        this.whatsapp_no = whatsapp_no;
        this.city_name = city_name;
        this.pincode = pincode;
        this.status = status;
        this.created_at = created_at;
        this.status_per = status_per;

    }


}
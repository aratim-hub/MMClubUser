package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class GoMultiincomeMemberListData {
    private String downline_id;
    private String downline_name;

    public GoMultiincomeMemberListData(String downline_id, String downline_name) {
        this.downline_id = downline_id;
        this.downline_name = downline_name;
    }


    public String getDownline_id() {
        return downline_id;
    }

    public String getDownline_name() {
        return downline_name;
    }
}
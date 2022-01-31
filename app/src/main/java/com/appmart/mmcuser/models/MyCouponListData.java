package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MyCouponListData {
    private String tg_id;
    private String group_for;
    private String group_link;

    public MyCouponListData(String tg_id, String group_for, String group_link) {
        this.tg_id = tg_id;
        this.group_for = group_for;
        this.group_link = group_link;
    }

    public String getTg_id() {
        return tg_id;
    }

    public String getGroup_for() {
        return group_for;
    }

    public String getGroup_link() {
        return group_link;
    }
}
package com.appmart.mmcuser.businessturnover;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TurnOverModel {

    public String id;
    public String firstname;
    public String lastname;
    public String user_name;
    public String mobile_no;
    public String turnover_for;
    public String is_turnoved_approved;
    public List<TurnoverListData> turnOver = null;

    @Override
    public String toString() {
        return "TurnOverModel{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", user_name='" + user_name + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                ", turnover_for='" + turnover_for + '\'' +
                ", is_turnoved_approved='" + is_turnoved_approved + '\'' +
                ", turnOver=" + turnOver +
                '}';
    }
}

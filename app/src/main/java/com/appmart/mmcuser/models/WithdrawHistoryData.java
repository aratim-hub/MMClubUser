  package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class WithdrawHistoryData {
    private String withdrawal_id,mobile_number,withdrawal_amount,withdraw_on,withdrawal_status,time_in_millis,user_comment;

    public String getWithdrawal_id() {
        return withdrawal_id;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getWithdrawal_amount() {
        return withdrawal_amount;
    }

    public String getWithdraw_on() {
        return withdraw_on;
    }

    public String getWithdrawal_status() {
        return withdrawal_status;
    }

    public String getTime_in_millis() {
        return time_in_millis;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public WithdrawHistoryData(String withdrawal_id, String mobile_number, String withdrawal_amount, String withdraw_on, String withdrawal_status, String user_comment, String time_in_millis) {
        this.withdrawal_id = withdrawal_id;
        this.mobile_number = mobile_number;
        this.withdrawal_amount = withdrawal_amount;
        this.withdraw_on = withdraw_on;
        this.withdrawal_status = withdrawal_status;
        this.user_comment = user_comment;
        this.time_in_millis = time_in_millis;
    }
}
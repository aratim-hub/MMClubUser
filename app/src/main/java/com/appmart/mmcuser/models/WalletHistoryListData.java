  package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class WalletHistoryListData {
    private String history_id,user_id,description,amount,datetime;

    public String getHistory_id() {
        return history_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getDatetime() {
        return datetime;
    }

    public WalletHistoryListData(String history_id, String user_id, String description, String amount, String datetime) {
        this.history_id = history_id;
        this.user_id = user_id;
        this.description = description;
        this.amount = amount;
        this.datetime = datetime;
    }
}
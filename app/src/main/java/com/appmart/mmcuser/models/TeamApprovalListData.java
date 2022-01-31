package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class TeamApprovalListData {
    private String link_id;
    private String user_id;
    private String company;
    private String ref_link;
    private String ispaid;
    private String payment_proof;
    private String isApproved;
    private String isApprovedByAdmin;
    private String is_training_completed;

    public String getLink_id() {
        return link_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCompany() {
        return company;
    }

    public String getRef_link() {
        return ref_link;
    }

    public String getIspaid() {
        return ispaid;
    }

    public String getPayment_proof() {
        return payment_proof;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public String getIsApprovedByAdmin() {
        return isApprovedByAdmin;
    }

    public String getIs_training_completed() {
        return is_training_completed;
    }

    public TeamApprovalListData(String link_id, String user_id, String company, String ref_link, String ispaid, String payment_proof, String isApproved, String isApprovedByAdmin, String is_training_completed) {
        this.link_id = link_id;
        this.user_id = user_id;
        this.company = company;
        this.ref_link =ref_link;
        this.ispaid = ispaid;
        this.payment_proof = payment_proof;
        this.isApproved = isApproved;
        this.isApprovedByAdmin = isApprovedByAdmin;
        this.is_training_completed = is_training_completed;

    }


}
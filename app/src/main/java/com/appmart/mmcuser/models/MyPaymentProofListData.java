package com.appmart.mmcuser.models;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MyPaymentProofListData {
    private String link_id, company, ref_link, payment_proof, payment_proof_date, isApproved, isApprovedByAdmin, is_training_completed, reject_reason;

    public MyPaymentProofListData(String link_id, String company, String ref_link, String payment_proof, String payment_proof_date, String isApproved, String isApprovedByAdmin, String is_training_completed, String reject_reason) {
        this.link_id = link_id;
        this.company = company;
        this.ref_link = ref_link;
        this.payment_proof = payment_proof;
        this.payment_proof_date = payment_proof_date;
        this.isApproved = isApproved;
        this.isApprovedByAdmin = isApprovedByAdmin;
        this.is_training_completed = is_training_completed;
        this.reject_reason = reject_reason;

    }

    public String getLink_id() {
        return link_id;
    }

    public String getCompany() {
        return company;
    }

    public String getRef_link() {
        return ref_link;
    }

    public String getPayment_proof() {
        return payment_proof;
    }

    public String getPayment_proof_date() {
        return payment_proof_date;
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

    public String getReject_reason() {
        return reject_reason;
    }
}
package com.appmart.mmcuser.companyshopee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompanyShopeeList {

    @SerializedName("company_shoppee_id")
    @Expose
    private String company_shoppee_id;
    @SerializedName("shop_name")
    @Expose
    private String shop_name;
    @SerializedName("address")
    @Expose
    private List<String> address;

    public String getCompany_shoppee_id() {
        return company_shoppee_id;
    }

    public void setCompany_shoppee_id(String company_shoppee_id) {
        this.company_shoppee_id = company_shoppee_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }
}

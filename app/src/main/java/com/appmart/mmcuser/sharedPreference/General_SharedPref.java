package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class General_SharedPref {
    private static General_SharedPref mInstance;
    private static Context mCtx;
    private String SHARED_PREF_NAME = "JoiningStatus";
    private String JOINING_STATUS = "strJoiningStatus";
    private String JOINING_WhatsApp = "strJoiningWhatsApp";
    private String LATEST_NEWS = "strLatestNews";
    private String VIDEO_IMAGE_MULTISUCCESS = "strVIDEO_IMAGE_MULTISUCCESS";
    private String VIDEO_IMAGE_TRAINING = "strVIDEO_IMAGE_TRAINING";
    private String VIDEO_IMAGE_INTRODUCTION = "strVIDEO_IMAGE_INTRODUCTION";
    private String START_BUSINESS_VIDEO_ID = "strSTART_BUSINESS_VIDEO_ID";
//    private String MSV_VIDEO_ID = "MSV_VIDEO_ID";
    private String REJECT_REASON = "REJECT_REASON";
    private String IS_APPROVED_BY_ADMIN = "IS_APPROVED_BY_ADMIN";
    private String TRAINGING_COMPLETED = "TRAINGING_COMPLETED";
    private String IS_APPROVED = "IS_APPROVED";

    private General_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized General_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new General_SharedPref(context);
        }
        return mInstance;
    }

    public boolean saveJoiningStatus(String joiningStatus) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JOINING_STATUS, joiningStatus);
        editor.apply();
        return true;
    }

    public boolean savePendingReason(String isApprovedByAdmin, String reject_reason, String is_training_completed, String isApproved) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_APPROVED_BY_ADMIN, isApprovedByAdmin);
        editor.putString(REJECT_REASON, reject_reason);
        editor.putString(TRAINGING_COMPLETED, is_training_completed);
        editor.putString(IS_APPROVED, isApproved);
        editor.apply();
        return true;
    }

    public boolean saveImageBelowVideo(String multisuccess, String training, String introduction) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(VIDEO_IMAGE_MULTISUCCESS, multisuccess);
        editor.putString(VIDEO_IMAGE_TRAINING, training);
        editor.putString(VIDEO_IMAGE_INTRODUCTION, introduction);
        editor.apply();
        return true;
    }


    public boolean latestNewsUpdates(String latestnews) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LATEST_NEWS, latestnews);
        editor.apply();
        return true;
    }

    public String getJoingStatus() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(JOINING_STATUS, null);
    }

    public String getLATEST_NEWS() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LATEST_NEWS, null);
    }

    public String getIS_APPROVED_BY_ADMIN() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_APPROVED_BY_ADMIN, null);
    }
    public String getTRAINGING_COMPLETED() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TRAINGING_COMPLETED, null);
    }

    public String getREJECT_REASON() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(REJECT_REASON, null);
    }

    public boolean clearSharedPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        return true;

    }

    public void whatsAppGroupJoiningStatus(String joined) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JOINING_WhatsApp, joined);
        editor.apply();

    }

    public String getJoingWhatsAppStatus() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(JOINING_WhatsApp, null);
    }


    //****************************************************************************************************************
    public String getVIDEO_IMAGE_INTRODUCTION() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(VIDEO_IMAGE_INTRODUCTION, null);
    }

    public String getVIDEO_IMAGE_TRAINING() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(VIDEO_IMAGE_TRAINING, null);
    }

    public String getVIDEO_IMAGE_MULTISUCCESS() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(VIDEO_IMAGE_MULTISUCCESS, null);
    }

    public boolean SaveStartBusinessVideoLink(String video_id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(START_BUSINESS_VIDEO_ID, video_id);
        editor.apply();
        return true;

    }

//    public boolean SaveMSVVideoLink(String video_id) {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(MSV_VIDEO_ID, video_id);
//        editor.apply();
//        return true;
//
//    }

    public String getSTART_BUSINESS_VIDEO_ID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(START_BUSINESS_VIDEO_ID, null);
    }

//    public String getMSV_VIDEO_ID() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(MSV_VIDEO_ID, null);
//    }
}
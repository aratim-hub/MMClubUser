package com.appmart.mmcuser.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class Profile_SharedPref {
    private static Profile_SharedPref mInstance;
    private static Context mCtx;
    private String SHARED_PREF_NAME = "PROFILE";
    private String ID = "strId";
    private String F_NAME = "strFName";
    private String L_NAME = "strLName";
    private String EMAIL = "strEmail";
    private String MOBILE = "strMobile";
    private String WHATSAPP_NUMBER = "strWhatsAppNumber";
    private String CIITY = "strCity";
    private String PIN_CODE = "strPinCode";
    private String REFERAL_SPONSOR_ID = "strHostID";
//    private String REFERAL_HOST_ID = "strHostID";
    private String CREATED_AT = "strCreatedAt";
//    private String HOST_NAME = "strHostName";
    private String INTRO_VDO_URL = "strIntroVdoUrl";
    private String IS_BUSINESS_STARTED = "strIS_BUSINESS_STARTED";
    private String WALLETE_BALANCE = "strWALLETE_BALANCE";
    private String MULTI_SUCCESS_PATH_VIDEO = "MULTI_SUCCESS_PATH_VIDEO";

    private Profile_SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized Profile_SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Profile_SharedPref(context);
        }
        return mInstance;
    }

    public boolean clearSharedPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        return true;

    }

    public boolean saveProfile(String id, String f_name, String l_name, String mobile, String email, String whatsapp_no, String city, String p_code, String host_id, String created_at, String introductionvideo_url, String is_business_start, String wallet_balance, String multisuccess_path_video) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID, id);
        editor.putString(F_NAME, f_name);
        editor.putString(L_NAME, l_name);
        editor.putString(EMAIL, email);
        editor.putString(MOBILE, mobile);
        editor.putString(WHATSAPP_NUMBER, whatsapp_no);
        editor.putString(CIITY, city);
        editor.putString(PIN_CODE, p_code);
        editor.putString(REFERAL_SPONSOR_ID, host_id);
        editor.putString(CREATED_AT, created_at);
        editor.putString(INTRO_VDO_URL, introductionvideo_url);
        editor.putString(IS_BUSINESS_STARTED, is_business_start);
        editor.putString(WALLETE_BALANCE, wallet_balance);
        editor.putString(MULTI_SUCCESS_PATH_VIDEO, multisuccess_path_video);
        editor.apply();
        return true;
    }


    public String getUserId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ID, null);
    }

    public String getEmailId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, null);
    }

    public String getFName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(F_NAME, null);
    }

    public String getLName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(L_NAME, null);
    }

    public String getMobile() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MOBILE, null);
    }

    public String getWhatsAppNumber() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(WHATSAPP_NUMBER, null);
    }

    public String getCity() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CIITY, null);
    }

    public String getPincode() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PIN_CODE, null);
    }

    public String getJoiningDate() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CREATED_AT, null);
    }

    public String getREFERAL_HOST_ID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(REFERAL_SPONSOR_ID, null);
    }

    public String getINTRO_VDO_URL() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(INTRO_VDO_URL, null);
    }
    public String getMULTI_SUCCESS_PATH_VIDEO() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MULTI_SUCCESS_PATH_VIDEO, null);
    }

    public void saveIsBusinessStarted(String b) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_BUSINESS_STARTED, b);
        editor.apply();

    }
    public String getIS_BUSINESS_STARTED() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_BUSINESS_STARTED, "false");
    }
    public String getWALLETE_BALANCE() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(WALLETE_BALANCE, "00");
    }
}






